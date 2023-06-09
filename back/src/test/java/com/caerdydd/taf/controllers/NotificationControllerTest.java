package com.caerdydd.taf.controllers;

import com.caerdydd.taf.models.dto.notification.NotificationDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNotification_Nominal() throws CustomRuntimeException {
        // Mock notificationService.createNotification()
        NotificationDTO mockedNotification = new NotificationDTO();
        when(notificationService.createNotification(mockedNotification)).thenReturn(mockedNotification);

        // Call method to test
        ResponseEntity<NotificationDTO> response = notificationController.createNotification(mockedNotification);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockedNotification, response.getBody());
    }

    @Test
    void testChangeStatusRead_Nominal() throws CustomRuntimeException {
        // Mock notificationService.changeStatusRead()
        NotificationDTO mockedNotification = new NotificationDTO();
        when(notificationService.changeStatusRead(any())).thenReturn(mockedNotification);

        // Call method to test
        ResponseEntity<NotificationDTO> response = notificationController.changeStatusRead(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedNotification, response.getBody());
    }

    @Test
    void testGetNotificationsByUserId_Nominal() throws CustomRuntimeException {
        // Mock notificationService.getNotificationsByUserId()
        List<NotificationDTO> mockedNotifications = List.of(new NotificationDTO());
        when(notificationService.getNotificationsByUserId(any())).thenReturn(mockedNotifications);

        // Call method to test
        ResponseEntity<List<NotificationDTO>> response = notificationController.getNotificationsByUserId(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedNotifications, response.getBody());
    }

    @Test
    void testGetAllNotifications_Nominal() {
        // Mock notificationService.getAllNotifications()
        List<NotificationDTO> mockedNotifications = List.of(new NotificationDTO());
        when(notificationService.getAllNotifications()).thenReturn(mockedNotifications);

        // Call method to test
        ResponseEntity<List<NotificationDTO>> response = notificationController.getAllNotifications();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedNotifications, response.getBody());
    }

    @Test
    void testCreateNotification_UserNotFound() throws CustomRuntimeException {
        // Mock notificationService.createNotification()
        when(notificationService.createNotification(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND));

        // Call method to test
        ResponseEntity<NotificationDTO> response = notificationController.createNotification(new NotificationDTO());

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateNotification_ServiceError() throws CustomRuntimeException {
        // Mock notificationService.createNotification()
        when(notificationService.createNotification(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<NotificationDTO> response = notificationController.createNotification(new NotificationDTO());

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCreateNotification_UnexpectedError() throws CustomRuntimeException {
        // Mock notificationService.createNotification()
        when(notificationService.createNotification(any())).thenThrow(new CustomRuntimeException("Unexpected Error"));

        // Call method to test
        ResponseEntity<NotificationDTO> response = notificationController.createNotification(new NotificationDTO());

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    // Test for changeStatusRead method

    @Test
    void testChangeStatusRead_NotificationNotFound() throws CustomRuntimeException {
        // Mock notificationService.changeStatusRead()
        when(notificationService.changeStatusRead(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.NOTIFICATION_NOT_FOUND));

        // Call method to test
        ResponseEntity<NotificationDTO> response = notificationController.changeStatusRead(1);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testChangeStatusRead_ServiceError() throws CustomRuntimeException {
        // Mock notificationService.changeStatusRead()
        when(notificationService.changeStatusRead(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<NotificationDTO> response = notificationController.changeStatusRead(1);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testChangeStatusRead_UnexpectedError() throws CustomRuntimeException {
        // Mock notificationService.changeStatusRead()
        when(notificationService.changeStatusRead(any())).thenThrow(new CustomRuntimeException("Unexpected Error"));

        // Call method to test
        ResponseEntity<NotificationDTO> response = notificationController.changeStatusRead(1);

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    // Test for getNotificationsByUserId method

    @Test
    void testGetNotificationsByUserId_UserNotFound() throws CustomRuntimeException {
        // Mock notificationService.getNotificationsByUserId()
        when(notificationService.getNotificationsByUserId(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND));

        // Call method to test
        ResponseEntity<List<NotificationDTO>> response = notificationController.getNotificationsByUserId(1);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetNotificationsByUserId_ServiceError() throws CustomRuntimeException {
        // Mock notificationService.getNotificationsByUserId()
        when(notificationService.getNotificationsByUserId(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<List<NotificationDTO>> response = notificationController.getNotificationsByUserId(1);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetNotificationsByUserId_UnexpectedError() throws CustomRuntimeException {
        // Mock notificationService.getNotificationsByUserId()
        when(notificationService.getNotificationsByUserId(any())).thenThrow(new CustomRuntimeException("Unexpected Error"));

        // Call method to test
        ResponseEntity<List<NotificationDTO>> response = notificationController.getNotificationsByUserId(1);

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

}
