package com.caerdydd.taf.controllers;

import com.caerdydd.taf.models.dto.project.PresentationDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.PresentationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

public class PresentationControllerTest {

    @InjectMocks
    private PresentationController presentationController;

    @Mock
    private PresentationService presentationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePresentation_Nominal() throws CustomRuntimeException {
        // Mock presentationService.createPresentation()
        PresentationDTO mockedPresentation = new PresentationDTO();
        when(presentationService.createPresentation(mockedPresentation)).thenReturn(mockedPresentation);

        // Call method to test
        ResponseEntity<PresentationDTO> response = presentationController.createPresentation(mockedPresentation);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockedPresentation, response.getBody());
    }

    @Test
    void testCreatePresentation_JuryNotFound() throws CustomRuntimeException {
        // Mock presentationService.createPresentation()
        when(presentationService.createPresentation(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.JURY_NOT_FOUND));

        // Call method to test
        ResponseEntity<PresentationDTO> response = presentationController.createPresentation(new PresentationDTO());

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreatePresentation_ProjectNotFound() throws CustomRuntimeException {
        // Mock presentationService.createPresentation()
        when(presentationService.createPresentation(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PROJECT_NOT_FOUND));

        // Call method to test
        ResponseEntity<PresentationDTO> response = presentationController.createPresentation(new PresentationDTO());

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
void testGetPresentationById_Nominal() throws CustomRuntimeException {
    // Mock presentationService.getPresentationById()
    PresentationDTO mockedPresentation = new PresentationDTO();
    when(presentationService.getPresentationById(any())).thenReturn(mockedPresentation);

    // Call method to test
    ResponseEntity<PresentationDTO> response = presentationController.getPresentationById(1);

    // Assertions
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockedPresentation, response.getBody());
}

@Test
void testGetPresentationById_NotFound() throws CustomRuntimeException {
    // Mock presentationService.getPresentationById()
    when(presentationService.getPresentationById(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PRESENTATION_NOT_FOUND));

    // Call method to test
    ResponseEntity<PresentationDTO> response = presentationController.getPresentationById(1);

    // Assertions
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}

@Test
void testGetTeamPresentations_Nominal() throws CustomRuntimeException {
    // Mock presentationService.getTeamPresentations()
    List<PresentationDTO> mockedPresentations = List.of(new PresentationDTO());
    when(presentationService.getTeamPresentations(any())).thenReturn(mockedPresentations);

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.getTeamPresentations(1);

    // Assertions
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockedPresentations, response.getBody());
}

@Test
void testGetTeamPresentations_TeamNotFound() throws CustomRuntimeException {
    // Mock presentationService.getTeamPresentations()
    when(presentationService.getTeamPresentations(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND));

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.getTeamPresentations(1);

    // Assertions
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}


@Test
void testGetTeachingStaffPresentations_Nominal() throws CustomRuntimeException {
    // Mock presentationService.getTeachingStaffPresentations()
    List<PresentationDTO> mockedPresentations = List.of(new PresentationDTO());
    when(presentationService.getTeachingStaffPresentations(any())).thenReturn(mockedPresentations);

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.getTeachingStaffPresentations(1);

    // Assertions
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockedPresentations, response.getBody());
}

@Test
void testGetTeachingStaffPresentations_TeachingStaffNotFound() throws CustomRuntimeException {
    // Mock presentationService.getTeachingStaffPresentations()
    when(presentationService.getTeachingStaffPresentations(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_NOT_FOUND));

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.getTeachingStaffPresentations(1);

    // Assertions
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
}

@Test
void testListAllPresentations_Nominal() throws CustomRuntimeException {
    // Mock presentationService.listAllPresentations()
    List<PresentationDTO> mockedPresentations = List.of(new PresentationDTO());
    when(presentationService.listAllPresentations()).thenReturn(mockedPresentations);

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.listAllPresentations();

    // Assertions
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(mockedPresentations, response.getBody());
}

