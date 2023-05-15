package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeachingStaffService;

@ExtendWith(MockitoExtension.class)
public class TeachingStaffControllerTest {
    @InjectMocks
    private TeachingStaffController teachingStaffController;

    @Mock
    private TeachingStaffService teachingStaffService;

    @Test
    public void testList_Nominal() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        List<TeachingStaffDTO> mockedAnswer = new ArrayList<TeachingStaffDTO>();

        mockedAnswer.add(new TeachingStaffDTO());
        when(teachingStaffService.listAllTeachingStaff()).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<List<TeachingStaffDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teachingStaffService, times(1)).listAllTeachingStaff();
    }

    @Test
    public void testList_Empty() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        List<TeachingStaffDTO> mockedAnswer = new ArrayList<TeachingStaffDTO>();
        when(teachingStaffService.listAllTeachingStaff()).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<List<TeachingStaffDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teachingStaffService, times(1)).listAllTeachingStaff();
    }

    @Test
    public void testList_ServiceError() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        when(teachingStaffService.listAllTeachingStaff()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<List<TeachingStaffDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
        // Call the method to test
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teachingStaffService, times(1)).listAllTeachingStaff();
    }

    @Test
    public void testList_UnexpectedError() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        when(teachingStaffService.listAllTeachingStaff()).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected answer
        ResponseEntity<List<TeachingStaffDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        
        // Call the method to test
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teachingStaffService, times(1)).listAllTeachingStaff();
    }
}
