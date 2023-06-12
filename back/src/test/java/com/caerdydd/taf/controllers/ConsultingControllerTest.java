package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.consulting.ConsultingDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingConsultingDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.ConsultingService;

@ExtendWith(MockitoExtension.class)
public class ConsultingControllerTest {

    @InjectMocks
    private ConsultingController consultingController;

    @Mock
    private ConsultingService consultingService;

    @Test
    void testGetAllConsultings_Nominal() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        List<PlannedTimingConsultingDTO> mockedConsultings = List.of(
            new PlannedTimingConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                LocalDateTime.of(2023, 1, 1, 10, 30, 0)),
            new PlannedTimingConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 11, 0, 0),
                LocalDateTime.of(2023, 1, 1, 11, 30, 0))
        );
        when(consultingService.listAllPlannedTimingConsultings()).thenReturn(mockedConsultings);

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.getAllPlannedTimingConsultings();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testGetAllConsultings_Empty() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        List<PlannedTimingConsultingDTO> mockedConsultings = new ArrayList<>();
        when(consultingService.listAllPlannedTimingConsultings()).thenReturn(mockedConsultings);

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.getAllPlannedTimingConsultings();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testGetAllConsultings_UnexpectedError() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        when(consultingService.listAllPlannedTimingConsultings()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.getAllPlannedTimingConsultings();

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test
    void testuploadConsulting_Nominal() throws CustomRuntimeException, IOException {
        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/csv", "mock".getBytes());

        // Mock consultingService.uploadConsulting()
        List<PlannedTimingConsultingDTO> mockedConsultings = List.of(
            new PlannedTimingConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                LocalDateTime.of(2023, 1, 1, 10, 30, 0)),
            new PlannedTimingConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 11, 0, 0),
                LocalDateTime.of(2023, 1, 1, 11, 30, 0))
        );
        when(consultingService.uploadPlannedTimingConsultings(file)).thenReturn(mockedConsultings);

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.uploadPlannedTimingConsultings(file);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testuploadConsulting_EmptyFile() throws CustomRuntimeException, IOException {
        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "".getBytes());

        // Mock consultingService.uploadConsulting()
        when(consultingService.uploadPlannedTimingConsultings(file)).thenThrow(new CustomRuntimeException(CustomRuntimeException.FILE_IS_EMPTY));

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.uploadPlannedTimingConsultings(file);

        // Assertions
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }

    @Test
    void testuploadConsulting_UnsupportedMediaType() throws CustomRuntimeException, IOException {
        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());

        // Mock consultingService.uploadConsulting()
        when(consultingService.uploadPlannedTimingConsultings(file)).thenThrow(new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT));

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.uploadPlannedTimingConsultings(file);

        // Assertions
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }

    @Test
    void testuploadConsulting_UnexpectedException() throws CustomRuntimeException, IOException {
        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "mock".getBytes());

        // Mock consultingService.uploadConsulting()
        when(consultingService.uploadPlannedTimingConsultings(file)).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.uploadPlannedTimingConsultings(file);

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test 
    void testUpdateAvailability_Nominal() throws CustomRuntimeException {
        // Mock consultingService.updateAvailability()
        PlannedTimingAvailabilityDTO mockedConsulting = new PlannedTimingAvailabilityDTO();
        when(consultingService.updatePlannedTimingAvailability(mockedConsulting)).thenReturn(mockedConsulting);

        // Call method to test
        ResponseEntity<PlannedTimingAvailabilityDTO> response = consultingController.updateAvailability(mockedConsulting);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsulting, response.getBody());
    }

    @Test 
    void testUpdateAvailability_AvailabilityNotFound() throws CustomRuntimeException {
        // Mock consultingService.updateAvailability()
        when(consultingService.updatePlannedTimingAvailability(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND));

        // Call method to test
        ResponseEntity<PlannedTimingAvailabilityDTO> response = consultingController.updateAvailability(new PlannedTimingAvailabilityDTO());

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test 
    void testUpdateAvailability_UserNotTeachingStaff() throws CustomRuntimeException {
        // Mock consultingService.updateAvailability()
        when(consultingService.updatePlannedTimingAvailability(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Call method to test
        ResponseEntity<PlannedTimingAvailabilityDTO> response = consultingController.updateAvailability(new PlannedTimingAvailabilityDTO());

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test 
    void testUpdateAvailability_UserIsNotOwnerOfAvailability() throws CustomRuntimeException {
        // Mock consultingService.updateAvailability()
        when(consultingService.updatePlannedTimingAvailability(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY));

        // Call method to test
        ResponseEntity<PlannedTimingAvailabilityDTO> response = consultingController.updateAvailability(new PlannedTimingAvailabilityDTO());

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test 
    void testUpdateAvailability_PlannedTimingIsPast() throws CustomRuntimeException {
        // Mock consultingService.updateAvailability()
        when(consultingService.updatePlannedTimingAvailability(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_IN_PAST));

        // Call method to test
        ResponseEntity<PlannedTimingAvailabilityDTO> response = consultingController.updateAvailability(new PlannedTimingAvailabilityDTO());

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test 
    void testUpdateAvailability_PlannedTimingIsAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.updateAvailability()
        when(consultingService.updatePlannedTimingAvailability(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_ALREADY_TAKEN));

        // Call method to test
        ResponseEntity<PlannedTimingAvailabilityDTO> response = consultingController.updateAvailability(new PlannedTimingAvailabilityDTO());

        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test 
    void testUpdateAvailability_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.updateAvailability()
        when(consultingService.updatePlannedTimingAvailability(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<PlannedTimingAvailabilityDTO> response = consultingController.updateAvailability(new PlannedTimingAvailabilityDTO());

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test 
    void testUpdateAvailability_UnexpectedException() throws CustomRuntimeException {
        // Mock consultingService.updateAvailability()
        when(consultingService.updatePlannedTimingAvailability(any())).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call method to test
        ResponseEntity<PlannedTimingAvailabilityDTO> response = consultingController.updateAvailability(new PlannedTimingAvailabilityDTO());

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test
    void testCreateConsulting_Nominal() throws CustomRuntimeException {
        // Mock consultingService.createConsulting()
        ConsultingDTO mockedConsulting = new ConsultingDTO();
        when(consultingService.createConsulting(any())).thenReturn(mockedConsulting);

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.createConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsulting, response.getBody());
    }

    @Test
    void testCreateConsulting_PlannedTimingAvailabilityNotFound() throws CustomRuntimeException {
        // Mock consultingService.createConsulting()
        when(consultingService.createConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.createConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateConsulting_UserNotTeamMember() throws CustomRuntimeException {
        // Mock consultingService.createConsulting()
        when(consultingService.createConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEAM_MEMBER));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.createConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testCreateConsulting_PlannedTimingIsPast() throws CustomRuntimeException {
        // Mock consultingService.createConsulting()
        when(consultingService.createConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_IN_PAST));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.createConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testCreateConsulting_PlannedTimingIsAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.createConsulting()
        when(consultingService.createConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_ALREADY_TAKEN));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.createConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testCreateConsulting_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.createConsulting()
        when(consultingService.createConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.createConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testCreateConsulting_UnexpectedException() throws CustomRuntimeException {
        // Mock consultingService.createConsulting()
        when(consultingService.createConsulting(any())).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.createConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }
}