package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.UserRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void testListAllUsers_Nominal(){
        // Mock userRepository.findAll() method
        List<UserEntity> mockedAnswer = new ArrayList<UserEntity>();
        mockedAnswer.add(new UserEntity(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD"));
        mockedAnswer.add(new UserEntity(2, "firstName2", "lastName2", "login2", "password2", "email2", null));
        when(userRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<UserDTO> expectedAnswer = new ArrayList<UserDTO>();
        expectedAnswer.add(new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD"));
        expectedAnswer.add(new UserDTO(2, "firstName2", "lastName2", "login2", "password2", "email2", null));

        // Call the method to test
        List<UserDTO> result = new ArrayList<UserDTO>();
        try {
            result = userService.listAllUsers();
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(userRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testListAllUsers_Empty(){
        // Mock userRepository.findAll() method
        List<UserEntity> mockedAnswer = new ArrayList<UserEntity>();
        when(userRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<UserDTO> expectedAnswer = new ArrayList<UserDTO>();
        
        // Call the method to test
        List<UserDTO> result = new ArrayList<UserDTO>();
        try {
            result = userService.listAllUsers();
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(userRepository, times(1)).findAll();
        assertEquals(0, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testListAllUsers_ServiceError(){
        // Mock userRepository.findAll() method
        when(userRepository.findAll()).thenThrow(new NoSuchElementException());
        
        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            userService.listAllUsers();
        });
        
        // Verify the result
        verify(userRepository, times(1)).findAll();
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    public void testGetUserById_Nominal(){
        // Mock userRepository.findById() method
        Optional<UserEntity> mockedAnswer = Optional.of(new UserEntity(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD"));
        when(userRepository.findById(1)).thenReturn(mockedAnswer);

        // Define the expected answer
        UserDTO expectedAnswer = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");

        // Call the method to test
        UserDTO result = new UserDTO();
        try {
            result = userService.getUserById(1);
        } catch (CustomRuntimeException e) {
            fail();
        } 

        // Verify the result
        verify(userRepository, times(1)).findById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testGetUserById_UserNotFound(){
        // Mock userRepository.findById() method
        Optional<UserEntity> mockedAnswer = Optional.empty();
        when(userRepository.findById(1)).thenReturn(mockedAnswer);

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            userService.getUserById(1);
        });

        // Verify the result
        verify(userRepository, times(1)).findById(anyInt());
        assertEquals(CustomRuntimeException.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testGetUserById_ServiceError(){
        // Mock userRepository.findById() method
        when(userRepository.findById(1)).thenThrow(new NoSuchElementException());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            userService.getUserById(1);
        });

        // Verify the result
        verify(userRepository, times(1)).findById(anyInt());
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    public void testGetUserByLogin_Nominal(){
        // Mock userRepository.findByLogin() method
        Optional<UserEntity> mockedAnswer = Optional.of(new UserEntity(1, "jean", "dupont", "jdupont", "password1", "email1", "LD"));
        when(userRepository.findByLogin("jdupont")).thenReturn(mockedAnswer);

        // Define the expected answer
        UserDTO expectedAnswer = new UserDTO(1, "jean", "dupont", "jdupont", "password1", "email1", "LD");

        // Call the method to test
        UserDTO result = new UserDTO();
        try {
            result = userService.getUserByLogin("jdupont");
        } catch (CustomRuntimeException e) {
            fail();
        } 

        // Verify the result
        verify(userRepository, times(1)).findByLogin(anyString());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testGetUserByLogin_UserNotFound(){
        // Mock userRepository.findByLogin() method
        Optional<UserEntity> mockedAnswer = Optional.empty();
        when(userRepository.findByLogin("jdupont")).thenReturn(mockedAnswer);

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            userService.getUserByLogin("jdupont");
        });

        // Verify the result
        verify(userRepository, times(1)).findByLogin(anyString());
        assertEquals(CustomRuntimeException.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testGetUserByLogin_ServiceError(){
        // Mock userRepository.findByLogin() method
        when(userRepository.findByLogin("jdupont")).thenThrow(new NoSuchElementException());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            userService.getUserByLogin("jdupont");
        });

        // Verify the result
        verify(userRepository, times(1)).findByLogin(anyString());
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    public void testSaveUser_Nominal(){
        // Mock userRepository.save() method
        UserEntity mockedAnswer = new UserEntity(1, "jean", "dupont", "jdupont", "password1", "email1", "LD");
        when(userRepository.save(any(UserEntity.class))).thenReturn(mockedAnswer);

        // Define the expected answer
        UserDTO userToSave = new UserDTO(1, "jean", "dupont", "jdupont", "password1", "email1", "LD");

        // Call the method to test
        UserDTO result= new UserDTO();
        try {
            result = userService.saveUser(userToSave);
        } catch (Exception e) {
            fail();
        }

        // Verify the result
        verify(userRepository, times(1)).save(any(UserEntity.class));
        assertEquals(userToSave.toString(), result.toString());
    }

    @Test
    public void testDeleteUserById_Nominal(){
        // Mock userRepository.deleteById() method
        // UserEntity mockedAnswer = new UserEntity(1, "jean", "dupont", "jdupont", "password1", "email1", "LD");
        Mockito.doNothing().when(userRepository).deleteById(anyInt());

        // Call the method to test
        try {
            userService.deleteUserById(1);
        } catch (Exception e) {
            fail();
        }

        // Verify the result
        verify(userRepository, times(1)).deleteById(anyInt());
    }
    
}