@Test
void testListAllPresentations_ServiceError() throws CustomRuntimeException {
    // Mock presentationService.listAllPresentations()
    when(presentationService.listAllPresentations()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.listAllPresentations();

    // Assertions
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}


@Test
void testCreatePresentation_TeachingStaffNotAvailable() throws CustomRuntimeException {
    // Mock presentationService.createPresentation()
    when(presentationService.createPresentation(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_NOT_AVAILABLE));

    // Call method to test
    ResponseEntity<PresentationDTO> response = presentationController.createPresentation(new PresentationDTO());

    // Assertions
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
}

@Test
void testCreatePresentation_UserIsNotAPlanningAssistant() throws CustomRuntimeException {
    // Mock presentationService.createPresentation()
    when(presentationService.createPresentation(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT));

    // Call method to test
    ResponseEntity<PresentationDTO> response = presentationController.createPresentation(new PresentationDTO());

    // Assertions
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
}

@Test
void testCreatePresentation_PresentationEndBeforeBegin() throws CustomRuntimeException {
    // Mock presentationService.createPresentation()
    when(presentationService.createPresentation(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PRESENTATION_END_BEFORE_BEGIN));

    // Call method to test
    ResponseEntity<PresentationDTO> response = presentationController.createPresentation(new PresentationDTO());

    // Assertions
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
}

@Test
void testGetPresentationById_ServiceError() throws CustomRuntimeException {
    // Mock presentationService.getPresentationById()
    when(presentationService.getPresentationById(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    // Call method to test
    ResponseEntity<PresentationDTO> response = presentationController.getPresentationById(1);

    // Assertions
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}

@Test
void testGetPresentationById_UnexpectedError() throws CustomRuntimeException {
    // Mock presentationService.getPresentationById()
    when(presentationService.getPresentationById(any())).thenThrow(new CustomRuntimeException("UnexpectedError"));

    // Call method to test
    ResponseEntity<PresentationDTO> response = presentationController.getPresentationById(1);

    // Assertions
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
}

@Test
void testGetTeamPresentations_ServiceError() throws CustomRuntimeException {
    // Mock presentationService.getTeamPresentations()
    when(presentationService.getTeamPresentations(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.getTeamPresentations(1);

    // Assertions
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}

@Test
void testGetTeamPresentations_UnexpectedError() throws CustomRuntimeException {
    // Mock presentationService.getTeamPresentations()
    when(presentationService.getTeamPresentations(any())).thenThrow(new CustomRuntimeException("UnexpectedError"));

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.getTeamPresentations(1);

    // Assertions
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
}

@Test
void testGetTeachingStaffPresentations_ServiceError() throws CustomRuntimeException {
    // Mock presentationService.getTeachingStaffPresentations()
    when(presentationService.getTeachingStaffPresentations(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.getTeachingStaffPresentations(1);

    // Assertions
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}

@Test
void testGetTeachingStaffPresentations_UnexpectedError() throws CustomRuntimeException {
    // Mock presentationService.getTeachingStaffPresentations()
    when(presentationService.getTeachingStaffPresentations(any())).thenThrow(new CustomRuntimeException("UnexpectedError"));

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.getTeachingStaffPresentations(1);

    // Assertions
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
}

@Test
void testListAllPresentations_UnexpectedError() throws CustomRuntimeException {
    // Mock presentationService.listAllPresentations()
    when(presentationService.listAllPresentations()).thenThrow(new CustomRuntimeException("UnexpectedError"));

    // Call method to test
    ResponseEntity<List<PresentationDTO>> response = presentationController.listAllPresentations();

    // Assertions
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
}

@Test
void testCreatePresentation_ServiceError() throws CustomRuntimeException {
    // Mock presentationService.createPresentation()
    when(presentationService.createPresentation(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

    // Call method to test
    ResponseEntity<PresentationDTO> response = presentationController.createPresentation(new PresentationDTO());

    // Assertions
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
}

@Test
void testCreatePresentation_UnexpectedError() throws CustomRuntimeException {
    // Mock presentationService.createPresentation()
    when(presentationService.createPresentation(any())).thenThrow(new CustomRuntimeException("UnexpectedError"));

    // Call method to test
    ResponseEntity<PresentationDTO> response = presentationController.createPresentation(new PresentationDTO());

    // Assertions
    assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
}


    @Test
    void testUpdateNotesJury_Nominal() throws CustomRuntimeException {
        // Mock presentationService.setJuryNotes()
        PresentationDTO mockedPresentation = new PresentationDTO();
        when(presentationService.setJuryNotes(anyInt(), any())).thenReturn(mockedPresentation);

        // Create a request body with the required parameters
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idPresentation", 1);
        requestBody.put("note", "Excellent presentation");

        // Call method to test
        ResponseEntity<PresentationDTO> response = presentationController.updateNotesJury(requestBody);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedPresentation, response.getBody());

        // Verify that presentationService.setJuryNotes() was called with the correct arguments
        verify(presentationService, times(1)).setJuryNotes(1, "Excellent presentation");
    }

    @Test
    void testUpdateNotesJury_JuryNotFound() throws CustomRuntimeException {
        // Mock presentationService.setJuryNotes() to throw CustomRuntimeException with JURY_NOT_FOUND message
        when(presentationService.setJuryNotes(anyInt(), any()))
                .thenThrow(new CustomRuntimeException(CustomRuntimeException.JURY_NOT_FOUND));

        // Create a request body with the required parameters
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idPresentation", 1);
        requestBody.put("note", "Excellent presentation");

        // Call method to test
        ResponseEntity<PresentationDTO> response = presentationController.updateNotesJury(requestBody);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verify that presentationService.setJuryNotes() was called with the correct arguments
        verify(presentationService, times(1)).setJuryNotes(1, "Excellent presentation");
    }

    @Test
    void testUpdateNotesJury_PresentationDidNotBegin() throws CustomRuntimeException {
        // Mock presentationService.setJuryNotes() to throw CustomRuntimeException with PRESENTATION_DID_NOT_BEGIN message
        when(presentationService.setJuryNotes(anyInt(), any()))
                .thenThrow(new CustomRuntimeException(CustomRuntimeException.PRESENTATION_DID_NOT_BEGIN));

        // Create a request body with the required parameters
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idPresentation", 1);
        requestBody.put("note", "Excellent presentation");

        // Call method to test
        ResponseEntity<PresentationDTO> response = presentationController.updateNotesJury(requestBody);

        // Assertions
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());

        // Verify that presentationService.setJuryNotes() was called with the correct arguments
        verify(presentationService, times(1)).setJuryNotes(1, "Excellent presentation");
    }

    @Test
    void testUpdateNotesJury_ServiceError() throws CustomRuntimeException {
        // Mock presentationService.setJuryNotes() to throw CustomRuntimeException with SERVICE_ERROR message
        when(presentationService.setJuryNotes(anyInt(), any()))
                .thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Create a request body with the required parameters
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idPresentation", 1);
        requestBody.put("note", "Excellent presentation");

        // Call method to test
        ResponseEntity<PresentationDTO> response = presentationController.updateNotesJury(requestBody);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verify that presentationService.setJuryNotes() was called with the correct arguments
        verify(presentationService, times(1)).setJuryNotes(1, "Excellent presentation");
    }

    @Test
    void testUpdateNotesJury_BadRequest() throws CustomRuntimeException {
        // Mock presentationService.setJuryNotes() to throw a general CustomRuntimeException
        when(presentationService.setJuryNotes(anyInt(), any())).thenThrow(new CustomRuntimeException("Some error"));

        // Create a request body with the required parameters
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("idPresentation", 1);
        requestBody.put("note", "Excellent presentation");

        // Call method to test
        ResponseEntity<PresentationDTO> response = presentationController.updateNotesJury(requestBody);

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        // Verify that presentationService.setJuryNotes() was called with the correct arguments
        verify(presentationService, times(1)).setJuryNotes(1, "Excellent presentation");
    }

}
