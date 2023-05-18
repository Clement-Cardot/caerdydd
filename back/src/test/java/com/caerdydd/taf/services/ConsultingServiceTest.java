package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingConsultingDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.consulting.PlannedTimingAvailabilityEntity;
import com.caerdydd.taf.models.entities.consulting.PlannedTimingConsultingEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.models.entities.user.UserEntity;
import com.caerdydd.taf.repositories.PlannedTimingAvailabilityRepository;
import com.caerdydd.taf.repositories.PlannedTimingConsultingRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.ConsultingRules;
import com.caerdydd.taf.services.rules.FileRules;
import com.caerdydd.taf.services.rules.UserServiceRules;


@ExtendWith(MockitoExtension.class)
public class ConsultingServiceTest {

    @InjectMocks
    private ConsultingService consultingService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private PlannedTimingConsultingRepository plannedTimingConsultingRepository;

    @Mock
    private PlannedTimingAvailabilityRepository plannedTimingAvailabilityRepository;

    @Mock
    private UserServiceRules userServiceRules;

    @Mock
    private ConsultingRules consultingRules;

    @Mock
    private TeachingStaffService teachingStaffService;

    @Mock 
    private FileRules fileRules;

    @BeforeEach
    public void setUp() {
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // ListAllConsultings
    @Test
    void testListAllPlannedTimingConsultings_Nominal() throws CustomRuntimeException {
        // Mock consultingRepository.findAll()
        List<PlannedTimingConsultingEntity> mockedConsultings = List.of(
            new PlannedTimingConsultingEntity(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                LocalDateTime.of(2023, 1, 1, 10, 30, 0)),
            new PlannedTimingConsultingEntity(
                LocalDateTime.of(2023, 1, 1, 11, 0, 0),
                LocalDateTime.of(2023, 1, 1, 11, 30, 0))
        );
        when(plannedTimingConsultingRepository.findAll()).thenReturn(mockedConsultings);

        // Call method to test
        List<PlannedTimingConsultingDTO> consultings = consultingService.listAllPlannedTimingConsultings();

        // Expected Answer
        List<PlannedTimingConsultingDTO> expectedConsultings = List.of(
            new PlannedTimingConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 10, 0, 0),
                LocalDateTime.of(2023, 1, 1, 10, 30, 0)),
            new PlannedTimingConsultingDTO(
                LocalDateTime.of(2023, 1, 1, 11, 0, 0),
                LocalDateTime.of(2023, 1, 1, 11, 30, 0))
        );

