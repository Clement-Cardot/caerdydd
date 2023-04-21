package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.ConsultingDTO;
import com.caerdydd.taf.models.entities.ConsultingEntity;
import com.caerdydd.taf.repositories.ConsultingRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.FileRules;
import com.caerdydd.taf.services.rules.UserServiceRules;


@ExtendWith(MockitoExtension.class)
public class ConsultingServiceTest {

    @InjectMocks
    private ConsultingService consultingService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private ConsultingRepository consultingRepository;

    @Mock
    private UserServiceRules userServiceRules;

    @Mock 
    private FileRules fileRules;

    // ListAllConsultings
    @Test
    public void testListAllConsultings_Nominal() throws CustomRuntimeException {
        // Mock consultingRepository.findAll()
        List<ConsultingEntity> mockedConsultings = List.of(
            new ConsultingEntity(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                LocalDateTime.of(2023, 1, 1, 10, 30, 0)),
            new ConsultingEntity(
                LocalDateTime.of(2023, 1, 1, 11, 0, 0),
                LocalDateTime.of(2023, 1, 1, 11, 30, 0))
        );
        when(consultingRepository.findAll()).thenReturn(mockedConsultings);

        // Call method to test
        List<ConsultingDTO> consultings = consultingService.listAllConsultings();

        // Expected Answer
        List<ConsultingDTO> expectedConsultings = List.of(
            new ConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                LocalDateTime.of(2023, 1, 1, 10, 30, 0)),
            new ConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 11, 0, 0),
                LocalDateTime.of(2023, 1, 1, 11, 30, 0))
        );

        // Assertions
        assertEquals(mockedConsultings.size(), consultings.size());
        assertEquals(expectedConsultings.get(0).toString(), consultings.get(0).toString());
        assertEquals(expectedConsultings.get(1).toString(), consultings.get(1).toString());
    }

    @Test
    public void testListAllConsultings_EmptyList() throws CustomRuntimeException {
        // Mock consultingRepository.findAll()
        List<ConsultingEntity> mockedConsultings = new ArrayList<>();
        when(consultingRepository.findAll()).thenReturn(mockedConsultings);

        // Call method to test
        List<ConsultingDTO> consultings = consultingService.listAllConsultings();

        // Assertions
        assertEquals(mockedConsultings.size(), consultings.size());
    }

    @Test
    public void testListAllConsultings_ServiceError() {
        // Mock consultingRepository.findAll()
        when(consultingRepository.findAll()).thenThrow(new NullPointerException());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.listAllConsultings();
        });
        
        // Assertions
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    // SaveConsulting
    @Test
    public void testSaveConsulting_Nominal() {
        // mock consultingRepository.save()
        ConsultingEntity mockedConsulting = new ConsultingEntity(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0), 
            LocalDateTime.of(2023, 1, 1, 10, 30, 0)
        );
        when(consultingRepository.save(any(ConsultingEntity.class))).thenReturn(mockedConsulting);

        // Call method to test
        ConsultingDTO input = new ConsultingDTO(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0), 
            LocalDateTime.of(2023, 1, 1, 10, 30, 0)
        );
        ConsultingDTO response = consultingService.saveConsulting(input);

        // Assertions
        assertEquals(input.toString(), response.toString());
    }

    // UploadConsultings
    @Test
    public void testuploadConsulting_Nominal() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole()
        doNothing().when(userServiceRules).checkCurrentUserRole(any(String.class));

        // Mock fileRules.checkFileIsEmpty()
        doNothing().when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock fileRules.checkFileIsCSV()
        doNothing().when(fileRules).checkFileIsCSV(any(MultipartFile.class));

        // Mock consultingRepository.save()
        when(consultingRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000,20230421T082000\n20230421T090000,20230421T092000".getBytes());

        // Call method to test
        List<ConsultingDTO> response = consultingService.uploadConsultings(mockedFile);

        // Expected Answer
        List<ConsultingDTO> expectedAnswer = List.of(
            new ConsultingDTO(
            LocalDateTime.of(2023, 4, 21, 8, 0, 0),
            LocalDateTime.of(2023, 4, 21, 8, 20, 0)
            ), new ConsultingDTO(
            LocalDateTime.of(2023, 4, 21, 9, 0, 0),
            LocalDateTime.of(2023, 4, 21, 9, 20, 0)
        ));

        // Assertions
        assertEquals(expectedAnswer.size(), response.size());
        assertEquals(expectedAnswer.get(0).toString(), response.get(0).toString());
    }

    @Test
    public void testuploadConsulting_NotPlanningAssistant() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole()
        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT)).when(userServiceRules).checkCurrentUserRole(any(String.class));

        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000,20230421T082000\n20230421T090000,20230421T092000".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.uploadConsultings(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT, exception.getMessage());
    }

    @Test
    public void testuploadConsulting_FileIsEmpty() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole()
        doNothing().when(userServiceRules).checkCurrentUserRole(any(String.class));

        // Mock fileRules.checkFileIsEmpty()
        doThrow(new CustomRuntimeException(CustomRuntimeException.FILE_IS_EMPTY)).when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000,20230421T082000\n20230421T090000,20230421T092000".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.uploadConsultings(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.FILE_IS_EMPTY, exception.getMessage());
    }

    @Test
    public void testuploadConsulting_FileIsNotCSV() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole()
        doNothing().when(userServiceRules).checkCurrentUserRole(any(String.class));

        // Mock fileRules.checkFileIsEmpty()
        doNothing().when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock fileRules.checkFileIsCSV()
        doThrow(new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT)).when(fileRules).checkFileIsCSV(any(MultipartFile.class));

        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000,20230421T082000\n20230421T090000,20230421T092000".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.uploadConsultings(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }

    @Test
    public void testuploadConsulting_FileHasWrongCSVFormat() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole()
        doNothing().when(userServiceRules).checkCurrentUserRole(any(String.class));

        // Mock fileRules.checkFileIsEmpty()
        doNothing().when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock fileRules.checkFileIsCSV()
        doNothing().when(fileRules).checkFileIsCSV(any(MultipartFile.class));

        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000;20230421T082000".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.uploadConsultings(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }

    // ReadCsvFile
    @Test
    public void testReadCsvFile_Nominal() throws CustomRuntimeException {
        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000,20230421T082000".getBytes());

        // Expected Answer
        List<ConsultingDTO> expecedAnswer = List.of(new ConsultingDTO(
            LocalDateTime.of(2023, 4, 21, 8, 0, 0),
            LocalDateTime.of(2023, 4, 21, 8, 20, 0))
        );

        // Call the method to test
        List<ConsultingDTO> response = consultingService.readCsvFile(mockedFile);

        // Assertions
        assertEquals(expecedAnswer.size(), response.size());
        assertEquals(expecedAnswer.get(0).toString(), response.get(0).toString());
    }

    @Test
    public void testReadCsvFile_FileIsEmpty() throws CustomRuntimeException {
        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "".getBytes());

        // Expected Answer
        List<ConsultingDTO> expecedAnswer = new ArrayList<>();

        // Call the method to test
        List<ConsultingDTO> response = consultingService.readCsvFile(mockedFile);

        // Assertions
        assertEquals(expecedAnswer.size(), response.size());
    }

    @Test
    public void testReadCsvFile_FileHasWrongCSVFormat() throws CustomRuntimeException {
        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "test,test,test".getBytes());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.readCsvFile(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }
  
    
}