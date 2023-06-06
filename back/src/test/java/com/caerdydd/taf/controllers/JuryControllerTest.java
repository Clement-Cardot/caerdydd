package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
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

    @Test
void createJury_teachingStaffAreTheSame_returnBadRequest() throws CustomRuntimeException {
    // Arrange
    Integer juryMemberDevId = 1;
    Integer juryMemberArchiId = 2;
    when(juryService.addJury(juryMemberDevId, juryMemberArchiId)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_ARE_THE_SAME));

    // Act
    ResponseEntity<JuryDTO> response = juryController.createJury(juryMemberDevId, juryMemberArchiId);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
}

@Test
void createJury_teachingStaffNotFound_returnNotFound() throws CustomRuntimeException {
    // Arrange
    Integer juryMemberDevId = 1;
    Integer juryMemberArchiId = 2;
    when(juryService.addJury(juryMemberDevId, juryMemberArchiId)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_NOT_FOUND));

    // Act
    ResponseEntity<JuryDTO> response = juryController.createJury(juryMemberDevId, juryMemberArchiId);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}

@Test
void createJury_juryAlreadyExists_returnConflict() throws CustomRuntimeException {
    // Arrange
    Integer juryMemberDevId = 1;
    Integer juryMemberArchiId = 2;
    when(juryService.addJury(juryMemberDevId, juryMemberArchiId)).thenThrow(new CustomRuntimeException(CustomRuntimeException.JURY_ALREADY_EXISTS));

    // Act
    ResponseEntity<JuryDTO> response = juryController.createJury(juryMemberDevId, juryMemberArchiId);

    // Assert
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
}

@Test
void testGetAllJuries_Nominal() throws CustomRuntimeException {
    // Given
    List<JuryDTO> mockedJuries = Arrays.asList(new JuryDTO(), new JuryDTO());
    when(juryService.getAllJuries()).thenReturn(mockedJuries);

    // When
    ResponseEntity<List<JuryDTO>> response = juryController.getAllJuries();

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockedJuries, response.getBody());
}

@Test
void testGetAllJuries_ServiceError() throws CustomRuntimeException {
    // Given
    when(juryService.getAllJuries()).thenThrow(new RuntimeException());

    // When
    ResponseEntity<List<JuryDTO>> response = juryController.getAllJuries();

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}

@Test
void testGetJury_Nominal() throws CustomRuntimeException {
    // Given
    JuryDTO mockedJury = new JuryDTO();
    when(juryService.getJury(any(Integer.class))).thenReturn(mockedJury);

    // When
    ResponseEntity<JuryDTO> response = juryController.getJury(1);

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockedJury, response.getBody());
}

@Test
void testGetJury_JuryNotFound() throws CustomRuntimeException {
    // Given
    when(juryService.getJury(any(Integer.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.JURY_NOT_FOUND));

    // When
    ResponseEntity<JuryDTO> response = juryController.getJury(1);

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}

@Test
void testGetJury_ServiceError() throws CustomRuntimeException {
    // Given
    when(juryService.getJury(any(Integer.class))).thenThrow(new CustomRuntimeException("Error"));

    // When
    ResponseEntity<JuryDTO> response = juryController.getJury(1);

    // Then
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}





}
