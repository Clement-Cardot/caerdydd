package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.project.ProjectEntity;
import com.caerdydd.taf.models.entities.project.TeamEntity;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;
import com.caerdydd.taf.services.rules.UserServiceRules;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    
    @InjectMocks
    private ProjectService projectService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private ProjectRepository projectRepository;


    @Mock
    private TeamRepository teamRepository;

    @Mock
    private SecurityConfig securityConfig;

    @Mock
    private UserServiceRules userServiceRules;

    @Mock
    private TeamMemberService teamMemberService;
    
    
    @Test
    void testSaveProject_Nominal() {
        // Mock projectRepository.save() method
        ProjectEntity mockedAnswer = new ProjectEntity("Projet A", "Description 1");
        when(projectRepository.save(any(ProjectEntity.class))).thenReturn(mockedAnswer);

        // Prepare the input
        ProjectDTO projectToSave = new ProjectDTO("Projet A", "Description 1");
        
        // Call the method to test
        ProjectDTO result = projectService.saveProject(projectToSave);

        // Check the result
        verify(projectRepository, times(1)).save(any(ProjectEntity.class));
        assertEquals(projectToSave.toString(), result.toString());
    }

    @Test
    void testCreateProject_Nominal() throws CustomRuntimeException {
        // Mock projectRepository.save() method
        when(projectRepository.save(any(ProjectEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        // Definie the expected answer
        ProjectDTO project1 = new ProjectDTO();
        project1.setIdProject(1);
        project1.setName("Projet 1");
        project1.setDescription("Description 1");
        project1.setIsValidated(false);
        ProjectDTO project2 = new ProjectDTO();
        project2.setIdProject(2);
        project2.setName("Projet 2");
        project2.setDescription("Description 2");
        project2.setIsValidated(false);
        List<ProjectDTO> expectedAnswer = new ArrayList<>();
        expectedAnswer.add(project1);
        expectedAnswer.add(project2);

        // Call the method to test
        List<ProjectDTO> result = projectService.createProjects(2,0);

        // Check the result
        verify(projectRepository, times(2)).save(any(ProjectEntity.class));
        assertEquals(expectedAnswer.toString(), result.toString());

    }


    @Test
    public void testGetProjectById_Nominal() throws CustomRuntimeException {
        // Préparer les données d'entrée
        Integer projectId = 1;

        // Préparer le projet Entity à retourner
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setIdProject(projectId);
        projectEntity.setName("Project 1");
        projectEntity.setDescription("Description 1");

        // Préparer le projet DTO attendu en sortie
        ProjectDTO expectedProjectDTO = new ProjectDTO();
        expectedProjectDTO.setIdProject(projectId);
        expectedProjectDTO.setName("Project 1");
        expectedProjectDTO.setDescription("Description 1");

        // Configurer les comportements des Mocks
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(modelMapper.map(projectEntity, ProjectDTO.class)).thenReturn(expectedProjectDTO);

        // Appeler la méthode à tester
        ProjectDTO actualProjectDTO = projectService.getProjectById(projectId);

        // Vérifier les résultats
        verify(projectRepository, times(1)).findById(projectId);
        verify(modelMapper, times(1)).map(projectEntity, ProjectDTO.class);
        assertEquals(expectedProjectDTO, actualProjectDTO);
    }

    @Test
    public void testGetProjectById_ProjectNotFound() {
        // Préparer les données d'entrée
        Integer projectId = 1;

        // Configurer les comportements des Mocks
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        // Appeler la méthode à tester et vérifier l'exception
        assertThrows(CustomRuntimeException.class, () -> projectService.getProjectById(projectId));
        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    public void testGetProjectById_ServiceError() {
    // Préparer les données d'entrée
    Integer projectId = 1;

    // Configurer les comportements des Mocks
    when(projectRepository.findById(projectId)).thenThrow(new RuntimeException("Erreur de service"));

    // Appeler la méthode à tester et vérifier l'exception
    CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> projectService.getProjectById(projectId));
    verify(projectRepository, times(1)).findById(projectId);
    assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
}


@Test
public void testUpdateProject_Nominal() throws CustomRuntimeException {
    // Prepare data
    Integer projectId = 1;
    ProjectDTO projectDTO = new ProjectDTO();
    projectDTO.setIdProject(projectId);
    projectDTO.setName("Updated Project");
    projectDTO.setDescription("Updated Description");

    ProjectEntity existingProjectEntity = new ProjectEntity();
    existingProjectEntity.setIdProject(projectId);
    existingProjectEntity.setName("Project 1");
    existingProjectEntity.setDescription("Description 1");

    ProjectEntity updatedProjectEntity = new ProjectEntity();
    updatedProjectEntity.setIdProject(projectId);
    updatedProjectEntity.setName(projectDTO.getName());
    updatedProjectEntity.setDescription(projectDTO.getDescription());

    // Configure Mocks behavior
    when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProjectEntity));
    when(projectRepository.save(any(ProjectEntity.class))).thenReturn(updatedProjectEntity);

    // IMPORTANT: Mock the ModelMapper's map method for both uses in the updateProject() method
    doReturn(updatedProjectEntity).when(modelMapper).map(any(ProjectDTO.class), eq(ProjectEntity.class));
    doReturn(projectDTO).when(modelMapper).map(any(ProjectEntity.class), eq(ProjectDTO.class));

    // Call the method to test
    ProjectDTO actualProjectDTO = projectService.updateProject(projectDTO);

    // Verify the results
    verify(projectRepository, times(1)).findById(projectId);
    verify(projectRepository, times(1)).save(any(ProjectEntity.class));
    verify(modelMapper, times(1)).map(any(ProjectDTO.class), eq(ProjectEntity.class));
    verify(modelMapper, times(1)).map(any(ProjectEntity.class), eq(ProjectDTO.class));
    assertEquals(projectDTO, actualProjectDTO);
}



@Test
public void testUpdateProject_ProjectNotFound() {
    // Prepare data
    Integer projectId = 1;
    ProjectDTO projectDTO = new ProjectDTO();
    projectDTO.setIdProject(projectId);

    // Configure Mocks behavior
    when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

    // Call the method to test and verify the exception
    assertThrows(CustomRuntimeException.class, () -> projectService.updateProject(projectDTO));
    verify(projectRepository, times(1)).findById(projectId);
}

@Test
public void testUpdateProject_ServiceError() {
    // Prepare data
    Integer projectId = 1;
    ProjectDTO projectDTO = new ProjectDTO();
    projectDTO.setIdProject(projectId);

    ProjectEntity existingProjectEntity = new ProjectEntity();
    existingProjectEntity.setIdProject(projectId);

    // Configure Mocks behavior
    when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProjectEntity));
    when(projectRepository.save(any(ProjectEntity.class))).thenThrow(new RuntimeException("Service Error"));

    // Call the method to test and verify the exception
    CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> projectService.updateProject(projectDTO));
    verify(projectRepository, times(1)).findById(projectId);
    assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
}

