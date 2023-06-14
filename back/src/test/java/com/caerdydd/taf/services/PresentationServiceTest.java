package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.caerdydd.taf.models.dto.project.PresentationDTO;
import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.project.PresentationEntity;
import com.caerdydd.taf.models.entities.project.ProjectEntity;
import com.caerdydd.taf.models.entities.project.TeamEntity;
import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.models.entities.user.TeamMemberEntity;
import com.caerdydd.taf.models.entities.user.UserEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.PresentationRepository;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.PresentationServiceRule;
import com.caerdydd.taf.services.rules.UserServiceRules;

@ExtendWith(MockitoExtension.class)
class PresentationServiceTest {
    
    @InjectMocks
    private PresentationService presentationService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private PresentationRepository presentationRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectService projectService;

    @Mock
    private JuryRepository juryRepository;

    @Mock
    private PresentationServiceRule presentationServiceRule;

    @Mock
    private UserServiceRules userServiceRules;


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
    void testGetPresentationById_Nominal() throws CustomRuntimeException {
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
    void testGetPresentationById_PresentationNotFound() {
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


@Test
void testCreatePresentation_Nominal() throws CustomRuntimeException {
    // Prepare test data
    PresentationDTO presentationDTO = new PresentationDTO(1);

    JuryDTO juryDTO = new JuryDTO();
    juryDTO.setIdJury(1);
    presentationDTO.setJury(juryDTO);

    ProjectDTO projectDTO = new ProjectDTO();
    projectDTO.setIdProject(1);
    presentationDTO.setProject(projectDTO);
    presentationDTO.setType("Présentation intermédiaire");

    presentationDTO.setDatetimeBegin(LocalDateTime.now());
    presentationDTO.setDatetimeEnd(LocalDateTime.now().plusHours(1));

    // Prepare entities for repository response
    JuryEntity juryEntity = new JuryEntity(juryDTO.getIdJury());
    ProjectEntity projectEntity = new ProjectEntity(projectDTO.getIdProject(), null, null, null);
    PresentationEntity presentationEntity = new PresentationEntity(1);
    presentationEntity.setJury(juryEntity);

    // Mock dependencies
    when(modelMapper.map(presentationDTO, PresentationEntity.class)).thenReturn(presentationEntity);
    when(modelMapper.map(presentationEntity, PresentationDTO.class)).thenReturn(presentationDTO); 
    when(presentationRepository.save(any(PresentationEntity.class))).thenReturn(presentationEntity);
    when(projectService.getProjectById(projectDTO.getIdProject())).thenReturn(projectDTO);
    doNothing().when(presentationServiceRule).checkJuryExists(presentationDTO.getJury().getIdJury());
    doNothing().when(presentationServiceRule).checkProjectExists(presentationDTO.getProject().getIdProject());
    doNothing().when(userServiceRules).checkCurrentUserRole("PLANNING_ROLE");
    doNothing().when(presentationServiceRule).checkPresentationTimeframe(presentationDTO.getDatetimeBegin(), presentationDTO.getDatetimeEnd());
    doNothing().when(presentationServiceRule).checkTeachingStaffAvailability(presentationDTO.getJury().getIdJury(), presentationDTO.getDatetimeBegin(), presentationDTO.getDatetimeEnd());

    // Call method
    PresentationDTO result = presentationService.createPresentation(presentationDTO);

    // Check results
    verify(presentationRepository, times(1)).save(presentationEntity);
    assertNotNull(result);
    assertEquals(presentationDTO.getIdPresentation(), result.getIdPresentation());
    assertEquals(presentationDTO.getJury().getIdJury(), result.getJury().getIdJury());
    assertEquals(presentationDTO.getProject().getIdProject(), result.getProject().getIdProject());
    assertEquals(presentationDTO.getDatetimeBegin(), result.getDatetimeBegin());
    assertEquals(presentationDTO.getDatetimeEnd(), result.getDatetimeEnd());
}

    

    @Test
    void testGetTeamPresentations_Nominal() throws CustomRuntimeException {
        // Mock methods
        Integer teamId = 1;
        TeamEntity team = new TeamEntity();
        team.setProjectDev(new ProjectEntity());  // Setting a ProjectEntity to avoid null
        team.setProjectValidation(new ProjectEntity());  // Setting a ProjectEntity to avoid null
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
    
        List<PresentationEntity> presentations = new ArrayList<>();
        presentations.add(new PresentationEntity(1));
        when(presentationRepository.findByProject(any(ProjectEntity.class))).thenReturn(presentations);
    
        // Call method
        List<PresentationDTO> result = presentationService.getTeamPresentations(teamId);
    
        // Check results
        verify(presentationRepository, times(2)).findByProject(any(ProjectEntity.class)); // because we called it twice in the method
        assertEquals(presentations.size() * 2, result.size()); // expecting two presentations because we have two projects

    }
    

@Test
void testGetTeachingStaffPresentations_Nominal() throws CustomRuntimeException {
    // Mock methods
    Integer staffId = 1;
    List<PresentationEntity> presentations = new ArrayList<>();
    presentations.add(new PresentationEntity(1));
    when(presentationRepository.findByTeachingStaff(staffId)).thenReturn(presentations);

    // Call method
    List<PresentationDTO> result = presentationService.getTeachingStaffPresentations(staffId);

    // Check results
    verify(presentationRepository, times(1)).findByTeachingStaff(staffId);
    assertEquals(presentations.size(), result.size());
}

@Test
void testGetTeamPresentations_TeamNotFound() {
    // Mock methods
    Integer teamId = 1;
    when(teamRepository.findById(teamId)).thenReturn(Optional.empty()); // Return empty optional to trigger the exception

    // Call method and catch the exception
    CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
        presentationService.getTeamPresentations(teamId);
    });

    // Check the exception message
    assertEquals(CustomRuntimeException.TEAM_NOT_FOUND, exception.getMessage());

    // Check interactions
    verify(presentationRepository, times(0)).findByProject(any(ProjectEntity.class)); // It should not be called because of the exception
}

@Test
void testGetTeachingStaffPresentations_ServiceError() {
    // Mock methods
    Integer staffId = 1;
    List<PresentationEntity> presentations = new ArrayList<>();
    presentations.add(new PresentationEntity(1));
    when(presentationRepository.findByTeachingStaff(staffId)).thenReturn(presentations);

    // Spy on the ModelMapper and make it throw an exception when trying to map
    ModelMapper spyMapper = Mockito.spy(modelMapper);
    doThrow(new RuntimeException()).when(spyMapper).map(any(), eq(PresentationDTO.class));

    // Inject the spyMapper into the presentationService
    ReflectionTestUtils.setField(presentationService, "modelMapper", spyMapper);

    // Call method and catch the exception
    CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
        presentationService.getTeachingStaffPresentations(staffId);
    });

    // Check the exception message
    assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());

    // Check interactions
    verify(presentationRepository, times(1)).findByTeachingStaff(staffId);
}

