package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.repositories.TeachingStaffRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class TeachingStaffServiceTest {

    @InjectMocks
    private TeachingStaffService teachingStaffService;

    @Mock
    private TeachingStaffRepository teachingStaffRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void testListAllTeachingStaff_Nominal() {
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
    void testListAllTeachingStaff_Empty() {
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
    void testListAllTeachingStaff_ServiceError() {
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
}