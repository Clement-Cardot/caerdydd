package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.JuryService;

@ExtendWith(MockitoExtension.class)
public class JuryControllerTest {
    @InjectMocks
    private JuryController juryController;

    @Mock
    private JuryService juryService;

    @Test
    void testCreateJury_nominal() throws CustomRuntimeException {
        // Given
        Integer juryMemberDevId = 1;
        Integer juryMemberArchiId = 2;
        JuryDTO juryDTO = new JuryDTO();
        when(juryService.addJury(juryMemberDevId, juryMemberArchiId)).thenReturn(juryDTO);

        // When
        ResponseEntity<JuryDTO> response = juryController.createJury(juryMemberDevId, juryMemberArchiId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(juryDTO, response.getBody());
    }

    @Test
    void createJury_userNotPlanningAssistant_returnNotFound() throws CustomRuntimeException {
        // Arrange
        Integer juryMemberDevId = 1;
        Integer juryMemberArchiId = 2;
        when(juryService.addJury(juryMemberDevId, juryMemberArchiId)).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT));

        // Act
        ResponseEntity<JuryDTO> response = juryController.createJury(juryMemberDevId, juryMemberArchiId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createJury_serviceError_returnInternalServerError() throws CustomRuntimeException {
        // Arrange
        Integer juryMemberDevId = 1;
        Integer juryMemberArchiId = 2;
        when(juryService.addJury(juryMemberDevId, juryMemberArchiId)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Act
        ResponseEntity<JuryDTO> response = juryController.createJury(juryMemberDevId, juryMemberArchiId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCreateJury_UnexpectedError() throws CustomRuntimeException{
        Integer juryMemberDevId = 1;
        Integer juryMemberArchiId = 2;
        when(juryService.addJury(juryMemberDevId, juryMemberArchiId)).thenThrow(new CustomRuntimeException("Unexpected exception"));
        
        ResponseEntity<JuryDTO> response = juryController.createJury(juryMemberDevId, juryMemberArchiId);

        // Then
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }
}