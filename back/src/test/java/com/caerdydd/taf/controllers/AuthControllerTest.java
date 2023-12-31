package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;


    @Test
    void testLoginUser_Nominal() throws CustomRuntimeException{
        // Mock authService.loginUser()
        UserDTO userDTO = new UserDTO("firstname", "lastname", "login", "password", "email", "LD");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,  "JSESSIONID=1234567890");
        when(authService.loginUser(any(), any())).thenReturn(new ResponseEntity<UserDTO>(userDTO, headers, HttpStatus.OK));
        
        // Call login method
        ResponseEntity<UserDTO> response = authController.login("login", "password");

        // Check response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        assertNotNull(response.getHeaders().get(HttpHeaders.SET_COOKIE));
        assertEquals("JSESSIONID=1234567890", response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0));
    }

    @Test
    void testLoginUser_UserNotFound() throws CustomRuntimeException{
        // Mock authService.loginUser()
        when(authService.loginUser(any(), any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND));
        
        // Call login method
        ResponseEntity<UserDTO> response = authController.login("login", "password");

        // Check response
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testLoginUser_UserPasswordNotMatch() throws CustomRuntimeException{
        // Mock authService.loginUser()
        when(authService.loginUser(any(), any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_PASSWORD_NOT_MATCH));
        
        // Call login method
        ResponseEntity<UserDTO> response = authController.login("login", "password");

        // Check response
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void testLoginUser_UnexpectedError() throws CustomRuntimeException{
        // Mock authService.loginUser()
        when(authService.loginUser(any(), any())).thenThrow(new CustomRuntimeException("Unexpected error"));
        
        // Call login method
        ResponseEntity<UserDTO> response = authController.login("login", "password");

        // Check response
        assertNotNull(response);
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test
    void testLogoutUser_Nominal() {
        // Mock authService.logout()
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,  "JSESSIONID=Expired");
        when(authService.logoutUser()).thenReturn(new ResponseEntity<String>("You've been signed out!", headers, HttpStatus.OK));

        // Call logout method
        ResponseEntity<String> response = authController.logoutUser();

        // Check response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("You've been signed out!", response.getBody());
        // Verify that the JSESSIONID cookie has been removed
        assertNotNull(response.getHeaders().get(HttpHeaders.SET_COOKIE));
        assertEquals("JSESSIONID=Expired", response.getHeaders().get(HttpHeaders.SET_COOKIE).get(0));
    }
    
    @Test
    void testLogoutUser_Exception() {
        // Mock authService.logout() pour simuler une exception
        when(authService.logoutUser()).thenThrow(new RuntimeException("Something went wrong"));

        // Appel de la méthode de contrôleur et vérification de la réponse
        ResponseEntity<String> response = authController.logoutUser();

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }
}