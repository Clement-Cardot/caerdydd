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
    void testGetAllPlannedTimingAvailabilities_Nominal() throws CustomRuntimeException {
        // Mock consultingService.listAllPlannedTimingAvailabilities()
        List<PlannedTimingAvailabilityDTO> mockedAvailabilities = List.of(
            new PlannedTimingAvailabilityDTO(),
            new PlannedTimingAvailabilityDTO()
        );
        when(consultingService.listAllPlannedTimingAvailabilities()).thenReturn(mockedAvailabilities);

        // Call method to test
        ResponseEntity<List<PlannedTimingAvailabilityDTO>> response = consultingController.getAllPlannedTimingAvailabilities();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedAvailabilities, response.getBody());
    }

    @Test
    void testGetAllPlannedTimingAvailabilities_Empty() throws CustomRuntimeException {
        // Mock consultingService.listAllPlannedTimingAvailabilities()
        List<PlannedTimingAvailabilityDTO> mockedAvailabilities = new ArrayList<>();
        when(consultingService.listAllPlannedTimingAvailabilities()).thenReturn(mockedAvailabilities);

        // Call method to test
        ResponseEntity<List<PlannedTimingAvailabilityDTO>> response = consultingController.getAllPlannedTimingAvailabilities();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockedAvailabilities, response.getBody());
    }

    @Test
    void testGetAllPlannedTimingAvailabilities_PlannedTimingAvailabilityNotFound() throws CustomRuntimeException {
        // Mock consultingService.listAllPlannedTimingAvailabilities()
        when(consultingService.listAllPlannedTimingAvailabilities()).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND));

        // Call method to test
        ResponseEntity<List<PlannedTimingAvailabilityDTO>> response = consultingController.getAllPlannedTimingAvailabilities();

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllPlannedTimingAvailabilities_ConflictError() throws CustomRuntimeException {
        // Mock consultingService.listAllPlannedTimingAvailabilities()
        when(consultingService.listAllPlannedTimingAvailabilities()).thenThrow(new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_ALREADY_TAKEN));

        // Call method to test
        ResponseEntity<List<PlannedTimingAvailabilityDTO>> response = consultingController.getAllPlannedTimingAvailabilities();

        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void testGetAllPlannedTimingAvailabilities_UnexpectedError() throws CustomRuntimeException {
        // Mock consultingService.listAllPlannedTimingAvailabilities()
        when(consultingService.listAllPlannedTimingAvailabilities()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<List<PlannedTimingAvailabilityDTO>> response = consultingController.getAllPlannedTimingAvailabilities();

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetAllPlannedTimingAvailabilities_UnhandledException() throws CustomRuntimeException {
        // Mock consultingService.listAllPlannedTimingAvailabilities()
        when(consultingService.listAllPlannedTimingAvailabilities()).thenThrow(new CustomRuntimeException("Some unhandled exception"));

        // Call method to test
        ResponseEntity<List<PlannedTimingAvailabilityDTO>> response = consultingController.getAllPlannedTimingAvailabilities();

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
    void testGetAllConsultings_ConsultingNotFound() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        when(consultingService.listAllConsultings()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FOUND));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getAllConsultings();

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllConsultings_ConflictError() throws CustomRuntimeException {
        // Mock consultingService.listAllConsultings()
        when(consultingService.listAllConsultings()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_ALREADY_TAKEN));

        // Call method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getAllConsultings();

        // Assertions
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
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
    void testGetConsultingsBySpecialityInfra_ConsultingNotFound() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityInfra() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityInfra()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FOUND));

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
    void testGetConsultingsBySpecialityInfra_ConsultingIsInPast() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityInfra() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityInfra()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_IN_PAST));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityInfra();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityInfra();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityInfra_ConsultingIsAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityInfra() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityInfra()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_ALREADY_TAKEN));

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
    void testGetConsultingsBySpecialityDev_ConsultingIsInPast() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_IN_PAST));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityDev();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityDevelopment();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityDev_ConsultingIsAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityDevelopment() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityDevelopment()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_ALREADY_TAKEN));

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

    // Get Consulting By Speciality Modeling
    @Test
    void testGetConsultingsBySpecialityModeling() throws CustomRuntimeException {

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
    void testGetConsultingsBySpecialityModel_ConsultingNotFound() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityModeling()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FOUND));

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
    void testGetConsultingsBySpecialityModel_ConsultingIsInPast() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityModeling()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_IN_PAST));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.FORBIDDEN);

        // Call the method to test
        ResponseEntity<List<ConsultingDTO>> response = consultingController.getConsultingsBySpecialityModel();

        // Verify the result
        Mockito.verify(consultingService, Mockito.times(1)).getConsultingsBySpecialityModeling();
        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testGetConsultingsBySpecialityModel_ConsultingIsAlreadyTaken() throws CustomRuntimeException {
        // Mock consultingService.getConsultingsBySpecialityModeling() pour lancer l'exception
        when(consultingService.getConsultingsBySpecialityModeling()).thenThrow(new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_ALREADY_TAKEN));

        // Define the expected answer
        ResponseEntity<List<ConsultingDTO>> expectedResponse = new ResponseEntity<>(HttpStatus.CONFLICT);

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
    void testuploadPlannedTimingConsulting_UnsupportedMediaType() throws CustomRuntimeException, IOException {
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
    void testuploadPlannedTimingConsulting_UnexpectedException() throws CustomRuntimeException, IOException {
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
    void testUpdateConsulting_UserNotTeachingStaff() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        when(consultingService.updateConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(new ConsultingDTO());

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
    void testUpdateConsulting_ServiceError() throws CustomRuntimeException {
        // Mock consultingService.updateConsulting()
        when(consultingService.updateConsulting(any())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call method to test
        ResponseEntity<ConsultingDTO> response = consultingController.updateConsulting(new ConsultingDTO());

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

}