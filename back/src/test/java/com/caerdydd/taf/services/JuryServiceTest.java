package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
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
    UserService userService;

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

    @Test
    void testGetJury_Nominal() throws CustomRuntimeException {
        // Mock methods
        Integer juryId = 1;
        JuryEntity jury = new JuryEntity();
        when(juryRepository.findById(juryId)).thenReturn(Optional.of(jury));

        // Call method
        JuryDTO result = juryService.getJury(juryId);

        // Check results
        verify(juryRepository, times(1)).findById(juryId); 
        assertNotNull(result); // Assuming the modelMapper works correctly
    }

    @Test
    void testGetJury_JuryNotFound() {
        // Mock methods
        Integer juryId = 1;
        when(juryRepository.findById(juryId)).thenReturn(Optional.empty()); // Return empty optional to trigger the exception

        // Call method and catch the exception
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            juryService.getJury(juryId);
        });

        // Check the exception message
        assertEquals(CustomRuntimeException.JURY_NOT_FOUND, exception.getMessage());

        // Check interactions
        verify(juryRepository, times(1)).findById(juryId); 
    }

    @Test
    void testGetAllJuries_Nominal() {
        // Mock methods
        List<JuryEntity> juries = new ArrayList<>();
        juries.add(new JuryEntity());
        when(juryRepository.findAll()).thenReturn(juries);

        // Call method
        List<JuryDTO> result = juryService.getAllJuries();

        // Check results
        verify(juryRepository, times(1)).findAll(); 
        assertEquals(juries.size(), result.size()); // Assuming the modelMapper works correctly
    }

    @Test
    void testAddJuryMemberRole_UserAlreadyHasRole() throws CustomRuntimeException {
        // Arrange
        UserDTO user = new UserDTO();
        RoleDTO role = new RoleDTO();
        role.setRole("JURY_MEMBER_ROLE");
        user.getRoles().add(role);
        
        TeachingStaffDTO teachingStaff = new TeachingStaffDTO();
        teachingStaff.setUser(user);

        // Pas besoin de mocker checkCurrentUserRole(), par défaut il autorise l'action

        // Act
        TeachingStaffDTO result = juryService.addJuryMemberRole(teachingStaff);
        
        // Assert
        verify(userService, times(0)).updateUser(any(UserDTO.class)); // User is not updated
        assertEquals(teachingStaff, result); // Returned object is the same as input object
    }

    @Test
    void testAddJuryMemberRole_UserDoesNotHaveRole() throws CustomRuntimeException {
        // Arrange
        UserDTO user = new UserDTO();
        TeachingStaffDTO teachingStaff = new TeachingStaffDTO();
        teachingStaff.setUser(user);
        
        // Pas besoin de mocker checkCurrentUserRole(), par défaut il autorise l'action
        when(userService.updateUser(any(UserDTO.class))).thenReturn(user);
        when(teachingStaffService.getTeachingStaffById(any())).thenReturn(teachingStaff);

        // Act
        TeachingStaffDTO result = juryService.addJuryMemberRole(teachingStaff);
        
        // Assert
        verify(userService, times(1)).updateUser(any(UserDTO.class)); // User is updated
        verify(teachingStaffService, times(1)).getTeachingStaffById(any()); // Teaching staff is fetched by id
        assertEquals(1, result.getUser().getRoles().size()); // New role is added
        assertEquals("JURY_MEMBER_ROLE", result.getUser().getRoles().get(0).getRole()); // Role is correct
    }


    @Test
    void testFindJuryByTs1AndTs2_ServiceError() {
        // Arrange
        TeachingStaffDTO ts1 = new TeachingStaffDTO();
        TeachingStaffDTO ts2 = new TeachingStaffDTO();

        when(juryRepository.findByTs1AndTs2(any(), any())).thenThrow(new RuntimeException());

        // Act & Assert
        CustomRuntimeException thrownException = assertThrows(CustomRuntimeException.class, () -> {
            juryService.findJuryByTs1AndTs2(ts1, ts2);
        });

        assertEquals(CustomRuntimeException.SERVICE_ERROR, thrownException.getMessage());
    }
}