        // Assertions
        assertEquals(mockedConsultings.size(), consultings.size());
        assertEquals(expectedConsultings.get(0).toString(), consultings.get(0).toString());
        assertEquals(expectedConsultings.get(1).toString(), consultings.get(1).toString());
    }

    @Test
    void testListAllPlannedTimingConsultings_EmptyList() throws CustomRuntimeException {
        // Mock consultingRepository.findAll()
        List<PlannedTimingConsultingEntity> mockedConsultings = new ArrayList<>();
        when(plannedTimingConsultingRepository.findAll()).thenReturn(mockedConsultings);

        // Call method to test
        List<PlannedTimingConsultingDTO> consultings = consultingService.listAllPlannedTimingConsultings();

        // Assertions
        assertEquals(mockedConsultings.size(), consultings.size());
    }

    @Test
    void testListAllPlannedTimingConsultings_ServiceError() {
        // Mock consultingRepository.findAll()
        when(plannedTimingConsultingRepository.findAll()).thenThrow(new NullPointerException());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.listAllPlannedTimingConsultings();
        });
        
        // Assertions
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    // getPlannedTimingAvailabilityById
    @Test
    void testGetPlannedTimingAvailabilityById_Nominal() throws CustomRuntimeException{
        // Mock consultingRepository.findById()
        PlannedTimingConsultingEntity plannedTimingConsultingEntity = new PlannedTimingConsultingEntity(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            LocalDateTime.of(2023, 1, 1, 10, 30, 0)
        );
        TeachingStaffEntity teachingStaffEntity = new TeachingStaffEntity(new UserEntity(1, "firstname", "lastname", "login", "password", "email", null));
        PlannedTimingAvailabilityEntity mockedAvailability = new PlannedTimingAvailabilityEntity(plannedTimingConsultingEntity, teachingStaffEntity);
        mockedAvailability.setIdPlannedTimingAvailability(1);
        plannedTimingConsultingEntity.getTeachingStaffAvailabilities().add(mockedAvailability);

        when(plannedTimingAvailabilityRepository.findById(1)).thenReturn(Optional.of(mockedAvailability));

        // Call method to test
        PlannedTimingAvailabilityDTO consulting = consultingService.getPlannedTimingAvailabilityById(1);

        // Expected Answer
        PlannedTimingConsultingDTO plannedTimingConsultingDTO = new PlannedTimingConsultingDTO(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            LocalDateTime.of(2023, 1, 1, 10, 30, 0)
        );
        TeachingStaffDTO teachingStaffDTO = new TeachingStaffDTO(new UserDTO(1, "firstname", "lastname", "login", "password", "email", null));
        PlannedTimingAvailabilityDTO expectedConsulting = new PlannedTimingAvailabilityDTO(plannedTimingConsultingDTO, teachingStaffDTO);
        expectedConsulting.setIdPlannedTimingAvailability(1);

        // Assertions
        assertEquals(expectedConsulting.toString(), consulting.toString());
    }

    @Test
    void testGetPlannedTimingAvailabilityById_ServiceError() throws CustomRuntimeException{
        // Mock consultingRepository.findById()
        when(plannedTimingAvailabilityRepository.findById(1)).thenThrow(new NullPointerException());

        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.getPlannedTimingAvailabilityById(1);
        });
        
        // Assertions
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    void testGetPlannedTimingAvailabilityById_NotFound() throws CustomRuntimeException{
        // Mock consultingRepository.findById()
        when(plannedTimingAvailabilityRepository.findById(1)).thenReturn(Optional.empty());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.getPlannedTimingAvailabilityById(1);
        });
        
        // Assertions
        assertEquals(CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND, exception.getMessage());
    }

    // SavePlannedTimingConsultings
    @Test
    void testSavePlannedTimingConsultings_Nominal() {
        // mock consultingRepository.save()
        PlannedTimingConsultingEntity mockedConsulting = new PlannedTimingConsultingEntity(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0), 
            LocalDateTime.of(2023, 1, 1, 10, 30, 0)
        );
        when(plannedTimingConsultingRepository.saveAll(any())).thenReturn(List.of(mockedConsulting));

        // Call method to test
        PlannedTimingConsultingDTO input = new PlannedTimingConsultingDTO(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0), 
            LocalDateTime.of(2023, 1, 1, 10, 30, 0)
        );
        List<PlannedTimingConsultingDTO> response = consultingService.savePlannedTimingConsultings(List.of(input));

        // Assertions
        assertEquals(1, response.size());
        assertEquals(input.toString(), response.get(0).toString());
    }

    // SavePlannedTimingAvailability
    @Test
    void testSavePlannedTimingAvailability_Nominal() {
        // mock consultingRepository.save()
        when(plannedTimingAvailabilityRepository.save(any(PlannedTimingAvailabilityEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // Prepare input
        PlannedTimingConsultingDTO plannedTimingConsultingEntity = new PlannedTimingConsultingDTO(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0), 
            LocalDateTime.of(2023, 1, 1, 10, 30, 0)
        );
        TeachingStaffDTO teachingStaffEntity = new TeachingStaffDTO(new UserDTO(1, "firstname", "lastname", "login", "password", "email", null));
        PlannedTimingAvailabilityDTO input = new PlannedTimingAvailabilityDTO(plannedTimingConsultingEntity, teachingStaffEntity);

        // Call method to test
        PlannedTimingAvailabilityDTO response = consultingService.savePlannedTimingAvailability(input);

        // Assertions
        assertEquals(input.toString(), response.toString());
    }

    // UploadPlannedTimingConsultings
    @Test
    void testUploadPlannedTimingConsultings_Nominal() throws CustomRuntimeException, IOException {

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // Mock userServiceRules.checkCurrentUserRole()
        doNothing().when(userServiceRules).checkCurrentUserRole(any(String.class));

        // Mock fileRules.checkFileIsEmpty()
        doNothing().when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock fileRules.checkFileIsCSV()
        doNothing().when(fileRules).checkFileIsCSV(any(MultipartFile.class));

        // Mock teachingStaffService.listAllTeachingStaff()
        List<TeachingStaffDTO> mockedTeachingStaffs = List.of(
            new TeachingStaffDTO(new UserDTO(1, "prenom1", "nom1", "login1", "password1", "email1", null)),
            new TeachingStaffDTO(new UserDTO(2, "prenom2", "nom2", "login2", "password2", "email2", null))
        );
        when(teachingStaffService.listAllTeachingStaff()).thenReturn(mockedTeachingStaffs);

        // Mock consultingRepository.save()
        when(plannedTimingConsultingRepository.saveAll(any())).thenAnswer(i -> i.getArguments()[0]);

        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000,20230421T082000\n20230421T090000,20230421T092000".getBytes());

        // Call method to test
        List<PlannedTimingConsultingDTO> response = consultingService.uploadPlannedTimingConsultings(mockedFile);

        // Expected Answer
        List<PlannedTimingConsultingDTO> expectedAnswer = List.of(
            new PlannedTimingConsultingDTO(
            LocalDateTime.of(2023, 4, 21, 8, 0, 0),
            LocalDateTime.of(2023, 4, 21, 8, 20, 0)
            ), new PlannedTimingConsultingDTO(
            LocalDateTime.of(2023, 4, 21, 9, 0, 0),
            LocalDateTime.of(2023, 4, 21, 9, 20, 0)
        ));

        // Assertions
        assertEquals(expectedAnswer.size(), response.size());
        assertEquals(expectedAnswer.get(0).toString(), response.get(0).toString());
    }

    @Test
    void testUploadPlannedTimingConsultings_NotPlanningAssistant() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole()
        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT)).when(userServiceRules).checkCurrentUserRole(any(String.class));

        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000,20230421T082000\n20230421T090000,20230421T092000".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.uploadPlannedTimingConsultings(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT, exception.getMessage());
    }

    @Test
    void testUploadPlannedTimingConsultings_FileIsEmpty() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole()
        doNothing().when(userServiceRules).checkCurrentUserRole(any(String.class));

        // Mock fileRules.checkFileIsEmpty()
        doThrow(new CustomRuntimeException(CustomRuntimeException.FILE_IS_EMPTY)).when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000,20230421T082000\n20230421T090000,20230421T092000".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.uploadPlannedTimingConsultings(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.FILE_IS_EMPTY, exception.getMessage());
    }

    @Test
    void testUploadPlannedTimingConsultings_FileIsNotCSV() throws CustomRuntimeException {
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
            consultingService.uploadPlannedTimingConsultings(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }

    @Test
    void testUploadPlannedTimingConsultings_FileHasWrongCSVFormat() throws CustomRuntimeException {
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
            consultingService.uploadPlannedTimingConsultings(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }

    // ReadCsvFile
    @Test
    void testReadCsvFile_Nominal() throws CustomRuntimeException, IOException {
        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "20230421T080000,20230421T082000".getBytes());

        // Expected Answer
        List<PlannedTimingConsultingDTO> expecedAnswer = List.of(new PlannedTimingConsultingDTO(
            LocalDateTime.of(2023, 4, 21, 8, 0, 0),
            LocalDateTime.of(2023, 4, 21, 8, 20, 0))
        );

        // Call the method to test
        List<PlannedTimingConsultingDTO> response = consultingService.readCsvFile(mockedFile);

        // Assertions
        assertEquals(expecedAnswer.size(), response.size());
        assertEquals(expecedAnswer.get(0).toString(), response.get(0).toString());
    }

    @Test
    void testReadCsvFile_FileIsEmpty() throws CustomRuntimeException, IOException {
        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "".getBytes());

        // Expected Answer
        List<PlannedTimingConsultingDTO> expecedAnswer = new ArrayList<>();

        // Call the method to test
        List<PlannedTimingConsultingDTO> response = consultingService.readCsvFile(mockedFile);

        // Assertions
        assertEquals(expecedAnswer.size(), response.size());
    }

    @Test
    void testReadCsvFile_FileHasWrongCSVFormat() throws CustomRuntimeException {
        // Mock file
        MultipartFile mockedFile = new MockMultipartFile("file", "testcsv", "text/csv", "test,test,test".getBytes());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingService.readCsvFile(mockedFile);
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }
  
    // UpdatePlannedTimingAvailability
    @Test
    void testUpdatePlannedTimingAvailability() throws CustomRuntimeException {
        // Mock consultingRepository.findById()
        PlannedTimingConsultingEntity plannedTimingConsultingEntity = new PlannedTimingConsultingEntity(
            LocalDateTime.of(2023, 1, 1, 10, 0, 0),
            LocalDateTime.of(2023, 1, 1, 10, 30, 0)
        );
        TeachingStaffEntity teachingStaffEntity = new TeachingStaffEntity(new UserEntity(1, "firstname", "lastname", "login", "password", "email", null));
        PlannedTimingAvailabilityEntity mockedAvailability = new PlannedTimingAvailabilityEntity(plannedTimingConsultingEntity, teachingStaffEntity);
        mockedAvailability.setIdPlannedTimingAvailability(1);
        plannedTimingConsultingEntity.getTeachingStaffAvailabilities().add(mockedAvailability);

        when(plannedTimingAvailabilityRepository.findById(1)).thenReturn(Optional.of(mockedAvailability));

        // Mock userServiceRules.checkCurrentUserRole()
        doNothing().when(userServiceRules).checkCurrentUserRole("TEACHING_STAFF_ROLE");

        // Mock userServiceRules.getCurrentUser()
        UserDTO mockedUser = new UserDTO();
        mockedUser.setTeachingStaff(new TeachingStaffDTO());
        when(userServiceRules.getCurrentUser()).thenReturn(mockedUser);

        // Mock consultingRules.checkUserIsOwnerOfAvailability()
        doNothing().when(consultingRules).checkUserIsOwnerOfAvailability(any(TeachingStaffDTO.class), any(PlannedTimingAvailabilityDTO.class));

        // Mock consultingRules.checkPlannedTimingIsNotInPast()
        doNothing().when(consultingRules).checkPlannedTimingIsNotInPast(any(PlannedTimingAvailabilityDTO.class));

        // Mock consultingRules.checkPlannedTimingIsNotAlreadyTaken()
        doNothing().when(consultingRules).checkPlannedTimingIsNotAlreadyTaken(any(PlannedTimingAvailabilityDTO.class));

        // Mock plannedTimingAvailabilityRepository.save
        when(plannedTimingAvailabilityRepository.save(any(PlannedTimingAvailabilityEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // Prepare Input
        PlannedTimingAvailabilityDTO input = new PlannedTimingAvailabilityDTO();
        input.setIdPlannedTimingAvailability(1);
        input.setIsAvailable(true);

        // Call method to test
        PlannedTimingAvailabilityDTO result = consultingService.updatePlannedTimingAvailability(input);

        // Assertions
        assertEquals(input.getIdPlannedTimingAvailability(), result.getIdPlannedTimingAvailability());
        assertEquals(input.getIsAvailable(), result.getIsAvailable());
    }
    
}
