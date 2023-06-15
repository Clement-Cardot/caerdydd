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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.consulting.ConsultingDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingConsultingDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.ConsultingService;

@ExtendWith(MockitoExtension.class)
public class ConsultingControllerTest {

    @InjectMocks
    private ConsultingController consultingController;

    @Mock
    private ConsultingService consultingService;

    @Test
    void testGetAllPlannedTimingConsultings_Nominal() throws CustomRuntimeException {
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
    void testGetAllPlannedTimingConsultings_Empty() throws CustomRuntimeException {
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
    void testGetAllPlannedTimingConsultings_UnexpectedError() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        when(consultingService.listAllPlannedTimingConsultings()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.getAllPlannedTimingConsultings();

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test
    void testGetAllConsultings_Nominal() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        List<ConsultingDTO> mockedConsultings = List.of(
            new ConsultingDTO(),
            new ConsultingDTO()
        );
        when(consultingService.listAllConsultings()).thenReturn(mockedConsultings);

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getAllConsultings();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testGetAllConsultings_Empty() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        List<ConsultingDTO> mockedConsultings = new ArrayList<>();
        when(consultingService.listAllConsultings()).thenReturn(mockedConsultings);

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getAllConsultings();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testGetAllConsultings_UnexpectedError() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        when(consultingService.listAllConsultings()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getAllConsultings();

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetAllConsultings_UnhandledException() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        when(consultingService.listAllConsultings()).thenThrow(new CustomRuntimeException("Some unhandled exception"));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getAllConsultings();

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    // Get Consulting By Speciality Infrastructure
    @Test
    void testGetConsultingsBySpecialityInfra_Nominal() throws CustomRuntimeException {

        // Mock listAllConsultings() method
        List<ConsultingDTO> allConsultings = new ArrayList<>();
        ConsultingDTO consulting1 = new ConsultingDTO();
        ConsultingDTO consulting2 = new ConsultingDTO();
        ConsultingDTO consulting3 = new ConsultingDTO();
        consulting1.setSpeciality("infrastructure");
        consulting1.setIdConsulting(1);
        consulting2.setSpeciality("development");
        consulting2.setIdConsulting(2);
        consulting3.setSpeciality("infrastructure");
        consulting3.setIdConsulting(3);
        allConsultings.add(consulting1);
        allConsultings.add(consulting2);
        allConsultings.add(consulting3);

        // Mock consultingService.getConsultingsBySpecialityInfra()
        when(consultingService.getConsultingsBySpecialityInfra()).thenReturn(allConsultings);

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(allConsultings, HttpStatus.OK);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityInfra();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityInfra();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    void testGetConsultingsBySpecialityInfra_PlannedTimingAvailabilityNotFound() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityInfra()).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityInfra();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityInfra();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityInfra_UserNotTeachingStaff() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityInfra() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityInfra()).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityInfra();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityInfra();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityInfra_UserNotOwnerOfAvailability() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityInfra() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityInfra()).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityInfra();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityInfra();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityInfra_PlannedTimingIsAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityInfra()).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_ALREADY_TAKEN));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.CONFLICT);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityInfra();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityInfra();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityInfra_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityInfra() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityInfra()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityInfra();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityInfra();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityInfra_UnexpectedError() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityInfra()).thenThrow(new CustomRuntimeException("UnexpectedError"));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityInfra();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityInfra();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    // Get Consulting By Speciality Development
    @Test
    void testGetConsultingsBySpecialityDev_Nominal() throws CustomRuntimeException {

        // Mock listAllConsultings() method
        List<ConsultingDTO> allConsultings = new ArrayList<>();
        ConsultingDTO consulting1 = new ConsultingDTO();
        ConsultingDTO consulting2 = new ConsultingDTO();
        ConsultingDTO consulting3 = new ConsultingDTO();
        consulting1.setSpeciality("infrastructure");
        consulting1.setIdConsulting(1);
        consulting2.setSpeciality("development");
        consulting2.setIdConsulting(2);
        consulting3.setSpeciality("infrastructure");
        consulting3.setIdConsulting(3);
        allConsultings.add(consulting1);
        allConsultings.add(consulting2);
        allConsultings.add(consulting3);

        // Mock consultingService.getConsultingsBySpecialityInfra()
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenReturn(allConsultings);

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(allConsultings, HttpStatus.OK);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    void testGetConsultingsBySpecialityDev_ConsultingNotFound() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FOUND));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityDev_PlannedTimingAvailabilityNotFound() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }
    
    @Test
    void testGetConsultingsBySpecialityDev_UserNotTeachingStaff() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityDev_UserNotOwnerOfAvailability() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityDev_PlannedTimingIsInPast() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_IN_PAST));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityDev_PlannedTimingIsAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_ALREADY_TAKEN));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.CONFLICT);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }
    
    @Test
    void testGetConsultingsBySpecialityDev_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityDev_UnexpectedError() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException("UnexpectedError"));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    // Get Consulting By Speciality Modeling
    @Test
    void testGetConsultingsBySpecialityModeling_Nominal() throws CustomRuntimeException {

        // Mock listAllConsultings() method
        List<ConsultingDTO> allConsultings = new ArrayList<>();
        ConsultingDTO consulting1 = new ConsultingDTO();
        ConsultingDTO consulting2 = new ConsultingDTO();
        ConsultingDTO consulting3 = new ConsultingDTO();
        consulting1.setSpeciality("modeling");
        consulting1.setIdConsulting(1);
        consulting2.setSpeciality("development");
        consulting2.setIdConsulting(2);
        consulting3.setSpeciality("infrastructure");
        consulting3.setIdConsulting(3);
        allConsultings.add(consulting1);
        allConsultings.add(consulting2);
        allConsultings.add(consulting3);

        // Mock consultingService.getConsultingsBySpecialityInfra()
        when(consultingService.getConsultingsBySpecialityModeling()).thenReturn(allConsultings);

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(allConsultings, HttpStatus.OK);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityModel();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityModeling();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }

    @Test
    void testGetConsultingsBySpecialityModel_PlannedTimingAvailabilityNotFound() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityModeling()).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityModel();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityModeling();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }


    @Test
    void testGetConsultingsBySpecialityModel_UserNotTeachingStaff() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityModeling()).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityModel();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityModeling();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityModel_PlannedTimingIsAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityModeling()).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_ALREADY_TAKEN));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.CONFLICT);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityModel();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityModeling();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityModel_UserNotOwnerOfAvailability() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityModeling()).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityModel();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityModeling();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityModel_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityModeling()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityModel();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityModeling();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityModel_UnexpectedError() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityModeling()).thenThrow(new CustomRuntimeException("UnexpectedError"));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityModel();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityModeling();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testuploadPlannedTimingConsulting_Nominal() throws CustomRuntimeException, IOException {
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
    void testuploadPlannedTimingConsulting_EmptyFile() throws CustomRuntimeException, IOException {
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
    void testUploadPlannedTimingConsulting_ConsultingAlreadyExist() throws CustomRuntimeException, IOException {
        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "".getBytes());

        // Mock consultingService.uploadConsulting()
        when(consultingService.uploadPlannedTimingConsultings(file)).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_CONSULTING_ALREADY_EXISTS));

        // Call method to test
        ResponseEntity<List<PlannedTimingConsultingDTO>> response = consultingController.uploadPlannedTimingConsultings(file);

        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testUploadPlannedTimingConsulting_UnsupportedMediaType() throws CustomRuntimeException, IOException {
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
    void testUploadPlannedTimingConsulting_UnexpectedException() throws CustomRuntimeException, IOException {
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
    void testUpdateConsulting_Nominal() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        ConsultingDTO mockedConsulting = new ConsultingDTO();
        when(consultingService.updateConsulting(mockedConsulting)).thenReturn(mockedConsulting);

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(mockedConsulting);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsulting, response.getBody());
    }

    @Test
    void testUpdateConsulting_ConsultingNotFound() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        when(consultingService.updateConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FOUND));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreateConsulting_Nominal() throws CustomRuntimeException {
        // Mock consultingService.createConsulting()
        ConsultingDTO inputConsulting = new ConsultingDTO(/* initialize with necessary data */);
        ConsultingDTO mockedConsulting = new ConsultingDTO(/* initialize with expected data */);
        when(consultingService.createConsulting(inputConsulting)).thenReturn(mockedConsulting);

        // Call the method to test
        ResponseEntity<ConsultingDTO> response = consultingController.createConsulting(inputConsulting);

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
    void testCreateConsulting_PlannedTimingIsInPast() throws CustomRuntimeException {
        // Mock consultingService.createConsulting()
        when(consultingService.createConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_IN_PAST));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.createConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testUpdateConsulting_UserNotTeachingStaff() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        when(consultingService.updateConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(new ConsultingDTO());
        
         // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
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

    @Test
    void testGetConsultingsForCurrentTeachingStaff_Nominal() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForCurrentTeachingStaff()
        PlannedTimingAvailabilityDTO mockedAvailabilityDTO = new PlannedTimingAvailabilityDTO(
            new PlannedTimingConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                LocalDateTime.of(2023, 1, 1, 10, 30, 0)
            ),
            new TeachingStaffDTO(
                new UserDTO(1, "Bob", "Smith", "login", "password", "email", null)
            )
        );
        ConsultingDTO mockedConsultingDTO = new ConsultingDTO();
        mockedConsultingDTO.setPlannedTimingAvailability(mockedAvailabilityDTO);
        List<ConsultingDTO> mockedConsultings = List.of(mockedConsultingDTO);
        when(consultingService.getConsultingsForCurrentTeachingStaff()).thenReturn(mockedConsultings);

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForCurrentTeachingStaff();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testGetConsultingsForCurrentTeachingStaff_Empty() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForCurrentTeachingStaff()
        List<ConsultingDTO> mockedConsultings = new ArrayList<>();
        when(consultingService.getConsultingsForCurrentTeachingStaff()).thenReturn(mockedConsultings);

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForCurrentTeachingStaff();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testGetConsultingsForCurrentTeachingStaff_UserNotTeachingStaff() throws CustomRuntimeException{
        // Mock consultingService.getConsultingsForCurrentTeachingStaff()
        when(consultingService.getConsultingsForCurrentTeachingStaff()).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForCurrentTeachingStaff();

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testUpdateConsulting_UserIsNotOwnerOfAvailability() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        when(consultingService.updateConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testUpdateConsulting_ConsultingIsPast() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        when(consultingService.updateConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_IN_PAST));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(new ConsultingDTO());

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testUpdateConsulting_ConsultingIsAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        when(consultingService.updateConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_ALREADY_TAKEN));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(new ConsultingDTO());
        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
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
    void testUpdateConsulting_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        when(consultingService.updateConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(new ConsultingDTO());
        
        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetConsultingsForCurrentTeachsingStaff_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForCurrentTeachingStaff()
        when(consultingService.getConsultingsForCurrentTeachingStaff()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForCurrentTeachingStaff();

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testUpdateConsulting_UnexpectedException() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        when(consultingService.updateConsulting(any())).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(new ConsultingDTO());
        
        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }
    
    @Test
    void testGetConsultingsForCurrentTeachsingStaff_UnexpectedException() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForCurrentTeachingStaff()
        when(consultingService.getConsultingsForCurrentTeachingStaff()).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForCurrentTeachingStaff();

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test
    void testGetConsultingsForATeam_Nominal() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForATeam()
        PlannedTimingAvailabilityDTO mockedAvailabilityDTO = new PlannedTimingAvailabilityDTO(
            new PlannedTimingConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                LocalDateTime.of(2023, 1, 1, 10, 30, 0)
            ),
            new TeachingStaffDTO(
                new UserDTO(1, "Bob", "Smith", "login", "password", "email", null)
            )
        );
        ConsultingDTO mockedConsultingDTO = new ConsultingDTO();
        mockedConsultingDTO.setPlannedTimingAvailability(mockedAvailabilityDTO);
        List<ConsultingDTO> mockedConsultings = List.of(mockedConsultingDTO);
        when(consultingService.getConsultingsForATeam(any())).thenReturn(mockedConsultings);

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForATeam(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testGetConsultingsForATeam_Empty() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForATeam()
        List<ConsultingDTO> mockedConsultings = new ArrayList<>();
        when(consultingService.getConsultingsForATeam(any())).thenReturn(mockedConsultings);

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForATeam(1);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testGetConsultingsForATeam_UserIsNotAuthorized() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForATeam()
        when(consultingService.getConsultingsForATeam(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AUTHORIZED));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForATeam(1);

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testGetConsultingsForATeam_UserIsNotInAssociatedTeam() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForATeam()
        when(consultingService.getConsultingsForATeam(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_IN_ASSOCIATED_TEAM));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForATeam(1);

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testGetConsultingsForATeam_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForATeam()
        when(consultingService.getConsultingsForATeam(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForATeam(1);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetConsultingsForATeam_UnexpectedException() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsForATeam()
        when(consultingService.getConsultingsForATeam(any())).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsForATeam(1);

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test
    void testSetNotesConsulting_Nominal() throws CustomRuntimeException {
        // Mock ConsultingService.setNotesConsulting()
        ConsultingDTO mockedConsultingDTO = new ConsultingDTO();
        when(consultingService.setNotesConsulting(any(), any())).thenReturn(mockedConsultingDTO);

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.setNotesConsulting("1", "notes");

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultingDTO, response.getBody());

    }

    @Test
    void testSetNotesConsulting_UserNotATeachingStaff() throws CustomRuntimeException {
        // Mock ConsultingService.setNotesConsulting()
        when(consultingService.setNotesConsulting(any(), any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.setNotesConsulting("1", "notes");

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testSetNotesConsulting_User_Is_Not_Owner_Of_Consulting() throws CustomRuntimeException {
        // Mock ConsultingService.setNotesConsulting()
        when(consultingService.setNotesConsulting(any(), any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_OWNER_OF_CONSULTING));
        
        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.setNotesConsulting("1", "notes");

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testSetNotesConsulting_ConsultingNotFinished() throws CustomRuntimeException {
        // Mock ConsultingService.setNotesConsulting()
        when(consultingService.setNotesConsulting(any(), any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FINISHED));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.setNotesConsulting("1", "notes");

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testSetNotesConsulting_ConsultingNotFound() throws CustomRuntimeException {
        // Mock ConsultingService.setNotesConsulting()
        when(consultingService.setNotesConsulting(any(), any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FOUND));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.setNotesConsulting("1", "notes");

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSetNoteConsulting_ServiceError() throws CustomRuntimeException {
        // Mock ConsultingService.setNotesConsulting()
        when(consultingService.setNotesConsulting(any(), any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.setNotesConsulting("1", "notes");

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testSetNotesConsulting_UnexpedtedException() throws CustomRuntimeException {
        // Mock ConsultingService.setNotesConsulting()
        when(consultingService.setNotesConsulting(any(), any())).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.setNotesConsulting("1", "notes");

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }

    @Test
    void testReplaceTeachingStaff_nominal() throws CustomRuntimeException {
        // Mock consultingService.replaceTeachingStaff()
        ConsultingDTO inputConsulting = new ConsultingDTO(/* initialize with necessary data */);
        ConsultingDTO mockedConsulting = new ConsultingDTO(/* initialize with expected data */);
        when(consultingService.replaceTeachingStaff(inputConsulting)).thenReturn(mockedConsulting);

        // Call the method to test
        ResponseEntity<ConsultingDTO> response = consultingController.replaceTeachingStaff(inputConsulting);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsulting, response.getBody());
    }

    @Test
    void testReplaceTeachingStaff_ConsultingNotFound() throws CustomRuntimeException {
        // Mock consultingService.replaceTeachingStaff()
        ConsultingDTO inputConsulting = new ConsultingDTO(/* initialize with necessary data */);
        when(consultingService.replaceTeachingStaff(inputConsulting)).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FOUND));

        // Call the method to test
        ResponseEntity<ConsultingDTO> response = consultingController.replaceTeachingStaff(inputConsulting);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testReplaceTeachingStaff_UserNotATeachingStaff() throws CustomRuntimeException {
        // Mock consultingService.replaceTeachingStaff()
        ConsultingDTO inputConsulting = new ConsultingDTO(/* initialize with necessary data */);
        when(consultingService.replaceTeachingStaff(inputConsulting)).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Call the method to test
        ResponseEntity<ConsultingDTO> response = consultingController.replaceTeachingStaff(inputConsulting);

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testReplaceTeachingStaff_TeachingStaffNotAvailable() throws CustomRuntimeException {
        // Mock consultingService.replaceTeachingStaff()
        ConsultingDTO inputConsulting = new ConsultingDTO(/* initialize with necessary data */);
        when(consultingService.replaceTeachingStaff(inputConsulting)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_IS_NOT_AVAILABLE));

        // Call the method to test
        ResponseEntity<ConsultingDTO> response = consultingController.replaceTeachingStaff(inputConsulting);

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testReplaceTeachingStaff_ConsultingInPast() throws CustomRuntimeException {
        // Mock consultingService.replaceTeachingStaff()
        ConsultingDTO inputConsulting = new ConsultingDTO(/* initialize with necessary data */);
        when(consultingService.replaceTeachingStaff(inputConsulting)).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_IN_PAST));

        // Call the method to test
        ResponseEntity<ConsultingDTO> response = consultingController.replaceTeachingStaff(inputConsulting);

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testReplaceTeachingStaff_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.replaceTeachingStaff()
        ConsultingDTO inputConsulting = new ConsultingDTO(/* initialize with necessary data */);
        when(consultingService.replaceTeachingStaff(inputConsulting)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call the method to test
        ResponseEntity<ConsultingDTO> response = consultingController.replaceTeachingStaff(inputConsulting);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testReplaceTeachingStaff_UnexpectedException() throws CustomRuntimeException {
        // Mock consultingService.replaceTeachingStaff()
        ConsultingDTO inputConsulting = new ConsultingDTO(/* initialize with necessary data */);
        when(consultingService.replaceTeachingStaff(inputConsulting)).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call the method to test
        ResponseEntity<ConsultingDTO> response = consultingController.replaceTeachingStaff(inputConsulting);

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }
    @Test
    void testGetConsultingsWaiting() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultingsWaiting()
        List<ConsultingDTO> mockedConsultings = List.of(
            new ConsultingDTO(/* initialize with necessary data */),
            new ConsultingDTO(/* initialize with necessary data */)
        );
        when(consultingService.listAllConsultingsWaiting()).thenReturn(mockedConsultings);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsWaiting();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedConsultings, response.getBody());
    }

    @Test
    void testGetConsultingsWaiting_ConsultingNotFound() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultingsWaiting()
        when(consultingService.listAllConsultingsWaiting()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FOUND));

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsWaiting();

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetConsultingsWaiting_UserNotATeachingStaff() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultingsWaiting()
        when(consultingService.listAllConsultingsWaiting()).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsWaiting();

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testGetConsultingsWaiting_UserNotOwnerOfAvailability() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultingsWaiting()
        when(consultingService.listAllConsultingsWaiting()).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY));

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsWaiting();

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testGetConsultingsWaiting_ConsultingInPast() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultingsWaiting()
        when(consultingService.listAllConsultingsWaiting()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_IN_PAST));

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsWaiting();

        // Assertions
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void testGetConsultingsWaiting_ConsultingAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultingsWaiting()
        when(consultingService.listAllConsultingsWaiting()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_ALREADY_TAKEN));

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsWaiting();

        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testGetConsultingsWaiting_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultingsWaiting()
        when(consultingService.listAllConsultingsWaiting()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsWaiting();

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetConsultingsWaiting_UnexpectedException() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultingsWaiting()
        when(consultingService.listAllConsultingsWaiting()).thenThrow(new CustomRuntimeException("Unexpected exception"));

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsWaiting();

        // Assertions
        assertEquals(HttpStatus.I_AM_A_TEAPOT, response.getStatusCode());
    }
}