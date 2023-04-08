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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock 
    private TeamRepository teamRepository;

    @Mock
    private UserService userService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private SecurityConfig securityConfig;

    @Test
    void testListAllTeams_Nominal() {
        // Mock teamRepository.findAll() method
        List<TeamEntity> mockedAnswer = new ArrayList<TeamEntity>();
        mockedAnswer.add(new TeamEntity(1, "Team A"));
        mockedAnswer.add(new TeamEntity(2, "Team B"));
        when(teamRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<TeamDTO> expectedAnswer = new ArrayList<TeamDTO>();
        expectedAnswer.add(new TeamDTO(1, "Team A"));
        expectedAnswer.add(new TeamDTO(2, "Team B"));

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

    @Test
    void testGetTeamById_Nominal() {
        // Mock teamRepository.findById() method
        Optional<TeamEntity> mockedAnswer = Optional.of(new TeamEntity(1, "Team A"));
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Define the expected answer
        TeamDTO expectedAnswer = new TeamDTO(1, "Team A");

        // Call the method to test
        TeamDTO result = new TeamDTO();
        try {
            result = teamService.getTeamById(1);
        } catch (CustomRuntimeException e) {
            fail();
        } 

        // Verify the result
        verify(teamRepository, times(1)).findById(1);
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

    @Test
    public void saveTeamTest_Nominal(){
        // Mock teamRepository.save() method
        TeamEntity mockedAnswer = new TeamEntity(1, "test");
		when(teamRepository.save(any(TeamEntity.class))).thenReturn(mockedAnswer);
        
        // Prepare the input
        TeamDTO teamToSave = new TeamDTO(1, "test");

        // Call the method to test
        TeamDTO response = teamService.saveTeam(teamToSave);

        // Verify the result
        verify(teamRepository, times(1)).save(any(TeamEntity.class));
        assertEquals(response.toString(), teamToSave.toString());
    }

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

        mockedUser.setRoleEntities(new ArrayList<RoleDTO>());
        mockedUser.getRoleEntities().add(mockedRole);
        when(userService.getUserById(1)).thenReturn(mockedUser);
        when(userService.saveUser(any(UserDTO.class))).then(AdditionalAnswers.returnsFirstArg());

        // Mock teamRepository.findById() method
        Optional<TeamEntity> mockedAnswer = Optional.of(new TeamEntity(1, "Team A"));
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Mock Securityconfig.checkCurrentUser() method
        when(securityConfig.checkCurrentUser(1)).thenReturn(true);

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
        verify(userService, times(1)).getUserById(1);
        verify(userService, times(1)).saveUser(any(UserDTO.class));
        verify(teamRepository, times(1)).findById(1);
        verify(securityConfig, times(1)).checkCurrentUser(1);

        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void applyInATeamTest_AlreadyTeamMember() throws CustomRuntimeException {
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
        mockedRole.setRole("TEAM_MEMBER_ROLE");
        mockedRole.setUser(mockedUser);

        TeamMemberDTO mockedTeamMember = new TeamMemberDTO();
        mockedTeamMember.setUser(mockedUser);
        mockedTeamMember.setTeam(new TeamDTO(1, "Team A"));

        mockedUser.setRoleEntities(new ArrayList<RoleDTO>());
        mockedUser.getRoleEntities().add(mockedRole);
        mockedUser.setTeamMember(mockedTeamMember);

        when(userService.getUserById(1)).thenReturn(mockedUser);

        // Mock teamRepository.findById() method
        Optional<TeamEntity> mockedAnswer = Optional.of(new TeamEntity(1, "Team A"));
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Mock Securityconfig.checkCurrentUser() method
        when(securityConfig.checkCurrentUser(1)).thenReturn(true);
        
        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(1);
        verify(userService, times(0)).saveUser(any(UserDTO.class));
        verify(teamRepository, times(1)).findById(1);
        verify(securityConfig, times(1)).checkCurrentUser(1);
        assertEquals(CustomRuntimeException.USER_ALREADY_IN_A_TEAM, exception.getMessage());
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
        verify(userService, times(1)).getUserById(1);
        verify(userService, times(0)).saveUser(any(UserDTO.class));
        verify(teamRepository, times(0)).findById(1);
        verify(securityConfig, times(0)).checkCurrentUser(1);
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

        TeamMemberDTO mockedTeamMember = new TeamMemberDTO();
        mockedTeamMember.setUser(mockedUser);
        mockedTeamMember.setTeam(new TeamDTO(1, "Team A"));

        mockedUser.setRoleEntities(new ArrayList<RoleDTO>());
        mockedUser.getRoleEntities().add(mockedRole);
        mockedUser.setTeamMember(mockedTeamMember);

        when(userService.getUserById(1)).thenReturn(mockedUser);

        // Mock teamRepository.findById() method
        when(teamRepository.findById(1)).thenReturn(Optional.empty());
        
        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(1);
        verify(teamRepository, times(1)).findById(1);
        verify(userService, times(0)).saveUser(any(UserDTO.class));
        verify(securityConfig, times(0)).checkCurrentUser(1);
        assertEquals(CustomRuntimeException.TEAM_NOT_FOUND, exception.getMessage());
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

        TeamMemberDTO mockedTeamMember = new TeamMemberDTO();
        mockedTeamMember.setUser(mockedUser);
        mockedTeamMember.setTeam(new TeamDTO(1, "Team A"));

        mockedUser.setRoleEntities(new ArrayList<RoleDTO>());
        mockedUser.getRoleEntities().add(mockedRole);
        mockedUser.setTeamMember(mockedTeamMember);

        when(userService.getUserById(1)).thenReturn(mockedUser);

        // Mock teamRepository.findById() method
        Optional<TeamEntity> mockedAnswer = Optional.of(new TeamEntity(1, "Team A"));
        when(teamRepository.findById(1)).thenReturn(mockedAnswer);

        // Mock Securityconfig.checkCurrentUser() method
        when(securityConfig.checkCurrentUser(1)).thenReturn(false);
        // Mock Securityconfig.getCurrentUser() method
        UserDTO mockedCurrentUser = new UserDTO();
        mockedCurrentUser.setId(2);
        when(securityConfig.getCurrentUser()).thenReturn(mockedCurrentUser);
        
        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamService.applyInATeam(1, 1);
        });

        // Verify the result
        verify(userService, times(1)).getUserById(1);
        verify(userService, times(0)).saveUser(any(UserDTO.class));
        verify(teamRepository, times(1)).findById(1);
        verify(securityConfig, times(1)).checkCurrentUser(1);
        assertEquals(CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER, exception.getMessage());
    }

}
