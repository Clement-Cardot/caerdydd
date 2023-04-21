package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.caerdydd.taf.models.dto.ProjectDTO;
import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeamService;

@ExtendWith(MockitoExtension.class)
public class TeamControllerTest {

    @InjectMocks
    private TeamController teamController;

    @Mock
    private TeamService teamService;

    @Test
    public void testGetAllTeams_Nominal() throws CustomRuntimeException {
        // Mock teamService.listAllTeams() method
        List<TeamDTO> mockedAnswer = new ArrayList<>();
        mockedAnswer.add(new TeamDTO(
                                1, 
                                "Team 1",
                                new ProjectDTO("Project 1", "Description 1"),
                                new ProjectDTO("Project 2", "Description 2")
                            ));
        mockedAnswer.add(new TeamDTO(
                                2, 
                                "Team 2",
                                new ProjectDTO("Project 2", "Description 2"),
                                new ProjectDTO("Project 1", "Description 1")
                            ));
        when(teamService.listAllTeams()).thenReturn(mockedAnswer);

        // Define the expected response
        ResponseEntity<List<TeamDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<List<TeamDTO>> result = teamController.getAllTeams();

        // Verify the result
        verify(teamService, times(1)).listAllTeams();
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testGetAllTeams_Empty() throws CustomRuntimeException {
        // Mock teamService.listAllTeams() method
        List<TeamDTO> mockedAnswer = new ArrayList<>();
        when(teamService.listAllTeams()).thenReturn(mockedAnswer);

        // Define the expected response
        ResponseEntity<List<TeamDTO>> expectedAnswer = new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<List<TeamDTO>> result = teamController.getAllTeams();

        // Verify the result
        verify(teamService, times(1)).listAllTeams();
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testGetAllTeams_ServiceError() throws CustomRuntimeException {
        // Mock teamService.listAllTeams() method
        when(teamService.listAllTeams()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected response
        ResponseEntity<List<TeamDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<List<TeamDTO>> result = teamController.getAllTeams();

        // Verify the result
        verify(teamService, times(1)).listAllTeams();
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    public void testGetAllTeams_UnexpectedError() throws CustomRuntimeException {
        // Mock teamService.listAllTeams() method
        when(teamService.listAllTeams()).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected response
        ResponseEntity<List<TeamDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<List<TeamDTO>> result = teamController.getAllTeams();

        // Verify the result
        verify(teamService, times(1)).listAllTeams();
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeamById_Nominal() throws CustomRuntimeException {
        // Mock teamService.getTeamById() method
        TeamDTO mockedAnswer = new TeamDTO(
                                        1, 
                                        "Team 1",
                                        new ProjectDTO("Project 1", "Description 1"),
                                        new ProjectDTO("Project 2", "Description 2")
                                        );
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);

        // Call the method to test
        ResponseEntity<TeamDTO> result = teamController.getTeamById(1);

        // Verify the result
        verify(teamService, times(1)).getTeamById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeamById_TeamNotFound() throws CustomRuntimeException {
        // Mock teamService.getTeamById() method
        when(teamService.getTeamById(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND));   

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<TeamDTO> result = teamController.getTeamById(1);

        // Verify the result
        verify(teamService, times(1)).getTeamById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeamById_ServiceError() throws CustomRuntimeException {
        // Mock teamService.getTeamById() method
        when(teamService.getTeamById(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));   

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<TeamDTO> result = teamController.getTeamById(1);

        // Verify the result
        verify(teamService, times(1)).getTeamById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeamById_UnexpectedError() throws CustomRuntimeException {
        // Mock teamService.getTeamById() method
        when(teamService.getTeamById(1)).thenThrow(new CustomRuntimeException("Unexpected error"));   

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<TeamDTO> result = teamController.getTeamById(1);

        // Verify the result
        verify(teamService, times(1)).getTeamById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetAllTeamMembersOfTeamById_Nominal() throws CustomRuntimeException {
        // Mock teamService.getTeamById() method
        TeamDTO mockedAnswer = new TeamDTO(
                                    1, 
                                    "Team 1",
                                    new ProjectDTO("Project 1", "Description 1"),
                                    new ProjectDTO("Project 2", "Description 2")
                                );
        List<TeamMemberDTO> teamMembers = new ArrayList<>();

        UserDTO user1 = new UserDTO(1, "Firstname1", "Lastname1", "user1", "password1", "email1", "LD");
        UserDTO user2 = new UserDTO(2, "Firstname2", "Lastname2", "user2", "password2", "email2", "CSS");
        
        TeamMemberDTO teamMember1 = new TeamMemberDTO(user1, mockedAnswer);
        TeamMemberDTO teamMember2 = new TeamMemberDTO(user2, mockedAnswer);
        
        teamMembers.add(teamMember1);
        teamMembers.add(teamMember2);

        mockedAnswer.setTeamMembers(teamMembers);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        // Define the expected response
        ResponseEntity<List<TeamMemberDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer.getTeamMembers(), HttpStatus.OK);

        // Call the method to test
        ResponseEntity<List<TeamMemberDTO>> result = teamController.getAllTeamMembersOfTeamById(1);

        // Verify the result
        verify(teamService, times(1)).getTeamById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetAllTeamMembersOfTeamById_EmptyTeam() throws CustomRuntimeException {
        // Mock teamService.getTeamById() method
        TeamDTO mockedAnswer = new TeamDTO(
                                        1, 
                                        "Team 1",
                                        new ProjectDTO("Project 1", "Description 1"),
                                        new ProjectDTO("Project 2", "Description 2")
                                    );
        List<TeamMemberDTO> teamMembers = new ArrayList<>();
        mockedAnswer.setTeamMembers(teamMembers);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        // Define the expected response
        ResponseEntity<List<TeamMemberDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer.getTeamMembers(), HttpStatus.OK);

        // Call the method to test
        ResponseEntity<List<TeamMemberDTO>> result = teamController.getAllTeamMembersOfTeamById(1);

        // Verify the result
        verify(teamService, times(1)).getTeamById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetAllTeamMembersOfTeamById_TeamNotFound() throws CustomRuntimeException {
        // Mock teamService.getTeamById() method
        when(teamService.getTeamById(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND));

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<List<TeamMemberDTO>> result = teamController.getAllTeamMembersOfTeamById(1);

        // Verify the result
        verify(teamService, times(1)).getTeamById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetAllTeamMembersOfTeamById_ServiceError() throws CustomRuntimeException {
        // Mock teamService.getTeamById() method
        when(teamService.getTeamById(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<List<TeamMemberDTO>> result = teamController.getAllTeamMembersOfTeamById(1);

        // Verify the result
        verify(teamService, times(1)).getTeamById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetAllTeamMembersOfTeamById_UnexpectedError() throws CustomRuntimeException {
        // Mock teamService.getTeamById() method
        when(teamService.getTeamById(1)).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<List<TeamMemberDTO>> result = teamController.getAllTeamMembersOfTeamById(1);

        // Verify the result
        verify(teamService, times(1)).getTeamById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testApplyInATeam_Nominal() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        UserDTO mockedAnswer = new UserDTO(1, "Firstname1", "Lastname1", "user1", "password1", "email1", "LD");
        when(teamService.applyInATeam(1, 1)).thenReturn(mockedAnswer);
        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(mockedAnswer.toString(), result.getBody().toString());
    }

    @Test
    void testApplyInATeam_CurrentUserIsNotRequestUser() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    void testApplyInATeam_UserIsNotAStudent() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_STUDENT));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    void testApplyInATeam_TeamIsFull() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_IS_FULL));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    void testApplyInATeam_TeamAlreadyHas2CSS() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_ALREADY_HAS_2_CSS));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    void testApplyInATeam_TeamAlreadyHas4LD() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_ALREADY_HAS_4_LD));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    void testApplyInATeam_UserAlreadyInATeam() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_ALREADY_IN_A_TEAM));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
    }

    @Test
    void testApplyInATeam_TeamNotFound() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testApplyInATeam_UserNotFound() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void testApplyInATeam_ServiceError() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void testApplyInATeam_UnexpectedError() throws CustomRuntimeException {
        // Mock teamService.applyInATeam() method
        when(teamService.applyInATeam(1, 1)).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Call the method to test
        ResponseEntity<UserDTO> result = teamController.applyInATeam(1, 1);

        // Verify the result
        verify(teamService, times(1)).applyInATeam(anyInt(), anyInt());
        assertEquals(HttpStatus.I_AM_A_TEAPOT, result.getStatusCode());
    }

    @Test
    void testCreateTeams_Nominal() throws CustomRuntimeException {
        // Mock teamService.createTeams(1) method
        ProjectDTO project1 = new ProjectDTO("Projet 1", "Description 1");
        ProjectDTO project2 = new ProjectDTO("Projet 2", "Description 2");
        TeamDTO team1 = new TeamDTO(1, "Équipe 1", project1, project2);
        TeamDTO team2 = new TeamDTO(2, "Équipe 2", project2, project1);
        List<TeamDTO> mockedAnswer = new ArrayList<TeamDTO>();
        mockedAnswer.add(team1);
        mockedAnswer.add(team2);
        when(teamService.createTeams(1)).thenReturn(mockedAnswer);

        // Call the method to test
        ResponseEntity<List<TeamDTO>> result = teamController.createTeams(1);

        // Verify the result
        verify(teamService, times(1)).createTeams(anyInt());
        assertEquals(mockedAnswer.toString(), result.getBody().toString());

    }

    @Test
    void testCreateTeams_Not_An_Option_Leader() throws CustomRuntimeException {
        // Mock teamService.createTeams(1) method
        when(teamService.createTeams(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AN_OPTION_LEADER));

        // Call the method to test
        ResponseEntity<List<TeamDTO>> result = teamController.createTeams(1);

        // Verify the result
        verify(teamService, times(1)).createTeams(anyInt());
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @Test
    void testCreateTeams_Service_Error() throws CustomRuntimeException {
        // Mock teamService.createTeams(1) method
        when(teamService.createTeams(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Call the method to test
        ResponseEntity<List<TeamDTO>> result = teamController.createTeams(1);

        // Verify the result
        verify(teamService, times(1)).createTeams(anyInt());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void testCreateTeams_UnexpectedError() throws CustomRuntimeException {
        // Mock teamService.createTeams(1) method
        when(teamService.createTeams(1)).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Call the method to test
        ResponseEntity<List<TeamDTO>> result = teamController.createTeams(1);

        // Verify the result
        verify(teamService, times(1)).createTeams(anyInt());
        assertEquals(HttpStatus.I_AM_A_TEAPOT, result.getStatusCode());
    }
}