@Test
void testGetTeamPresentations_ServiceError() {
    // Mock methods
    Integer teamId = 1;
    TeamEntity team = new TeamEntity();
    team.setProjectDev(new ProjectEntity());  // Setting a ProjectEntity to avoid null
    team.setProjectValidation(new ProjectEntity());  // Setting a ProjectEntity to avoid null
    when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

    List<PresentationEntity> presentations = new ArrayList<>();
    presentations.add(new PresentationEntity(1));
    when(presentationRepository.findByProject(any(ProjectEntity.class))).thenReturn(presentations);

    // Spy on the ModelMapper and make it throw an exception when trying to map
    ModelMapper spyMapper = Mockito.spy(modelMapper);
    doThrow(new RuntimeException()).when(spyMapper).map(any(), eq(PresentationDTO.class));

    // Inject the spyMapper into the presentationService
    ReflectionTestUtils.setField(presentationService, "modelMapper", spyMapper);

    // Call method and catch the exception
    CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
        presentationService.getTeamPresentations(teamId);
    });

    // Check the exception message
    assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());

    // Check interactions
    verify(presentationRepository, times(2)).findByProject(any(ProjectEntity.class)); 
}

    @Test
    void testSetJury1Notes_Nominal() throws CustomRuntimeException {
        PresentationEntity presentation = new PresentationEntity();
        PresentationDTO presentationDTO = new PresentationDTO();

        String notes = "test";
        presentation.setJury1Notes((notes));

        ArgumentCaptor<PresentationEntity> captor = ArgumentCaptor.forClass(PresentationEntity.class);
        when(presentationRepository.save(captor.capture())).thenReturn(presentation);

        Optional<PresentationEntity> mockedPresentation = Optional.of(presentation);
        when(presentationRepository.findById(mockedPresentation.get().getIdPresentation())).thenReturn(mockedPresentation);

        presentationService.setJury1Notes(presentationDTO, notes);
        assertEquals(notes, captor.getValue().getJury1Notes());
    }

    @Test
    void testSetJury2Notes_Nominal() throws CustomRuntimeException {
        PresentationEntity presentation = new PresentationEntity();
        PresentationDTO presentationDTO = new PresentationDTO();

        String notes = "test";
        presentation.setJury2Notes((notes));

        ArgumentCaptor<PresentationEntity> captor = ArgumentCaptor.forClass(PresentationEntity.class);
        when(presentationRepository.save(captor.capture())).thenReturn(presentation);

        Optional<PresentationEntity> mockedPresentation = Optional.of(presentation);
        when(presentationRepository.findById(mockedPresentation.get().getIdPresentation())).thenReturn(mockedPresentation);

        presentationService.setJury2Notes(presentationDTO, notes);
        assertEquals(notes, captor.getValue().getJury2Notes());
    }

    @Test
    void testSetJuryNotes_Nominal_ts1() throws CustomRuntimeException {
        UserDTO user1 = new UserDTO();
        user1.setId(1);   
        UserDTO user2 = new UserDTO();
        user2.setId(2); 
        
        TeachingStaffDTO ts1 = new TeachingStaffDTO(user1);
        TeachingStaffDTO ts2 = new TeachingStaffDTO(user2);

        JuryDTO jury = new JuryDTO(ts1, ts2);

        UserEntity user1Entity = new UserEntity();
        user1Entity.setId(1);   
        UserEntity user2Entity = new UserEntity();
        user2Entity.setId(2); 
        
        TeachingStaffEntity ts1Entity = new TeachingStaffEntity(user1Entity);
        TeachingStaffEntity ts2Entity = new TeachingStaffEntity(user2Entity);

        JuryEntity juryEntity = new JuryEntity(ts1Entity, ts2Entity);

        PresentationEntity presentation = new PresentationEntity();
        PresentationDTO presentationDTO = new PresentationDTO();

        String notes = "test";
        presentation.setJury1Notes((notes));
        presentationDTO.setJury1Notes((notes));

        presentation.setIdPresentation(1);
        presentation.setJury(juryEntity);

        presentationDTO.setIdPresentation(1);
        presentationDTO.setJury(jury);

        ArgumentCaptor<PresentationEntity> captor = ArgumentCaptor.forClass(PresentationEntity.class);
        when(presentationRepository.save(captor.capture())).thenReturn(presentation);

        Optional<PresentationEntity> mockedPresentation = Optional.of(presentation);
        when(presentationRepository.findById(mockedPresentation.get().getIdPresentation())).thenReturn(mockedPresentation);

        // Mock des méthodes et comportements nécessaires
        doNothing().when(presentationServiceRule).checkDateBeginPassed(any());
        when(userServiceRules.getCurrentUser()).thenReturn(user1);
        
        PresentationDTO mockedJury1NotesPresentation = new PresentationDTO();
        mockedJury1NotesPresentation.setIdPresentation(presentation.getIdPresentation());
        PresentationDTO mockedJury2NotesPresentation = new PresentationDTO();
        mockedJury2NotesPresentation.setIdPresentation(presentation.getIdPresentation());

        // Appel de la méthode à tester
        PresentationDTO result = presentationService.setJuryNotes(1, notes);

        // Vérification des appels de méthodes et des résultats
        verify(presentationServiceRule).checkDateBeginPassed(presentation.getDatetimeBegin());
        verify(userServiceRules).getCurrentUser();

        assertEquals(notes, result.getJury1Notes());
    }

    @Test
    void testSetJuryNotes_Nominal_ts2() throws CustomRuntimeException {
        UserDTO user1 = new UserDTO();
        user1.setId(1);   
        UserDTO user2 = new UserDTO();
        user2.setId(2); 
        
        TeachingStaffDTO ts1 = new TeachingStaffDTO(user1);
        TeachingStaffDTO ts2 = new TeachingStaffDTO(user2);

        JuryDTO jury = new JuryDTO(ts1, ts2);

        UserEntity user1Entity = new UserEntity();
        user1Entity.setId(1);   
        UserEntity user2Entity = new UserEntity();
        user2Entity.setId(2); 
        
        TeachingStaffEntity ts1Entity = new TeachingStaffEntity(user1Entity);
        TeachingStaffEntity ts2Entity = new TeachingStaffEntity(user2Entity);

        JuryEntity juryEntity = new JuryEntity(ts1Entity, ts2Entity);

        PresentationEntity presentation = new PresentationEntity();
        PresentationDTO presentationDTO = new PresentationDTO();

        String notes = "test";
        presentation.setJury2Notes((notes));
        presentationDTO.setJury2Notes((notes));

        presentation.setIdPresentation(1);
        presentation.setJury(juryEntity);

        presentationDTO.setIdPresentation(1);
        presentationDTO.setJury(jury);

        ArgumentCaptor<PresentationEntity> captor = ArgumentCaptor.forClass(PresentationEntity.class);
        when(presentationRepository.save(captor.capture())).thenReturn(presentation);

        Optional<PresentationEntity> mockedPresentation = Optional.of(presentation);
        when(presentationRepository.findById(mockedPresentation.get().getIdPresentation())).thenReturn(mockedPresentation);

        // Mock des méthodes et comportements nécessaires
        doNothing().when(presentationServiceRule).checkDateBeginPassed(any());
        when(userServiceRules.getCurrentUser()).thenReturn(user2);
        
        PresentationDTO mockedJury1NotesPresentation = new PresentationDTO();
        mockedJury1NotesPresentation.setIdPresentation(presentation.getIdPresentation());
        PresentationDTO mockedJury2NotesPresentation = new PresentationDTO();
        mockedJury2NotesPresentation.setIdPresentation(presentation.getIdPresentation());

        // Appel de la méthode à tester
        PresentationDTO result = presentationService.setJuryNotes(1, notes);

        // Vérification des appels de méthodes et des résultats
        verify(presentationServiceRule).checkDateBeginPassed(presentation.getDatetimeBegin());
        assertEquals(notes, result.getJury2Notes());
    }

    @Test
    void testSetTeamNotes_Nominal() throws CustomRuntimeException {
        PresentationEntity presentation = new PresentationEntity();
        PresentationDTO presentationDTO = new PresentationDTO();

        String notes = "test";
        presentation.setValidationTeamNotes((notes));

        ArgumentCaptor<PresentationEntity> captor = ArgumentCaptor.forClass(PresentationEntity.class);
        when(presentationRepository.save(captor.capture())).thenReturn(presentation);

        Optional<PresentationEntity> mockedPresentation = Optional.of(presentation);
        when(presentationRepository.findById(mockedPresentation.get().getIdPresentation())).thenReturn(mockedPresentation);

        presentationService.setTeamNotes(presentationDTO.getIdPresentation(), notes);
        assertEquals(notes, captor.getValue().getValidationTeamNotes());
    }
}
