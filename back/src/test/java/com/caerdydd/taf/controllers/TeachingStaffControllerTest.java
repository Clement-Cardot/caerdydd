package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.any;
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

import com.caerdydd.taf.models.dto.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.UserDTO;
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
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.getAllTeachingStaff();

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
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.getAllTeachingStaff();

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
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.getAllTeachingStaff();

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
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.    getAllTeachingStaff();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teachingStaffService, times(1)).listAllTeachingStaff();
    }

    @Test
    public void testGetAllTeachingStaff_Nominal() throws CustomRuntimeException {
        // Mock teachingStaffService.listAllTeachingStaff() method
        List<TeachingStaffDTO> mockedAnswer = new ArrayList<>();
        UserDTO user1 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        UserDTO user2 = new UserDTO(2, "firstName2", "lastName2", "login2", "password2", "email2", null);

        mockedAnswer.add(new TeachingStaffDTO(user1));
        mockedAnswer.add(new TeachingStaffDTO(user2));
        when(teachingStaffService.listAllTeachingStaff()).thenReturn(mockedAnswer);

        // Define the expected response
        ResponseEntity<List<TeachingStaffDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);

        // Call the method to test
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.getAllTeachingStaff();

        // Verify the result
        verify(teachingStaffService, times(1)).listAllTeachingStaff();
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testGetAllTeachingStaff_Empty() throws CustomRuntimeException {
        // Mock teachingStaffService.listAllTeachingStaff() method
        List<TeachingStaffDTO> mockedAnswer = new ArrayList<>();
        when(teachingStaffService.listAllTeachingStaff()).thenReturn(mockedAnswer);
    
        // Define the expected response
        ResponseEntity<List<TeachingStaffDTO>> expectedAnswer = new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    
        // Call the method to test
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.getAllTeachingStaff();
    
        // Verify the result
        verify(teachingStaffService, times(1)).listAllTeachingStaff();
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testGetAllTeachingStaff_ServiceError() throws CustomRuntimeException {
        // Mock teachingStaffService.listAllTeachingStaff() method
        when(teachingStaffService.listAllTeachingStaff()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected response
        ResponseEntity<List<TeachingStaffDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.getAllTeachingStaff();

        // Verify the result
        verify(teachingStaffService, times(1)).listAllTeachingStaff();
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testGetAllTeachingStaff_UnexpectedError() throws CustomRuntimeException {
        // Mock teachingStaffService.listAllTeachingStaff() method
        when(teachingStaffService.listAllTeachingStaff()).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected response
        ResponseEntity<List<TeachingStaffDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<List<TeachingStaffDTO>> result = teachingStaffController.getAllTeachingStaff();

        // Verify the result
        verify(teachingStaffService, times(1)).listAllTeachingStaff();
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeachingStaffById_Nominal() throws CustomRuntimeException {
        UserDTO user1 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        // Mock teachingStaffService.getTeachingStaffById() method
        TeachingStaffDTO mockedAnswer = new TeachingStaffDTO(user1);
        when(teachingStaffService.getTeachingStaffById(1)).thenReturn(mockedAnswer);

        // Define the expected response
        ResponseEntity<TeachingStaffDTO> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);

        // Call the method to test
        ResponseEntity<TeachingStaffDTO> result = teachingStaffController.getTeachingStaffById(1);

        // Verify the result
        verify(teachingStaffService, times(1)).getTeachingStaffById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeachingStaffById_TeachingStaffNotFound() throws CustomRuntimeException {
        // Mock teachingStaffService.getTeachingStaffById() method
        when(teachingStaffService.getTeachingStaffById(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEACHINGSTAFF_NOT_FOUND));

        // Define the expected response
        ResponseEntity<TeachingStaffDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<TeachingStaffDTO> result = teachingStaffController.getTeachingStaffById(1);

        // Verify the result
        verify(teachingStaffService, times(1)).getTeachingStaffById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeachingStaffById_ServiceError() throws CustomRuntimeException {
        // Mock teachingStaffService.getTeachingStaffById() method
        when(teachingStaffService.getTeachingStaffById(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected response
        ResponseEntity<TeachingStaffDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<TeachingStaffDTO> result = teachingStaffController.getTeachingStaffById(1);

        // Verify the result
        verify(teachingStaffService, times(1)).getTeachingStaffById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeachingStaffById_UnexpectedError() throws CustomRuntimeException {
        // Mock teachingStaffService.getTeachingStaffById() method
        when(teachingStaffService.getTeachingStaffById(1)).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected response
        ResponseEntity<TeachingStaffDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<TeachingStaffDTO> result = teachingStaffController.getTeachingStaffById(1);

        // Verify the result
        verify(teachingStaffService, times(1)).getTeachingStaffById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testSubmitSpeciality_Nominal() throws CustomRuntimeException {
        // Mock teachingStaffService.updateTeachingStaff() method
        TeachingStaffDTO mockedAnswer = new TeachingStaffDTO();
        when(teachingStaffService.updateTeachingStaff(any(TeachingStaffDTO.class))).thenReturn(mockedAnswer);

        // Create a sample TeachingStaffDTO object for the request body
        TeachingStaffDTO requestTeachingStaffDTO = new TeachingStaffDTO();
        // Set the necessary properties of the requestTeachingStaffDTO object

        // Call the method to test
        ResponseEntity<TeachingStaffDTO> result = teachingStaffController.submitSpeciality(requestTeachingStaffDTO);

        // Verify the result
        verify(teachingStaffService, times(1)).updateTeachingStaff(any(TeachingStaffDTO.class));
        assertEquals(mockedAnswer.toString(), result.getBody().toString());
    }

    @Test
    void testSubmitSpeciality_CurrentUserIsNotRequestUser() throws CustomRuntimeException {
        // Mock teachingStaffService.updateTeachingStaff() method
        when(teachingStaffService.updateTeachingStaff(any(TeachingStaffDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER));

        // Create a sample TeachingStaffDTO object for the request body
        TeachingStaffDTO requestTeachingStaffDTO = new TeachingStaffDTO();
        // Set the necessary properties of the requestTeachingStaffDTO object

        // Call the method to test
        ResponseEntity<TeachingStaffDTO> result = teachingStaffController.submitSpeciality(requestTeachingStaffDTO);

        // Verify the result
        verify(teachingStaffService, times(1)).updateTeachingStaff(any(TeachingStaffDTO.class));
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    void testSubmitSpeciality_TeachingStaffNotFound() throws CustomRuntimeException {
        // Mock teachingStaffService.updateTeachingStaff() method
        when(teachingStaffService.updateTeachingStaff(any(TeachingStaffDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEACHINGSTAFF_NOT_FOUND));

        // Create a sample TeachingStaffDTO object for the request body
        TeachingStaffDTO requestTeachingStaffDTO = new TeachingStaffDTO();
        // Set the necessary properties of the requestTeachingStaffDTO object

        // Call the method to test
        ResponseEntity<TeachingStaffDTO> result = teachingStaffController.submitSpeciality(requestTeachingStaffDTO);

        // Verify the result
        verify(teachingStaffService, times(1)).updateTeachingStaff(any(TeachingStaffDTO.class));
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void testSubmitSpeciality_UnexpectedError() throws CustomRuntimeException {
        // Mock teachingStaffService.updateTeachingStaff() method
        when(teachingStaffService.updateTeachingStaff(any(TeachingStaffDTO.class))).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Create a sample TeachingStaffDTO object for the request body
        TeachingStaffDTO requestTeachingStaffDTO = new TeachingStaffDTO();
        // Set the necessary properties of the requestTeachingStaffDTO object

        // Define the expected answer
        ResponseEntity<TeachingStaffDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<TeachingStaffDTO> result = teachingStaffController.submitSpeciality(requestTeachingStaffDTO);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teachingStaffService, times(1)).updateTeachingStaff(any(TeachingStaffDTO.class));
    }
}
