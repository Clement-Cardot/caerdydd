package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.UserService;
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void testList_Nominal() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        List<UserDTO> mockedAnswer = new ArrayList<UserDTO>();
        mockedAnswer.add(new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD"));
        mockedAnswer.add(new UserDTO(2, "firstName2", "lastName2", "login2", "password2", "email2", null));
        when(userService.listAllUsers()).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<List<UserDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<List<UserDTO>> result = userController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    public void testList_Empty() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        List<UserDTO> mockedAnswer = new ArrayList<UserDTO>();
        when(userService.listAllUsers()).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<List<UserDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<List<UserDTO>> result = userController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    public void testList_ServiceError() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        when(userService.listAllUsers()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<List<UserDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
        // Call the method to test
        ResponseEntity<List<UserDTO>> result = userController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    public void testList_UnexpectedError() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        when(userService.listAllUsers()).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected answer
        ResponseEntity<List<UserDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        
        // Call the method to test
        ResponseEntity<List<UserDTO>> result = userController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).listAllUsers();
    }
    
    @Test
    public void testGet_Nominal() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        UserDTO mockedAnswer = new UserDTO( 1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        when(userService.getUserById(anyInt())).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<UserDTO> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<UserDTO> result = userController.get(1);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).getUserById(anyInt());
    }

    @Test
    public void testGet_UserNotFound() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        when(userService.getUserById(anyInt())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND));

        // Define the expected answer
        ResponseEntity<UserDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        // Call the method to test
        ResponseEntity<UserDTO> result = userController.get(1);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).getUserById(anyInt());
    }

    @Test
    public void testGet_ServiceError() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        when(userService.getUserById(anyInt())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<UserDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
        // Call the method to test
        ResponseEntity<UserDTO> result = userController.get(1);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).getUserById(anyInt());
    }

    @Test
    public void testGet_UnexpectedError() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        when(userService.getUserById(anyInt())).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected answer
        ResponseEntity<UserDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        
        // Call the method to test
        ResponseEntity<UserDTO> result = userController.get(1);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).getUserById(anyInt());
    }

    @Test
    public void testAdd_Nominal() throws CustomRuntimeException{
        // Mock userService.saveUser() method
        UserDTO mockedAnswer = new UserDTO( 1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        when(userService.saveUser(any(UserDTO.class))).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.CREATED);

        // Call the method to test
        ResponseEntity<HttpStatus> result = userController.add(mockedAnswer);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).saveUser(any(UserDTO.class));
    }

    @Test
    public void testAdd_UserAlreadyExists() throws CustomRuntimeException{
        // Mock userService.saveUser() method
        UserDTO mockedAnswer = new UserDTO( 1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        when(userService.saveUser(any(UserDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_ALREADY_EXISTS));

        // Define the expected answer
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.CONFLICT);

        // Call the method to test
        ResponseEntity<HttpStatus> result = userController.add(mockedAnswer);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).saveUser(any(UserDTO.class));
    }

    @Test
    public void testAdd_ServiceError() throws CustomRuntimeException{
        // Mock userService.saveUser() method
        UserDTO mockedAnswer = new UserDTO( 1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        when(userService.saveUser(any(UserDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<HttpStatus> result = userController.add(mockedAnswer);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).saveUser(any(UserDTO.class));
    }

    @Test
    public void testAdd_UnexpectedError() throws CustomRuntimeException{
        // Mock userService.saveUser() method
        UserDTO mockedAnswer = new UserDTO( 1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        when(userService.saveUser(any(UserDTO.class))).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected answer
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<HttpStatus> result = userController.add(mockedAnswer);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).saveUser(any(UserDTO.class));
    }

    @Test
    public void testUpdate_Nominal() throws CustomRuntimeException{
        // Mock userService.updateUser() method
        UserDTO mockedAnswer = new UserDTO( 1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        when(userService.updateUser(any(UserDTO.class))).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.OK);

        // Call the method to test
        ResponseEntity<HttpStatus> result = userController.update(mockedAnswer);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).updateUser(any(UserDTO.class));
    }

    @Test
    public void testUpdate_UserAlreadyExists() throws CustomRuntimeException{
        // Mock userService.updateUser() method
        UserDTO mockedAnswer = new UserDTO( 1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        when(userService.updateUser(any(UserDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND));

        // Define the expected answer
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<HttpStatus> result = userController.update(mockedAnswer);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).updateUser(any(UserDTO.class));
    }

    @Test
    public void testUpdate_ServiceError() throws CustomRuntimeException{
        // Mock userService.updateUser() method
        UserDTO mockedAnswer = new UserDTO( 1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        when(userService.updateUser(any(UserDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<HttpStatus> result = userController.update(mockedAnswer);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).updateUser(any(UserDTO.class));
    }

    @Test
    public void testUpdate_UnexpectedError() throws CustomRuntimeException{
        // Mock userService.updateUser() method
        UserDTO mockedAnswer = new UserDTO( 1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        when(userService.updateUser(any(UserDTO.class))).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected answer
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<HttpStatus> result = userController.update(mockedAnswer);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(userService, times(1)).updateUser(any(UserDTO.class));
    }

}
