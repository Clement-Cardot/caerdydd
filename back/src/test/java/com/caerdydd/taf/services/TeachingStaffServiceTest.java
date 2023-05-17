package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.TeachingStaffEntity;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.TeachingStaffRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.UserServiceRules;


@ExtendWith(MockitoExtension.class)
public class TeachingStaffServiceTest {

    @InjectMocks
    private TeachingStaffService teachingStaffService;

    @Mock
    private TeachingStaffRepository teachingStaffRepository;

    @Mock
    private UserServiceRules userServiceRules;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void testListAllTeachingStaff_Nominal() {
        // Mock teamMemberRepository.findAll() method
        List<TeachingStaffEntity> mockedAnswer = new ArrayList<TeachingStaffEntity>();

        mockedAnswer.add(new TeachingStaffEntity());
        when(teachingStaffRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<TeachingStaffDTO> expectedAnswer = new ArrayList<TeachingStaffDTO>();
        expectedAnswer.add(new TeachingStaffDTO());

        // Call the method to test
        List<TeachingStaffDTO> result = new ArrayList<TeachingStaffDTO>();
        try {
            result = teachingStaffService.listAllTeachingStaff();
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(teachingStaffRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testListAllTeachingStaff_Empty() {
          // Mock teamMemberRepository.findAll() method
          List<TeachingStaffEntity> mockedAnswer = new ArrayList<TeachingStaffEntity>();
          when(teachingStaffRepository.findAll()).thenReturn(mockedAnswer);
  
          // Define the expected answer
          List<TeachingStaffDTO> expectedAnswer = new ArrayList<TeachingStaffDTO>();
  
          // Call the method to test
          List<TeachingStaffDTO> result = new ArrayList<TeachingStaffDTO>();
          try {
              result = teachingStaffService.listAllTeachingStaff();
          } catch (CustomRuntimeException e) {
              fail();
          }
  
          // Verify the result
          verify(teachingStaffRepository, times(1)).findAll();
          assertEquals(0, result.size());
          assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testListAllTeachingStaff_ServiceError() {
          // Mock teamMemberRepository.findAll() method
          when(teachingStaffRepository.findAll()).thenThrow(new NoSuchElementException());
  
          // Call the method to test
          CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teachingStaffService.listAllTeachingStaff();
        });
  
          // Verify the result
          verify(teachingStaffRepository, times(1)).findAll();
          assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    void testGetTeachingStaffById_Nominal() {
        // Mock teachingStaffRepository.findById() method
        UserEntity user1 = new UserEntity(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        UserDTO user2 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        Optional<TeachingStaffEntity> mockedAnswer = Optional.of(
            new TeachingStaffEntity(user1)
            );
        when(teachingStaffRepository.findById(1)).thenReturn(mockedAnswer);

        // Define the expected answer
        TeachingStaffDTO expectedAnswer = new TeachingStaffDTO(user2);

        // Call the method to test
        TeachingStaffDTO result = new TeachingStaffDTO();
        try {
            result = teachingStaffService.getTeachingStaffById(1);
        } catch (CustomRuntimeException e) {
            fail();
        } 

        // Verify the result
        verify(teachingStaffRepository, times(1)).findById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeachingStaffById_ServiceError() {
        // Mock teachingStaffRepository.findById() method
        when(teachingStaffRepository.findById(1)).thenThrow(new NoSuchElementException());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teachingStaffService.getTeachingStaffById(1);
        });

        // Verify the result
        verify(teachingStaffRepository, times(1)).findById(1);
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    void testGetTeachingStaffById_TeachingStaffNotFound() {
        // Mock teachingStaffRepository.findById() method
        Optional<TeachingStaffEntity> mockedAnswer = Optional.empty();
        when(teachingStaffRepository.findById(1)).thenReturn(mockedAnswer);

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teachingStaffService.getTeachingStaffById(1);
        });

        // Verify the result
        verify(teachingStaffRepository, times(1)).findById(1);
        assertEquals(CustomRuntimeException.TEACHINGSTAFF_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testSaveTeachingStaff_Nominal() {
        // Mock teachingStaffRepository.save() method
        UserEntity user1 = new UserEntity(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        UserDTO user2 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        TeachingStaffEntity mockedAnswer = new TeachingStaffEntity(
            user1
            );
        when(teachingStaffRepository.save(any(TeachingStaffEntity.class))).thenReturn(mockedAnswer);

        // Prepare the input
        TeachingStaffDTO teachingStaffToSave = new TeachingStaffDTO(
            user2
            );

        // Call the method to test
        TeachingStaffDTO response = teachingStaffService.saveTeachingStaff(teachingStaffToSave);

        // Verify the result
        verify(teachingStaffRepository, times(1)).save(any(TeachingStaffEntity.class));
        assertEquals(teachingStaffToSave.toString(), response.toString());
    }

    @Test
    public void testUpdateTeachingStaff_Nominal() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole() method
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        UserEntity user1 = new UserEntity(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        UserDTO user2 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        // Prepare the input
        TeachingStaffDTO teachingStaffDTO = new TeachingStaffDTO(user2);

        // Mock teachingStaffRepository.findById() method
        TeachingStaffEntity teachingStaffEntity = new TeachingStaffEntity(user1);
        when(teachingStaffRepository.findById(teachingStaffDTO.getUser().getId())).thenReturn(Optional.of(teachingStaffEntity));
        
        // Mock teachingStaffRepository.save() method
        TeachingStaffEntity mockedAnswer = new TeachingStaffEntity(user1);
        when(teachingStaffRepository.save(any(TeachingStaffEntity.class))).thenReturn(mockedAnswer);

        // Call the method to test
        TeachingStaffDTO result = null;
        try {
            result = teachingStaffService.updateTeachingStaff(teachingStaffDTO);
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(userServiceRules, times(1)).checkCurrentUserRole("TEACHING_STAFF_ROLE");
        verify(teachingStaffRepository, times(1)).findById(teachingStaffDTO.getUser().getId());
        verify(teachingStaffRepository, times(1)).save(any(TeachingStaffEntity.class));
        assertEquals(teachingStaffDTO.toString(), result.toString());
    }

    @Test
    void testUpdateTeachingStaff_UserNotFound() throws CustomRuntimeException {
        UserDTO user1 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        // Prepare the input
        TeachingStaffDTO teachingStaffDTO = new TeachingStaffDTO(
            user1
                );

        // Mock teachingStaffRepository.findById() method
        when(teachingStaffRepository.findById(teachingStaffDTO.getUser().getId())).thenReturn(Optional.empty());
        
        // Call the method to test and verify the exception
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            teachingStaffService.updateTeachingStaff(teachingStaffDTO);
        });
        assertEquals(CustomRuntimeException.USER_NOT_FOUND, exception.getMessage());

        // Verify the method invocations
        verify(userServiceRules, times( 1)).checkCurrentUserRole("TEACHING_STAFF_ROLE");
        verify(teachingStaffRepository, times(1)).findById(teachingStaffDTO.getUser().getId());
        verify(teachingStaffRepository, never()).save(any(TeachingStaffEntity.class));
    }

    @Test
    public void testUpdateTeachingStaff_ServiceError() throws Exception {
        // Prepare the input
        UserEntity user1 = new UserEntity(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        UserDTO user2 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        TeachingStaffDTO teachingStaffDTO = new TeachingStaffDTO(user2);

        // Mock teachingStaffRepository.findById() method
        TeachingStaffEntity teachingStaffEntity = new TeachingStaffEntity(user1);
        when(teachingStaffRepository.findById(teachingStaffDTO.getUser().getId())).thenReturn(Optional.of(teachingStaffEntity));
        
        // Mock teachingStaffRepository.save() method to throw an exception
        when(teachingStaffRepository.save(any(TeachingStaffEntity.class))).thenThrow(new RuntimeException());

        // Call the method to test and verify the exception
        CustomRuntimeException thrownException = assertThrows(CustomRuntimeException.class, () -> {
            teachingStaffService.updateTeachingStaff(teachingStaffDTO);
        });
        assertEquals(CustomRuntimeException.SERVICE_ERROR, thrownException.getMessage());

        // Verify the method invocations
        verify(userServiceRules, times(1)).checkCurrentUserRole("TEACHING_STAFF_ROLE");
        verify(teachingStaffRepository, times(1)).findById(teachingStaffDTO.getUser().getId());
        verify(teachingStaffRepository, times(1)).save(any(TeachingStaffEntity.class));
    }
}