@Test
public void testUpdateValidation_Nominal() throws CustomRuntimeException {
    // Prepare data
    Integer projectId = 1;
    ProjectDTO projectDTO = new ProjectDTO();
    projectDTO.setIdProject(projectId);
    projectDTO.setName("Updated Project");
    projectDTO.setDescription("Updated Description");

    ProjectEntity existingProjectEntity = new ProjectEntity();
    existingProjectEntity.setIdProject(projectId);
    existingProjectEntity.setName("Project 1");
    existingProjectEntity.setDescription("Description 1");

    ProjectEntity updatedProjectEntity = new ProjectEntity();
    updatedProjectEntity.setIdProject(projectId);
    updatedProjectEntity.setName(projectDTO.getName());
    updatedProjectEntity.setDescription(projectDTO.getDescription());

    // Configure Mocks behavior
    doNothing().when(userServiceRules).checkCurrentUserRole("OPTION_LEADER_ROLE");
    when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProjectEntity));
    when(projectRepository.save(any(ProjectEntity.class))).thenReturn(updatedProjectEntity);
    doReturn(updatedProjectEntity).when(modelMapper).map(any(ProjectDTO.class), eq(ProjectEntity.class));
    doReturn(projectDTO).when(modelMapper).map(any(ProjectEntity.class), eq(ProjectDTO.class));

    // Call the method to test
    ProjectDTO actualProjectDTO = projectService.updateValidation(projectDTO);

    // Verify the results
    verify(userServiceRules, times(1)).checkCurrentUserRole("OPTION_LEADER_ROLE");
    verify(projectRepository, times(1)).findById(projectId);
    verify(projectRepository, times(1)).save(any(ProjectEntity.class));
    verify(modelMapper, times(1)).map(any(ProjectDTO.class), eq(ProjectEntity.class));
    verify(modelMapper, times(1)).map(any(ProjectEntity.class), eq(ProjectDTO.class));
    assertEquals(projectDTO, actualProjectDTO);
}


