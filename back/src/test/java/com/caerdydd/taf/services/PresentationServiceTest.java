package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.project.PresentationDTO;
import com.caerdydd.taf.models.entities.project.PresentationEntity;
import com.caerdydd.taf.repositories.PresentationRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
class PresentationServiceTest {
    
    @InjectMocks
    private PresentationService presentationService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private PresentationRepository presentationRepository;

    @Test
    void testListAllPresentations_Nominal() throws CustomRuntimeException {
        //Mock presentationRepository.findAll() method
        List<PresentationEntity> mockedAnswer = new ArrayList<PresentationEntity>();
        mockedAnswer.add(new PresentationEntity(1));
        mockedAnswer.add(new PresentationEntity(2));
        when(presentationRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<PresentationDTO> expectedAnswer = new ArrayList<PresentationDTO>();
        expectedAnswer.add(new PresentationDTO(1));
        expectedAnswer.add(new PresentationDTO(2));

        //Call the method to test
        List<PresentationDTO> result = new ArrayList<PresentationDTO>();

        result = presentationService.listAllPresentations();

        // Verify the result
        verify(presentationRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testListAllPresentations_Empty() throws CustomRuntimeException {
        // Mock presentationRepository.findAll() method
        List<PresentationEntity> mockedAnswer = new ArrayList<PresentationEntity>();
        when(presentationRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<PresentationDTO> expectedAnswer = new ArrayList<PresentationDTO>();

        // Call the method to test
        List<PresentationDTO> result = new ArrayList<PresentationDTO>();

        result = presentationService.listAllPresentations();

        // Verify the result
        verify(presentationRepository, times(1)).findAll();
        assertEquals(0, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testListAllPresentations_ServiceError() {
        // Mock presentationRepository.findAll() method
        when(presentationRepository.findAll()).thenThrow(new NoSuchElementException());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrows(CustomRuntimeException.class, () -> presentationService.listAllPresentations());

        // Verify the result
        verify(presentationRepository, times(1)).findAll();
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    void testSavePresentation_Nominal() {
        //Mock presentationRepository.save() method
        PresentationEntity mockedAnswer = new PresentationEntity(1);
        when(presentationRepository.save(any(PresentationEntity.class))).thenReturn(mockedAnswer);

        //Prepare the input
        PresentationDTO presentationToSave = new PresentationDTO(1);

        //Call the method to test
        PresentationDTO result = presentationService.savePresentation(presentationToSave);

        //Check the result
        verify(presentationRepository, times(1)).save(any(PresentationEntity.class));
        assertEquals(presentationToSave.toString(), result.toString()); 
    }

    @Test
    public void testGetPresentationById_Nominal() throws CustomRuntimeException {
        // Mock presentationRepository.findById() method
        Optional<PresentationEntity> mockedAnswer = Optional.of(new PresentationEntity(1));
        when(presentationRepository.findById(any(Integer.class))).thenReturn(mockedAnswer);

        // Define the expected answer
        PresentationDTO expectedAnswer = new PresentationDTO(1);

        // Call the method to test
        PresentationDTO result = presentationService.getPresentationById(1);

        // Verify the result
        verify(presentationRepository, times(1)).findById(any(Integer.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testGetPresentationById_PresentationNotFound() {
        // Mock presentationRepository.findById() method
        Optional<PresentationEntity> mockedAnswer = Optional.empty();
        when(presentationRepository.findById(any(Integer.class))).thenReturn(mockedAnswer);

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> presentationService.getPresentationById(1));

        // Verufy the result
        verify(presentationRepository, times(1)).findById(any(Integer.class));
        assertEquals(CustomRuntimeException.PRESENTATION_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetPresentationById_ServiceError() {
        // Mock presentationRepository.findById() method
        when(presentationRepository.findById(any(Integer.class))).thenThrow(new NoSuchElementException());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrows(CustomRuntimeException.class, () -> presentationService.getPresentationById(1));

        // Verify the result
        verify(presentationRepository, times(1)).findById(any(Integer.class));
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }
}
