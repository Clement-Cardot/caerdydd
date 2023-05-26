package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.dto.user.RoleDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.models.entities.user.UserEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.JuryServiceRules;
import com.caerdydd.taf.services.rules.UserServiceRules;

@ExtendWith(MockitoExtension.class)
public class JuryServiceTest {

    @InjectMocks
    private JuryService juryService;

    @Mock
    TeachingStaffService teachingStaffService;

    @Mock
    RoleService roleService;

    @Mock
    JuryServiceRules juryServiceRules;

    @Mock
    UserServiceRules userServiceRules;

    @Mock
    private JuryRepository juryRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void testUpdateTeamMember_Nominal() {
        JuryDTO input = new JuryDTO(1);
        JuryEntity juryEntity = new JuryEntity(1);

        when(juryRepository.save(any(JuryEntity.class))).thenReturn(juryEntity);

        JuryDTO result = new JuryDTO();

        try {
            result = juryService.updateJury(input);
        } catch (CustomRuntimeException e) {
            fail();
        }

        verify(juryRepository, times(1)).save(any(JuryEntity.class));
        assertEquals(input.toString(), result.toString());
    }

    @Test
    void testUpdateTeamMember_ServiceError() throws Exception {
        JuryDTO juryDTO = new JuryDTO();
        
        when(juryRepository.save(any(JuryEntity.class))).thenThrow(new RuntimeException());

        CustomRuntimeException thrownException = assertThrows(CustomRuntimeException.class, () -> {
            juryService.updateJury(juryDTO);
        });
        assertEquals(CustomRuntimeException.SERVICE_ERROR, thrownException.getMessage());
    }

    @Test
    public void testAddJury_nominal() throws CustomRuntimeException {
        // Set up test data
        TeachingStaffDTO devMember = new TeachingStaffDTO();
        devMember.setIdUser(1);
        TeachingStaffDTO archiMember = new TeachingStaffDTO();
        archiMember.setIdUser(2);

        UserEntity user1 = new UserEntity();
        user1.setId(1);
        UserEntity user2 = new UserEntity();
        user2.setId(2);
        TeachingStaffEntity ts1 = new TeachingStaffEntity(user1);
        TeachingStaffEntity ts2 = new TeachingStaffEntity(user2);
        JuryEntity juryEntity = new JuryEntity(ts1, ts2);

        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());
        when(juryRepository.save(any(JuryEntity.class))).thenReturn(juryEntity);

        when(teachingStaffService.getTeachingStaffById(1)).thenReturn(devMember);
        when(teachingStaffService.getTeachingStaffById(2)).thenReturn(archiMember);

        // Call the method to be tested
        JuryDTO result = juryService.addJury(1, 2);

        // Verify that the expected methods were called
        verify(userServiceRules).checkCurrentUserRole(RoleDTO.PLANNING_ROLE);
        verify(juryServiceRules).checkDifferentTeachingStaff(1, 2);
        verify(juryRepository).save(any(JuryEntity.class));

        // Verify the result
        assertNotNull(result);
        assertEquals(devMember.getIdUser(), result.getTs1().getIdUser());
        assertEquals(archiMember.getIdUser(), result.getTs2().getIdUser());
    }

    @Test
    public void findJuryByTs1AndTs2_ValidJury1_ReturnsJuryDTO() throws CustomRuntimeException {
        // Arrange
        TeachingStaffDTO ts1 = new TeachingStaffDTO();
        TeachingStaffDTO ts2 = new TeachingStaffDTO();
        TeachingStaffEntity tsEntity1 = new TeachingStaffEntity();
        TeachingStaffEntity tsEntity2 = new TeachingStaffEntity();
        JuryEntity juryEntity = new JuryEntity();
        JuryDTO expectedJuryDTO = new JuryDTO();

        when(modelMapper.map(ts1, TeachingStaffEntity.class)).thenReturn(tsEntity1);
        when(modelMapper.map(ts2, TeachingStaffEntity.class)).thenReturn(tsEntity2);

        Optional<JuryEntity> optionalJury = Optional.of(juryEntity);
        when(juryRepository.findByTs1AndTs2(any(), any())).thenReturn(optionalJury);

        when(modelMapper.map(juryEntity, JuryDTO.class)).thenReturn(expectedJuryDTO);

        // Act
        JuryDTO result = juryService.findJuryByTs1AndTs2(ts1, ts2);

        // Assert
        assertEquals(expectedJuryDTO, result);
        verify(modelMapper, times(1)).map(juryEntity, JuryDTO.class);
    }

    @Test
    public void findJuryByTs1AndTs2_NoJuryFound_ThrowsCustomRuntimeException() {
        TeachingStaffDTO ts12 = new TeachingStaffDTO();
        TeachingStaffDTO ts22 = new TeachingStaffDTO();

        when(juryRepository.findByTs1AndTs2(any(), any())).thenReturn(Optional.empty());

        assertThrows(CustomRuntimeException.class, () -> juryService.findJuryByTs1AndTs2(ts12, ts22));
    }
}