package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.notification.NotificationDTO;
import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.dto.user.RoleDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.project.ProjectEntity;
import com.caerdydd.taf.models.entities.project.TeamEntity;
import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.models.entities.user.TeamMemberEntity;
import com.caerdydd.taf.models.entities.user.UserEntity;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;
import com.caerdydd.taf.services.rules.JuryServiceRules;
import com.caerdydd.taf.services.rules.TeamServiceRules;
import com.caerdydd.taf.services.rules.UserServiceRules;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock 
    private TeamRepository teamRepository;

    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private ProjectService projectService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private SecurityConfig securityConfig;

    @Mock
    private TeamServiceRules teamServiceRules;

    @Mock
    private UserServiceRules userServiceRules;

    @Mock
    private JuryServiceRules juryServiceRules;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private NotificationService notificationService;

    // Test listAllTeams() method

    @Test
    void testListAllTeams_Nominal() {
        // Mock teamRepository.findAll() method
        List<TeamEntity> mockedAnswer = new ArrayList<TeamEntity>();
        mockedAnswer.add(new TeamEntity(
            1, 
            "Team A", 
            new ProjectEntity("Project A", "Description 1"), 
            new ProjectEntity("Project B", "Description 2")
        ));
        mockedAnswer.add(
            new TeamEntity(
                2, 
                "Team B",
                new ProjectEntity("Project B", "Description 2"),
                new ProjectEntity("Project A", "Description 1")
            ));
        when(teamRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<TeamDTO> expectedAnswer = new ArrayList<TeamDTO>();
        expectedAnswer.add(new TeamDTO(
            1, 
            "Team A",
            new ProjectDTO("Project A", "Description 1"),
            new ProjectDTO("Project B", "Description 2")
        ));
        expectedAnswer.add(new TeamDTO(
            2, 
            "Team B",
            new ProjectDTO("Project B", "Description 2"),
            new ProjectDTO("Project A", "Description 1")
        ));

        // Call the method to test
        List<TeamDTO> result = new ArrayList<TeamDTO>();
        try {
            result = teamService.listAllTeams();
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(teamRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testListAllTeams_Empty() {
        // Mock teamRepository.findAll() method
        List<TeamEntity> mockedAnswer = new ArrayList<TeamEntity>();
        when(teamRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<TeamDTO> expectedAnswer = new ArrayList<TeamDTO>();

        // Call the method to test
        List<TeamDTO> result = new ArrayList<TeamDTO>();
        try {
            result = teamService.listAllTeams();
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(teamRepository, times(1)).findAll();
        assertEquals(0, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testListAllTeams_ServiceError() {
        // Mock teamRepository.findAll() method
        when(teamRepository.findAll()).thenThrow(new NoSuchElementException());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.listAllTeams();
        });

        // Verify the result
        verify(teamRepository, times(1)).findAll();
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    // Test getTeamById() method
    @Test
    void testGetTeamById_Nominal() {
        // Mock teamRepository.findById() method
        Optional<TeamEntity> mockedAnswer = Optional.of(new TeamEntity(
                                                                1, 
                                                                "Team A", 
                                                                new ProjectEntity("Project A", "Description 1"),
                                                                new ProjectEntity("Project B", "Description 2")
                                                        ));
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Define the expected answer
        TeamDTO expectedAnswer = new TeamDTO(
                                        1, 
                                        "Team A",
                                        new ProjectDTO("Project A", "Description 1"),
                                        new ProjectDTO("Project B", "Description 2")    
                                    );

        // Call the method to test
        TeamDTO result = new TeamDTO();
        try {
            result = teamService.getTeamById(1);
        } catch (CustomRuntimeException e) {
            fail();
        } 

        // Verify the result
        verify(teamRepository, times(1)).findById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeamById_ServiceError() {
        // Mock teamRepository.findById() method
        when(teamRepository.findById(1)).thenThrow(new NoSuchElementException());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.getTeamById(1);
        });

        // Verify the result
        verify(teamRepository, times(1)).findById(1);
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    void testGetTeamById_TeamNotFound() {
        // Mock teamRepository.findById() method
        Optional<TeamEntity> mockedAnswer = Optional.empty();
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.getTeamById(1);
        });

        // Verify the result
        verify(teamRepository, times(1)).findById(1);
        assertEquals(CustomRuntimeException.TEAM_NOT_FOUND, exception.getMessage());
    }

    // Test saveTeam() method
    @Test
    void saveTeamTest_Nominal(){
        // Mock teamRepository.save() method
        TeamEntity mockedAnswer = new TeamEntity(
                                            1, 
                                            "test",
                                            new ProjectEntity("Project A", "Description 1"),
                                            new ProjectEntity("Project B", "Description 2")
                                            );
		when(teamRepository.save(any(TeamEntity.class))).thenReturn(mockedAnswer);
        
        // Prepare the input
        TeamDTO teamToSave = new TeamDTO(
                                    1, 
                                    "test",
                                    new ProjectDTO("Project A", "Description 1"),
                                    new ProjectDTO("Project B", "Description 2")
                                );

        // Call the method to test
        TeamDTO response = teamService.saveTeam(teamToSave);

        // Verify the result
        verify(teamRepository, times(1)).save(any(TeamEntity.class));
        assertEquals(teamToSave.toString(), response.toString());
    }

    // Test applyInATeam() method
    @Test
    void applyInATeamTest_Nominal() throws CustomRuntimeException {
        // Mock userService.getUserById() method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("STUDENT_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);
        when(userService.getUserById(1)).thenReturn(mockedUser);        

        // Mock teamRepository.findById() method
        TeamEntity mockedTeam = new TeamEntity(1, 
                                               "Team A",
                                               new ProjectEntity("Project A", "Description 1"),
                                               new ProjectEntity("Project B", "Description 2")
                                            );
        mockedTeam.setTeamMembers(new ArrayList<TeamMemberEntity>());
        Optional<TeamEntity> mockedAnswer = Optional.of(mockedTeam);
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Mock userServiceRules.checkUserRole()
        doNothing().when(userServiceRules)
                    .checkUserRole(any(UserDTO.class), anyString());
        // Mock userServiceRules.checkCurrentUser()
        doNothing().when(userServiceRules)
                    .checkCurrentUser(any(UserDTO.class));
        // Mock teamServiceRules.checkTeamIsFull()
        doNothing().when(teamServiceRules)
                    .checkTeamIsFull(any(TeamDTO.class));
        // Mock teamServiceRules.checkSpecialityRation()
        doNothing().when(teamServiceRules)
                    .checkSpecialityRatio(any(TeamDTO.class));

        // Mock UserService.updateUser() method
        when(userService.updateUser(any(UserDTO.class))).thenReturn(mockedUser);

        // Define the expected answer
        UserDTO expectedAnswer = mockedUser;
        TeamMemberDTO teamMember = new TeamMemberDTO();
        teamMember.setUser(expectedAnswer);
        
        // Call the method to test
        UserDTO result = new UserDTO();
        try {
            result = teamService.applyInATeam(1, 1);
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(userService, times(1)).getUserById(anyInt());
        verify(teamRepository, times(1)).findById(anyInt());

        verify(userServiceRules, times(1)).checkUserRole(any(UserDTO.class), anyString());
        verify(userServiceRules, times(1)).checkCurrentUser(any(UserDTO.class));
        verify(teamServiceRules, times(1)).checkTeamIsFull(any(TeamDTO.class));
        verify(teamServiceRules, times(1)).checkSpecialityRatio(any(TeamDTO.class));

        verify(userService, times(1)).updateUser(any(UserDTO.class));
        verify(roleService, times(1)).deleteRole(any(RoleDTO.class));

        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void applyInATeamTest_UserNotFound() throws CustomRuntimeException {
        // Mock userService.getUserById() method
        when(userService.getUserById(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND));        

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(anyInt());
        verify(teamRepository, times(0)).findById(anyInt());

        verify(userServiceRules, times(0)).checkUserRole(any(UserDTO.class), anyString());
        verify(userServiceRules, times(0)).checkCurrentUser(any(UserDTO.class));
        verify(teamServiceRules, times(0)).checkTeamIsFull(any(TeamDTO.class));
        verify(teamServiceRules, times(0)).checkSpecialityRatio(any(TeamDTO.class));

        verify(userService, times(0)).updateUser(any(UserDTO.class));
        verify(roleService, times(0)).deleteRole(any(RoleDTO.class));

        assertEquals(CustomRuntimeException.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void applyInATeamTest_TeamNotFound() throws CustomRuntimeException {
        // Mock userService.getUserById() method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("STUDENT_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);
        when(userService.getUserById(1)).thenReturn(mockedUser);        

        // Mock teamRepository.findById() method
        Optional<TeamEntity> mockedAnswer = Optional.empty();
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(anyInt());
        verify(teamRepository, times(1)).findById(anyInt());

        verify(userServiceRules, times(0)).checkUserRole(any(UserDTO.class), anyString());
        verify(userServiceRules, times(0)).checkCurrentUser(any(UserDTO.class));
        verify(teamServiceRules, times(0)).checkTeamIsFull(any(TeamDTO.class));
        verify(teamServiceRules, times(0)).checkSpecialityRatio(any(TeamDTO.class));

        verify(userService, times(0)).updateUser(any(UserDTO.class));
        verify(roleService, times(0)).deleteRole(any(RoleDTO.class));

        assertEquals(CustomRuntimeException.TEAM_NOT_FOUND, exception.getMessage());
    }

    @Test
    void applyInATeamTest_UserIsNotAStudent() throws CustomRuntimeException {
        // Mock userService.getUserById() method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("STUDENT_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);
        when(userService.getUserById(1)).thenReturn(mockedUser);        

        // Mock teamRepository.findById() method
        TeamEntity mockedTeam = new TeamEntity(1, 
                                               "Team A",
                                               new ProjectEntity("Project A", "Description 1"),
                                               new ProjectEntity("Project B", "Description 2")
                                            );
        mockedTeam.setTeamMembers(new ArrayList<TeamMemberEntity>());
        Optional<TeamEntity> mockedAnswer = Optional.of(mockedTeam);
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Mock userServiceRules.checkUserRole()
        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_STUDENT)).when(userServiceRules)
                    .checkUserRole(any(UserDTO.class), anyString());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(anyInt());
        verify(teamRepository, times(1)).findById(anyInt());

        verify(userServiceRules, times(1)).checkUserRole(any(UserDTO.class), anyString());
        verify(userServiceRules, times(0)).checkCurrentUser(any(UserDTO.class));
        verify(teamServiceRules, times(0)).checkTeamIsFull(any(TeamDTO.class));
        verify(teamServiceRules, times(0)).checkSpecialityRatio(any(TeamDTO.class));

        verify(userService, times(0)).updateUser(any(UserDTO.class));
        verify(roleService, times(0)).deleteRole(any(RoleDTO.class));

        assertEquals(CustomRuntimeException.USER_IS_NOT_A_STUDENT, exception.getMessage());
    }

    @Test
    void applyInATeamTest_CurrentUserIsNotRequestUser() throws CustomRuntimeException {
        // Mock userService.getUserById() method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("STUDENT_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);
        when(userService.getUserById(1)).thenReturn(mockedUser);        

        // Mock teamRepository.findById() method
        TeamEntity mockedTeam = new TeamEntity(1, 
                                               "Team A",
                                               new ProjectEntity("Project A", "Description 1"),
                                               new ProjectEntity("Project B", "Description 2")
                                            );
        mockedTeam.setTeamMembers(new ArrayList<TeamMemberEntity>());
        Optional<TeamEntity> mockedAnswer = Optional.of(mockedTeam);
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Mock userServiceRules.checkUserRole()
        doNothing().when(userServiceRules)
                    .checkUserRole(any(UserDTO.class), anyString());
        // Mock userServiceRules.checkCurrentUser()
        doThrow(new CustomRuntimeException(CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER)).when(userServiceRules)
                    .checkCurrentUser(any(UserDTO.class));

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(anyInt());
        verify(teamRepository, times(1)).findById(anyInt());

        verify(userServiceRules, times(1)).checkUserRole(any(UserDTO.class), anyString());
        verify(userServiceRules, times(1)).checkCurrentUser(any(UserDTO.class));
        verify(teamServiceRules, times(0)).checkTeamIsFull(any(TeamDTO.class));
        verify(teamServiceRules, times(0)).checkSpecialityRatio(any(TeamDTO.class));

        verify(userService, times(0)).updateUser(any(UserDTO.class));
        verify(roleService, times(0)).deleteRole(any(RoleDTO.class));

        assertEquals(CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER, exception.getMessage());
    }

    @Test
    void applyInATeamTest_TeamIsFull() throws CustomRuntimeException {
        // Mock userService.getUserById() method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("STUDENT_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);
        when(userService.getUserById(1)).thenReturn(mockedUser);        

        // Mock teamRepository.findById() method
        TeamEntity mockedTeam = new TeamEntity(1, 
                                               "Team A",
                                               new ProjectEntity("Project A", "Description 1"),
                                               new ProjectEntity("Project B", "Description 2")
                                            );
        mockedTeam.setTeamMembers(new ArrayList<TeamMemberEntity>());
        Optional<TeamEntity> mockedAnswer = Optional.of(mockedTeam);
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Mock userServiceRules.checkUserRole()
        doNothing().when(userServiceRules)
                    .checkUserRole(any(UserDTO.class), anyString());
        // Mock userServiceRules.checkCurrentUser()
        doNothing().when(userServiceRules)
                    .checkCurrentUser(any(UserDTO.class));
        // Mock teamServiceRules.checkTeamIsFull()
        doThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_IS_FULL)).when(teamServiceRules)
                    .checkTeamIsFull(any(TeamDTO.class));

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(anyInt());
        verify(teamRepository, times(1)).findById(anyInt());

        verify(userServiceRules, times(1)).checkUserRole(any(UserDTO.class), anyString());
        verify(userServiceRules, times(1)).checkCurrentUser(any(UserDTO.class));
        verify(teamServiceRules, times(1)).checkTeamIsFull(any(TeamDTO.class));
        verify(teamServiceRules, times(0)).checkSpecialityRatio(any(TeamDTO.class));

        verify(userService, times(0)).updateUser(any(UserDTO.class));
        verify(roleService, times(0)).deleteRole(any(RoleDTO.class));

        assertEquals(CustomRuntimeException.TEAM_IS_FULL, exception.getMessage());
    }

    @Test
    void applyInATeamTest_TeamHasAlready2CSS() throws CustomRuntimeException {
        // Mock userService.getUserById() method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("STUDENT_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);
        when(userService.getUserById(1)).thenReturn(mockedUser);        

        // Mock teamRepository.findById() method
        TeamEntity mockedTeam = new TeamEntity(1, 
                                               "Team A",
                                               new ProjectEntity("Project A", "Description 1"),
                                               new ProjectEntity("Project B", "Description 2")
                                            );
        mockedTeam.setTeamMembers(new ArrayList<TeamMemberEntity>());
        Optional<TeamEntity> mockedAnswer = Optional.of(mockedTeam);
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Mock userServiceRules.checkUserRole()
        doNothing().when(userServiceRules)
                    .checkUserRole(any(UserDTO.class), anyString());
        // Mock userServiceRules.checkCurrentUser()
        doNothing().when(userServiceRules)
                    .checkCurrentUser(any(UserDTO.class));
        // Mock teamServiceRules.checkTeamIsFull()
        doNothing().when(teamServiceRules)
                    .checkTeamIsFull(any(TeamDTO.class));
        // Mock teamServiceRules.checkSpecialityRation()
        doThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_ALREADY_HAS_2_CSS)).when(teamServiceRules)
                    .checkSpecialityRatio(any(TeamDTO.class));

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(anyInt());
        verify(teamRepository, times(1)).findById(anyInt());

        verify(userServiceRules, times(1)).checkUserRole(any(UserDTO.class), anyString());
        verify(userServiceRules, times(1)).checkCurrentUser(any(UserDTO.class));
        verify(teamServiceRules, times(1)).checkTeamIsFull(any(TeamDTO.class));
        verify(teamServiceRules, times(1)).checkSpecialityRatio(any(TeamDTO.class));

        verify(userService, times(0)).updateUser(any(UserDTO.class));
        verify(roleService, times(0)).deleteRole(any(RoleDTO.class));

        assertEquals(CustomRuntimeException.TEAM_ALREADY_HAS_2_CSS, exception.getMessage());
    }

    @Test
    void applyInATeamTest_TeamHasAlready4LD() throws CustomRuntimeException {
        // Mock userService.getUserById() method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("STUDENT_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);
        when(userService.getUserById(1)).thenReturn(mockedUser);        

        // Mock teamRepository.findById() method
        TeamEntity mockedTeam = new TeamEntity(1, 
                                               "Team A",
                                               new ProjectEntity("Project A", "Description 1"),
                                               new ProjectEntity("Project B", "Description 2")
                                            );
        mockedTeam.setTeamMembers(new ArrayList<TeamMemberEntity>());
        Optional<TeamEntity> mockedAnswer = Optional.of(mockedTeam);
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Mock userServiceRules.checkUserRole()
        doNothing().when(userServiceRules)
                    .checkUserRole(any(UserDTO.class), anyString());
        // Mock userServiceRules.checkCurrentUser()
        doNothing().when(userServiceRules)
                    .checkCurrentUser(any(UserDTO.class));
        // Mock teamServiceRules.checkTeamIsFull()
        doNothing().when(teamServiceRules)
                    .checkTeamIsFull(any(TeamDTO.class));
        // Mock teamServiceRules.checkSpecialityRation()
        doThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_ALREADY_HAS_4_LD)).when(teamServiceRules)
                    .checkSpecialityRatio(any(TeamDTO.class));

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(anyInt());
        verify(teamRepository, times(1)).findById(anyInt());

        verify(userServiceRules, times(1)).checkUserRole(any(UserDTO.class), anyString());
        verify(userServiceRules, times(1)).checkCurrentUser(any(UserDTO.class));
        verify(teamServiceRules, times(1)).checkTeamIsFull(any(TeamDTO.class));
        verify(teamServiceRules, times(1)).checkSpecialityRatio(any(TeamDTO.class));

        verify(userService, times(0)).updateUser(any(UserDTO.class));
        verify(roleService, times(0)).deleteRole(any(RoleDTO.class));

        assertEquals(CustomRuntimeException.TEAM_ALREADY_HAS_4_LD, exception.getMessage());
    }

    @Test
    void testCreateTeams_NominalWithTeams() throws CustomRuntimeException {
        // Mock securityConfig.getCurrentUser() method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("OPTION_LEADER_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);

        when(securityConfig.getCurrentUser()).thenReturn(mockedUser);

        // Mock userServiceRules.checkUserRole() method
        doNothing().when(userServiceRules).checkUserRole(any(UserDTO.class), anyString());

        // Mock projectService.createProjects(2, 2) method
        ProjectDTO mockedProject1 = new ProjectDTO("Project 3", "Description 3");
        ProjectDTO mockedProject2 = new ProjectDTO("Project 4", "Description 4");
        List<ProjectDTO> mocketProjects = new ArrayList<ProjectDTO>();
        mocketProjects.add(mockedProject1);
        mocketProjects.add(mockedProject2);
        when(projectService.createProjects(2, 2)).thenReturn(mocketProjects);

        // Mock teamRepository.findAll() method
        List<TeamEntity> mockedAnswer = new ArrayList<TeamEntity>();
        mockedAnswer.add(new TeamEntity(
            1, 
            "Équipe 1", 
            new ProjectEntity("Projet 1", "Description 1"), 
            new ProjectEntity("Projet 2", "Description 2")
        ));
        mockedAnswer.add(
            new TeamEntity(
                2, 
                "Équipe 2",
                new ProjectEntity("Projet 2", "Description 2"),
                new ProjectEntity("Projet 1", "Description 1")
            ));
        when(teamRepository.findAll()).thenReturn(mockedAnswer);

        // Mock teamRepository.save() method
        when(teamRepository.save(any(TeamEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        // Define the expected answer
        List<TeamDTO> expectedAnswer = new ArrayList<TeamDTO>();
        expectedAnswer.add(new TeamDTO(
            3, 
            "Équipe 3", 
            new ProjectDTO("Projet 3", "Description 3"), 
            new ProjectDTO("Projet 4", "Description 4")
        ));
        expectedAnswer.add(new TeamDTO(
            4, 
            "Équipe 4", 
            new ProjectDTO("Projet 4", "Description 4"), 
            new ProjectDTO("Projet 3", "Description 3")
        ));

        // Call the method to test
        List<TeamDTO> result = teamService.createTeams(1);

        // Verify the result
        verify(securityConfig, times(1)).getCurrentUser();
        verify(userServiceRules, times(1)).checkUserRole(any(UserDTO.class), anyString());
        verify(projectService, times(1)).createProjects(2, 2);
        verify(teamRepository, times(1)).findAll();
        verify(teamRepository, times(2)).save(any(TeamEntity.class));
        
        assertEquals(2, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testCreateTeams_NominalWithoutTeams() throws CustomRuntimeException {
        // Mock securityConfig.getCurrentUser() method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("OPTION_LEADER_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);

        when(securityConfig.getCurrentUser()).thenReturn(mockedUser);

        // Mock userServiceRules.checkUserRole() method
        doNothing().when(userServiceRules).checkUserRole(any(UserDTO.class), anyString());

        // Mock projectService.createProjects(2, 2) method
        ProjectDTO mockedProject1 = new ProjectDTO("Project 1", "Description 1");
        ProjectDTO mockedProject2 = new ProjectDTO("Project 2", "Description 2");
        List<ProjectDTO> mocketProjects = new ArrayList<ProjectDTO>();
        mocketProjects.add(mockedProject1);
        mocketProjects.add(mockedProject2);
        when(projectService.createProjects(2, 0)).thenReturn(mocketProjects);

        // Mock teamRepository.findAll() method
        List<TeamEntity> mockedAnswer = new ArrayList<TeamEntity>();
        when(teamRepository.findAll()).thenReturn(mockedAnswer);

        // Mock teamRepository.save() method
        when(teamRepository.save(any(TeamEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        // Define the expected answer
        List<TeamDTO> expectedAnswer = new ArrayList<TeamDTO>();
        expectedAnswer.add(new TeamDTO(
            1, 
            "Équipe 1", 
            new ProjectDTO("Projet 1", "Description 1"), 
            new ProjectDTO("Projet 2", "Description 2")
        ));
        expectedAnswer.add(new TeamDTO(
            2, 
            "Équipe 2", 
            new ProjectDTO("Projet 2", "Description 2"), 
            new ProjectDTO("Projet 1", "Description 1")
        ));

        // Call the method to test
        List<TeamDTO> result = teamService.createTeams(1);

        // Verify the result
        verify(securityConfig, times(1)).getCurrentUser();
        verify(userServiceRules, times(1)).checkUserRole(any(UserDTO.class), anyString());
        verify(projectService, times(1)).createProjects(2, 0);
        verify(teamRepository, times(1)).findAll();
        verify(teamRepository, times(2)).save(any(TeamEntity.class));
        
        assertEquals(2, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testCreateTeams_UserIsNotAnOptionLeader() throws CustomRuntimeException{
        // Mock securityCongif.getCurrentUser method
        UserDTO mockedUser = new UserDTO();
        mockedUser.setId(1);
        mockedUser.setFirstname("jean");
        mockedUser.setLastname("dupont");
        mockedUser.setEmail("jdupont@reseau.eseo.fr");
        mockedUser.setLogin("jdupont");
        mockedUser.setPassword("$2a$12$beDKCRFS7AkSAzqfuVAgjemzWSbtYRMmGmg6lMmSqymZet9egfL7q");

        RoleDTO mockedRole = new RoleDTO();
        mockedRole.setIdRole(1);
        mockedRole.setRole("STUDENT_ROLE");
        mockedRole.setUser(mockedUser);

        mockedUser.setRoles(new ArrayList<RoleDTO>());
        mockedUser.getRoles().add(mockedRole);
    
        when(securityConfig.getCurrentUser()).thenReturn(mockedUser);

        // Mock userServiceRules.checkUserRole() method
        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AN_OPTION_LEADER))
            .when(userServiceRules).checkUserRole(any(UserDTO.class), anyString());
        
        // Call the method to test
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            teamService.createTeams(1);
        });

        // Verify the result
        verify(securityConfig, times(1)).getCurrentUser();
        verify(userServiceRules, times(1)).checkUserRole(any(UserDTO.class), anyString());
        assertEquals(CustomRuntimeException.USER_IS_NOT_AN_OPTION_LEADER, exception.getMessage());
    }

    @Test
    void testCreateTeams_Number_Teams_Invalid() throws CustomRuntimeException {
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            teamService.createTeams(0);
        });
        assertEquals(CustomRuntimeException.NB_TEAMS_INVALID, exception.getMessage());
    }

    @Test
    void testCreateTeams_ServiceError() throws CustomRuntimeException {
        // Mock securityConfig.getCurrentUser() method
        when(securityConfig.getCurrentUser()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call the method to test
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            teamService.createTeams(1);
        });

        // Verify the result
        verify(securityConfig, times(1)).getCurrentUser();
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    void testAddTestBookLink() throws CustomRuntimeException {
        // Mock teamRepository.findById() method
        TeamEntity teamEntity = new TeamEntity(1, "Team A", null, null);
        TeamEntity pairTeamEntity = new TeamEntity(2, "Team B", null, null);
        
        // Add projectValidation to team
        ProjectEntity projectValidation = new ProjectEntity();
        projectValidation.setIdProject(2);
        teamEntity.setProjectValidation(projectValidation);
        
        Optional<TeamEntity> mockedAnswer = Optional.of(teamEntity);
        Optional<TeamEntity> pairTeamMockedAnswer = Optional.of(pairTeamEntity);
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);
        when(teamRepository.findById(2)).thenReturn(pairTeamMockedAnswer);

        // Mock teamRepository.save() method
        when(teamRepository.save(any(TeamEntity.class))).thenAnswer(i -> i.getArguments()[0]);

            // Mock team members of pair team
        List<TeamMemberEntity> pairTeamMembers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TeamMemberEntity member = new TeamMemberEntity();
            UserEntity user = new UserEntity();
            user.setId(i+1);
            member.setUser(user);
            pairTeamMembers.add(member);
        }

        pairTeamEntity.setTeamMembers(pairTeamMembers);


        // Prepare input
        TeamDTO input = new TeamDTO();
        input.setIdTeam(1);
        input.setTestBookLink("https://testbook.com/testlink");

        // Call the method to test
        TeamDTO result = new TeamDTO();
        result = teamService.addTestBookLink(input);
        
        // Verify the result
        verify(teamRepository, times(2)).findById(any(Integer.class));
        verify(teamRepository, times(1)).save(any(TeamEntity.class));
        assertEquals("https://testbook.com/testlink", result.getTestBookLink());

        // Verify notifications were sent to each member of the pair team
        verify(notificationService, times(3)).createNotification(any(NotificationDTO.class));
    }

    @Test
    void testGetTestBookLinkDev() {
        // Mock teamRepository.findById() method
        TeamEntity teamEntity = new TeamEntity(1, "Team A", null, null);
        teamEntity.setTestBookLink("https://testbook.com/testlink");
        Optional<TeamEntity> mockedAnswer = Optional.of(teamEntity);
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Call the method to test
        String result = "";
        try {
            result = teamService.getTestBookLinkDev(1);
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(teamRepository, times(1)).findById(1);
        assertEquals("https://testbook.com/testlink", result);
    }

    @Test
    void testSetTeamWorkMarkByIdSuccess() throws CustomRuntimeException {
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        TeamEntity team = new TeamEntity();

        int mark = 2;
        team.setTeamWorkMark(mark);

        ArgumentCaptor<TeamEntity> captor = ArgumentCaptor.forClass(TeamEntity.class);
        when(teamRepository.save(captor.capture())).thenReturn(team);

        Optional<TeamEntity> mockedTeam = Optional.of(team);
        when(teamRepository.findById(mockedTeam.get().getIdTeam())).thenReturn(mockedTeam);
        mockedTeam.get().setTeamWorkMark(0); // set bonus penalty to 0 initially

        teamService.setTeamWorkMarkById(mockedTeam.get().getIdTeam(), mark);

        assertEquals(mark, captor.getValue().getTeamWorkMark());
    }

    @Test
    void testSetteamWorkMarkByIdUserNotAuthorized() throws CustomRuntimeException {
        // Mock des inputs
        Integer id = 1;
        Integer teamWorkMark = 15;

        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AUTHORIZED))
        .when(userServiceRules).checkCurrentUserRole(RoleDTO.JURY_MEMBER_ROLE);

        // Appel de la méthode à tester et vérification de l'exception levée
        CustomRuntimeException thrownException = assertThrows(CustomRuntimeException.class, () -> {
            teamService.setTeamWorkMarkById(id, teamWorkMark);
        });

        assertEquals(CustomRuntimeException.USER_IS_NOT_AUTHORIZED, thrownException.getMessage());
    }

    @Test
    void testSetTeamValidationMarkById_Nominal() throws CustomRuntimeException {
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        TeamEntity team = new TeamEntity();

        int mark = 2;
        team.setTeamValidationMark(mark);

        ArgumentCaptor<TeamEntity> captor = ArgumentCaptor.forClass(TeamEntity.class);
        when(teamRepository.save(captor.capture())).thenReturn(team);

        Optional<TeamEntity> mockedTeam = Optional.of(team);
        when(teamRepository.findById(mockedTeam.get().getIdTeam())).thenReturn(mockedTeam);
        mockedTeam.get().setTeamValidationMark(0); // set bonus penalty to 0 initially

        teamService.setTeamValidationMarkById(mockedTeam.get().getIdTeam(), mark);

        assertEquals(mark, captor.getValue().getTeamValidationMark());
    }

    @Test
    void testSetteamValidationMarkById_UserNotAuthorized() throws CustomRuntimeException {
        // Mock des inputs
        Integer id = 1;
        Integer teamValidationMark = 15;

        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AUTHORIZED))
        .when(userServiceRules).checkCurrentUserRole(RoleDTO.JURY_MEMBER_ROLE);

        // Appel de la méthode à tester et vérification de l'exception levée
        CustomRuntimeException thrownException = assertThrows(CustomRuntimeException.class, () -> {
            teamService.setTeamValidationMarkById(id, teamValidationMark);
        });

        assertEquals(CustomRuntimeException.USER_IS_NOT_AUTHORIZED, thrownException.getMessage());
    }
    
    @Test
    void testGetTestBookLinkValidation() throws CustomRuntimeException {
        // Préparation des données
        Integer idTeam = 1;
        Integer pairedTeamId = 2;
        String pairedTeamLink = "https://testbook.com/testlink";

        // Mock teamRepository.findById() method
        TeamEntity teamEntity = new TeamEntity(idTeam, "Team A", null, null);
        TeamEntity pairedTeamEntity = new TeamEntity(pairedTeamId, "Team B", null, null);
        pairedTeamEntity.setTestBookLink(pairedTeamLink);

        // Add projectValidation to team
        ProjectEntity projectValidation = new ProjectEntity();
        projectValidation.setIdProject(pairedTeamId);
        teamEntity.setProjectValidation(projectValidation);

        Optional<TeamEntity> mockedAnswer = Optional.of(teamEntity);
        Optional<TeamEntity> pairedTeamMockedAnswer = Optional.of(pairedTeamEntity);
        when(teamRepository.findById(idTeam)).thenReturn(mockedAnswer);
        when(teamRepository.findById(pairedTeamId)).thenReturn(pairedTeamMockedAnswer);

        // Appel de la méthode à tester
        String result = teamService.getTestBookLinkValidation(idTeam);

        // Vérification du résultat
        verify(teamRepository, times(2)).findById(any(Integer.class));
        assertEquals(pairedTeamLink, result);
    }

    @Test
    void testSetCommentOnReport_Nominal() throws CustomRuntimeException {
        ProjectEntity mockedProject = new ProjectEntity(null, null);
        mockedProject.setJury(new JuryEntity(
            new TeachingStaffEntity(
                new UserEntity(1, null, null, null, null, null, null)
            ), 
            new TeachingStaffEntity(
                new UserEntity(2, null, null, null, null, null, null)
            )
        ));
        TeamEntity mockedTeam = new TeamEntity(1, null, mockedProject, null);
        Optional<TeamEntity> mockedAnswer = Optional.of(mockedTeam);

        // Mock userServiceRules.checkUserRole()
        doNothing().when(userServiceRules).checkCurrentUserRole(RoleDTO.JURY_MEMBER_ROLE);
        // Mock juryServiceRules.checkJuryMemberManageTeam
        doNothing().when(juryServiceRules).checkJuryMemberManageTeam(any(Integer.class), any(TeamDTO.class));
        
        UserDTO mockedUser = new UserDTO(1, null, null, null, null, null, null);
        when(userServiceRules.getCurrentUser()).thenReturn(mockedUser);

        ProjectDTO expectedProject = new ProjectDTO(null, null);
        expectedProject.setJury(new JuryDTO(
            new TeachingStaffDTO(
                new UserDTO(1, null, null, null, null, null, null)
            ), 
            new TeachingStaffDTO(
                new UserDTO(2, null, null, null, null, null, null)
            )
        ));
        TeamDTO expectedAnswer = new TeamDTO(1, null, expectedProject, null);
        expectedAnswer.setReportComments("comment");

        when(teamRepository.findById(1)).thenReturn(mockedAnswer);
        when(teamRepository.save(any(TeamEntity.class))).then(AdditionalAnswers.returnsFirstArg());

        TeamDTO result = new TeamDTO();
        try {
            result = teamService.setCommentOnReport(1, "comment");
        } catch (CustomRuntimeException e) {
            fail();
        }

        assertEquals(expectedAnswer.getIdTeam(), result.getIdTeam());
        assertEquals(expectedAnswer.getReportComments(), result.getReportComments());
    }

    @Test
    void testSetCommentOnReport_UserNotJury() throws CustomRuntimeException {
        // Mock userServiceRules.checkUserRole()
        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AUTHORIZED))
        .when(userServiceRules).checkCurrentUserRole(RoleDTO.JURY_MEMBER_ROLE);
        
        CustomRuntimeException thrownException = assertThrows(CustomRuntimeException.class, () -> {
            teamService.setCommentOnReport(1, "comment");
        });

        assertEquals(CustomRuntimeException.USER_IS_NOT_AUTHORIZED, thrownException.getMessage());
    }

    @Test
    void testSetCommentOnReport_UserNotJuryOfTeam() throws CustomRuntimeException {
        ProjectEntity mockedProject = new ProjectEntity(null, null);
        mockedProject.setJury(new JuryEntity(
            new TeachingStaffEntity(
                new UserEntity(1, null, null, null, null, null, null)
            ), 
            new TeachingStaffEntity(
                new UserEntity(2, null, null, null, null, null, null)
            )
        ));
        TeamEntity mockedTeam = new TeamEntity(1, null, mockedProject, null);
        Optional<TeamEntity> mockedAnswer = Optional.of(mockedTeam);

        UserDTO mockedUser = new UserDTO(1, null, null, null, null, null, null);
        when(userServiceRules.getCurrentUser()).thenReturn(mockedUser);
        
        when(teamRepository.findById(anyInt())).thenReturn(mockedAnswer);
        // Mock userServiceRules.checkUserRole()
        doNothing().when(userServiceRules).checkCurrentUserRole(RoleDTO.JURY_MEMBER_ROLE);
        // Mock userServiceRules.checkUserRole()
        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AUTHORIZED))
        .when(juryServiceRules).checkJuryMemberManageTeam(any(Integer.class), any(TeamDTO.class));
        
        CustomRuntimeException thrownException = assertThrows(CustomRuntimeException.class, () -> {
            teamService.setCommentOnReport(1, "comment");
        });

        assertEquals(CustomRuntimeException.USER_IS_NOT_AUTHORIZED, thrownException.getMessage());
    }
}

