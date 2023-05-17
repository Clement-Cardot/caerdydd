package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testGetAllConsultings_Nominal() throws CustomRuntimeException {
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
    public void testGetAllConsultings_Empty() throws CustomRuntimeException {
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
    public void testGetAllConsultings_UnexpectedError() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        when(consultingService.listAllPlannedTimingConsultings()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.getAllPlannedTimingConsultings();

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test
    public void testuploadConsulting_Nominal() throws CustomRuntimeException, IOException {
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
    public void testuploadConsulting_EmptyFile() throws CustomRuntimeException, IOException {
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
    public void testuploadConsulting_UnsupportedMediaType() throws CustomRuntimeException, IOException {
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
    public void testuploadConsulting_UnexpectedException() throws CustomRuntimeException, IOException {
        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "mock".getBytes());

        // Mock consultingService.uploadConsulting()
        when(consultingService.uploadPlannedTimingConsultings(file)).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.uploadPlannedTimingConsultings(file);

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test void testUpdateAvailability_Nominal() throws CustomRuntimeException {
        // Mock consultingService.updateAvailability()
        PlannedTimingAvailabilityDTO mockedConsulting = new PlannedTimingAvailabilityDTO();
        when(consultingService.updatePlannedTimingAvailability(mockedConsulting)).thenReturn(mockedConsulting);

        // Call method to test
        ResponseEntity<PlannedTimingAvailabilityDTO> response = consultingController.updateAvailability(mockedConsulting);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsulting, response.getBody());
    }
}