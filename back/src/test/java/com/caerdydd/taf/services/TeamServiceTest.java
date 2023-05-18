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

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.ProjectEntity;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.models.entities.TeamMemberEntity;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;
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
    public void saveTeamTest_Nominal(){
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
    public void applyInATeamTest_Nominal() throws CustomRuntimeException {
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
    public void applyInATeamTest_UserNotFound() throws CustomRuntimeException {
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
    public void applyInATeamTest_TeamNotFound() throws CustomRuntimeException {
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
    public void applyInATeamTest_UserIsNotAStudent() throws CustomRuntimeException {
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
    public void applyInATeamTest_CurrentUserIsNotRequestUser() throws CustomRuntimeException {
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
    public void applyInATeamTest_TeamIsFull() throws CustomRuntimeException {
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
    public void applyInATeamTest_TeamHasAlready2CSS() throws CustomRuntimeException {
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
    public void applyInATeamTest_TeamHasAlready4LD() throws CustomRuntimeException {
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
    void testSetTeamWorkMarkByIdSuccess() throws CustomRuntimeException {
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        TeamEntity team = new TeamEntity();

        TeamEntity team = new TeamEntity(id, team);

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

        // Appel de la méthode à tester et vérification de l'exception levée
        assertThrows(CustomRuntimeException.class, () -> {
            teamService.setTeamWorkMarkById(id, teamWorkMark);
        });
    }

    @Test
    void testSetTeamValidationMarkByIdSuccess() throws CustomRuntimeException {
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        TeamEntity team = new TeamEntity();

        TeamEntity team = new TeamEntity(user, team);

        int mark = 2;
        team.setTeamValidationMark(mark);

        ArgumentCaptor<TeamEntity> captor = ArgumentCaptor.forClass(TeamEntity.class);
        when(teamRepository.save(captor.capture())).thenReturn(team);

        Optional<TeamEntity> mockedTeam = Optional.of(team);
        when(teamRepository.findById(mockedTeam.get().getIdUser())).thenReturn(mockedTeam);
        mockedTeam.get().setValidationMark(0); // set bonus penalty to 0 initially

        teamService.setTeamValidationMarkById(mockedTeam.get().getIdUser(), mark);

        assertEquals(mark, captor.getValue().getTeamValidationMark());
    }

    @Test
    void testSetteamValidationMarkByIdUserNotAuthorized() throws CustomRuntimeException {
        // Mock des inputs
        Integer id = 1;
        Integer teamValidationMark = 15;

        // Appel de la méthode à tester et vérification de l'exception levée
        assertThrows(CustomRuntimeException.class, () -> {
            teamService.setTeamValidationMarkById(id, teamValidationMark);
        });
    }
    
    
}