@Test
public void testUpdateDescription_Nominal() throws CustomRuntimeException {
    // Prepare data
    Integer projectId = 1;
    Integer teamId = 2;
    ProjectDTO projectDTO = new ProjectDTO();
    projectDTO.setIdProject(projectId);
    projectDTO.setName("Updated Project");
    projectDTO.setDescription("Updated Description");
    TeamDTO teamDTO = new TeamDTO();
    teamDTO.setIdTeam(teamId);
    projectDTO.setTeamDev(teamDTO);

    UserDTO currentUser = new UserDTO();
    currentUser.setId(3);

    TeamEntity teamEntity = new TeamEntity();
    teamEntity.setIdTeam(teamId);

    TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
    teamMemberDTO.setUser(currentUser);
    teamMemberDTO.setTeam(teamDTO);

    ProjectEntity existingProjectEntity = new ProjectEntity();
    existingProjectEntity.setIdProject(projectId);
    existingProjectEntity.setName("Project 1");
    existingProjectEntity.setDescription("Description 1");

    ProjectEntity updatedProjectEntity = new ProjectEntity();
    updatedProjectEntity.setIdProject(projectId);
    updatedProjectEntity.setName(projectDTO.getName());
    updatedProjectEntity.setDescription(projectDTO.getDescription());

    // Configure Mocks behavior
    doNothing().when(userServiceRules).checkCurrentUserRole("TEAM_MEMBER_ROLE");
    when(userServiceRules.getCurrentUser()).thenReturn(currentUser);
    when(teamRepository.findByProjectDevId(projectId)).thenReturn(Optional.of(teamEntity));
    when(teamMemberService.getTeamMemberById(currentUser.getId())).thenReturn(teamMemberDTO);
    when(projectRepository.findById(projectId)).thenReturn(Optional.of(existingProjectEntity));
    when(projectRepository.save(any(ProjectEntity.class))).thenReturn(updatedProjectEntity);
    doReturn(updatedProjectEntity).when(modelMapper).map(any(ProjectDTO.class), eq(ProjectEntity.class));
    doReturn(projectDTO).when(modelMapper).map(any(ProjectEntity.class), eq(ProjectDTO.class));

    // Call the method to test
    ProjectDTO actualProjectDTO = projectService.updateDescription(projectDTO);

    // Verify the results
    verify(userServiceRules, times(1)).checkCurrentUserRole("TEAM_MEMBER_ROLE");
    verify(userServiceRules, times(1)).getCurrentUser();
    verify(teamRepository, times(1)).findByProjectDevId(projectId);
    verify(teamMemberService, times(1)).getTeamMemberById(currentUser.getId());
    verify(projectRepository, times(1)).findById(projectId);
    verify(projectRepository, times(1)).save(any(ProjectEntity.class));
    assertEquals(projectDTO, actualProjectDTO);
}


