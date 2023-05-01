package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.models.entities.TeamMemberEntity;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;

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
        Integer projectId = 1;
        Integer currentUserId = 2;

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setIdProject(projectId);
        projectDTO.setName("Updated Project");
        projectDTO.setDescription("Updated Description");

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setIdProject(projectId);
        projectEntity.setName("Project 1");
        projectEntity.setDescription("Description 1");

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamMembers(new ArrayList<>());
        
        UserEntity currentUserEntity = new UserEntity();
        currentUserEntity.setId(currentUserId);
        
        UserDTO currentUserDTO = new UserDTO();
        currentUserDTO.setId(currentUserId);
        
        TeamMemberEntity teamMember = new TeamMemberEntity();
        teamMember.setUser(currentUserEntity);
        
        teamEntity.getTeamMembers().add(teamMember);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(teamRepository.findByProjectDevId(projectId)).thenReturn(Optional.of(teamEntity));
        when(securityConfig.getCurrentUser()).thenReturn(currentUserDTO);
        when(projectRepository.save(projectEntity)).thenReturn(projectEntity);
        when(modelMapper.map(projectEntity, ProjectDTO.class)).thenReturn(projectDTO);

        ProjectDTO actualProjectDTO = projectService.updateProject(projectDTO);

        verify(projectRepository, times(1)).findById(projectId);
        verify(teamRepository, times(1)).findByProjectDevId(projectId);
        verify(securityConfig, times(1)).getCurrentUser();
        verify(projectRepository, times(1)).save(projectEntity);
        verify(modelMapper, times(1)).map(projectEntity, ProjectDTO.class);
        assertEquals(projectDTO, actualProjectDTO);
    }

    @Test
    public void testUpdateProject_ServiceError() {
        Integer projectId = 1;

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setIdProject(projectId);
        projectDTO.setName("Updated Project");
        projectDTO.setDescription("Updated Description");

        when(projectRepository.findById(projectId)).thenThrow(RuntimeException.class);

        assertThrows(CustomRuntimeException.class, () -> projectService.updateProject(projectDTO));
    }

    @Test
    public void testUpdateProject_ProjectNotFound() {
        Integer projectId = 1;

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setIdProject(projectId);
        projectDTO.setName("Updated Project");
        projectDTO.setDescription("Updated Description");

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(CustomRuntimeException.class, () -> projectService.updateProject(projectDTO));
    }

    @Test
    public void testUpdateProject_TeamNotFound() {
        Integer projectId = 1;

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setIdProject(projectId);
        projectDTO.setName("Updated Project");
        projectDTO.setDescription("Updated Description");

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setIdProject(projectId);
        projectEntity.setName("Project 1");
        projectEntity.setDescription("Description 1");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(teamRepository.findByProjectDevId(projectId)).thenReturn(Optional.empty());

        assertThrows(CustomRuntimeException.class, () -> projectService.updateProject(projectDTO));
    }

    @Test
    public void testUpdateProject_UserNotInAssociatedTeam() throws CustomRuntimeException {
        Integer projectId = 1;
        Integer currentUserId = 2;

        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setIdProject(projectId);
        projectDTO.setName("Updated Project");
        projectDTO.setDescription("Updated Description");

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setIdProject(projectId);
        projectEntity.setName("Project 1");
        projectEntity.setDescription("Description 1");

        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamMembers(new ArrayList<>());

        UserEntity currentUserEntity = new UserEntity();
        currentUserEntity.setId(3);

        UserDTO currentUserDTO = new UserDTO();
        currentUserDTO.setId(currentUserId);

        TeamMemberEntity teamMember = new TeamMemberEntity();
        teamMember.setUser(currentUserEntity);

        teamEntity.getTeamMembers().add(teamMember);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));
        when(teamRepository.findByProjectDevId(projectId)).thenReturn(Optional.of(teamEntity));
        when(securityConfig.getCurrentUser()).thenReturn(currentUserDTO);

        assertThrows(CustomRuntimeException.class, () -> projectService.updateProject(projectDTO));
    }


    






    


}