@Test
public void testUpdateDescription_TeamNotFound() throws CustomRuntimeException {
// Prepare data
Integer projectId = 1;
Integer teamId = 2;
ProjectDTO projectDTO = new ProjectDTO();
projectDTO.setIdProject(projectId);
projectDTO.setName("Updated Project");
projectDTO.setDescription("Updated Description");
TeamDTO teamDTO = new TeamDTO();
teamDTO.setIdTeam(teamId);
projectDTO.setTeamDev(teamDTO);
UserDTO currentUser = new UserDTO();
currentUser.setId(3);

// Configure Mocks behavior
doNothing().when(userServiceRules).checkCurrentUserRole("TEAM_MEMBER_ROLE");
when(userServiceRules.getCurrentUser()).thenReturn(currentUser);
when(teamRepository.findByProjectDevId(projectId)).thenReturn(Optional.empty());

// Call the method to test and verify the exception is thrown
assertThrows(CustomRuntimeException.class, () -> {
    projectService.updateDescription(projectDTO);
});

// Verify the results
verify(userServiceRules, times(1)).checkCurrentUserRole("TEAM_MEMBER_ROLE");
verify(userServiceRules, times(1)).getCurrentUser();
verify(teamRepository, times(1)).findByProjectDevId(projectId);
}

@Test
public void testUpdateDescription_UserNotInTeam() throws CustomRuntimeException {
// Prepare data
Integer projectId = 1;
Integer teamId = 2;
ProjectDTO projectDTO = new ProjectDTO();
projectDTO.setIdProject(projectId);
projectDTO.setName("Updated Project");
projectDTO.setDescription("Updated Description");
TeamDTO teamDTO = new TeamDTO();
teamDTO.setIdTeam(teamId);
projectDTO.setTeamDev(teamDTO);
UserDTO currentUser = new UserDTO();
currentUser.setId(3);

TeamEntity teamEntity = new TeamEntity();
teamEntity.setIdTeam(teamId);

TeamDTO anotherTeamDTO = new TeamDTO();
anotherTeamDTO.setIdTeam(4);

TeamMemberDTO teamMemberDTO = new TeamMemberDTO();
teamMemberDTO.setUser(currentUser);
teamMemberDTO.setTeam(anotherTeamDTO);

// Configure Mocks behavior
doNothing().when(userServiceRules).checkCurrentUserRole("TEAM_MEMBER_ROLE");
when(userServiceRules.getCurrentUser()).thenReturn(currentUser);
when(teamRepository.findByProjectDevId(projectId)).thenReturn(Optional.of(teamEntity));
when(teamMemberService.getTeamMemberById(currentUser.getId())).thenReturn(teamMemberDTO);

// Call the method to test and verify the exception is thrown
assertThrows(CustomRuntimeException.class, () -> {
    projectService.updateDescription(projectDTO);
});

// Verify the results
verify(userServiceRules, times(1)).checkCurrentUserRole("TEAM_MEMBER_ROLE");
verify(userServiceRules, times(1)).getCurrentUser();
verify(teamRepository, times(1)).findByProjectDevId(projectId);
verify(teamMemberService, times(1)).getTeamMemberById(currentUser.getId());
}

@Test
public void testListAllProjects() throws CustomRuntimeException {
    // Prepare data
    List<ProjectEntity> projectEntities = new ArrayList<>();
    projectEntities.add(new ProjectEntity(1, "Project 1", "Description 1", true));
    projectEntities.add(new ProjectEntity(2, "Project 2", "Description 2", false));
    projectEntities.add(new ProjectEntity(3, "Project 3", "Description 3", true));
    List<ProjectDTO> expectedProjectDTOs = projectEntities.stream()
        .map(project -> modelMapper.map(project, ProjectDTO.class))
        .collect(Collectors.toList());

    // Configure mocks behavior
    when(projectRepository.findAll()).thenReturn(projectEntities);

    // Call the method to test
    List<ProjectDTO> actualProjectDTOs = projectService.listAllProjects();

    // Verify the results
    verify(projectRepository, times(1)).findAll();
    assertEquals(expectedProjectDTOs.toString(), actualProjectDTOs.toString());
}

@Test
public void testListAllProjects_ServiceError() {
    // Configure Mocks behavior
    when(projectRepository.findAll()).thenThrow(new RuntimeException("Service Error"));

    // Call the method to test and verify the exception
    CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> projectService.listAllProjects());
    verify(projectRepository, times(1)).findAll();
    assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
}

}
