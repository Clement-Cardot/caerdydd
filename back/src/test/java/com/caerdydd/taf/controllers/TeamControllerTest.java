package com.caerdydd.taf.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.FileService;
import com.caerdydd.taf.services.TeamService;

@ExtendWith(MockitoExtension.class)
public class TeamControllerTest {

    @InjectMocks
    private TeamController teamController;

    @Mock
    private TeamService teamService;

    @Mock
    private FileService fileService;

    @Test
    void testGetAllTeams_Nominal() throws CustomRuntimeException {
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
    void testGetAllTeams_Empty() throws CustomRuntimeException {
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
    void testGetAllTeams_ServiceError() throws CustomRuntimeException {
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
    void testGetAllTeams_UnexpectedError() throws CustomRuntimeException {
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

        UserDTO user1 = new UserDTO("Firstname1", "Lastname1", "user1", "password1", "email1", "LD");
        UserDTO user2 = new UserDTO("Firstname2", "Lastname2", "user2", "password2", "email2", "CSS");
        
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
        UserDTO mockedAnswer = new UserDTO("Firstname1", "Lastname1", "user1", "password1", "email1", "LD");
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

    @Test
    void testAddTestBookLink_Nominal() throws CustomRuntimeException {
        // Mock teamService.addTestBookLink() method
        when(teamService.addTestBookLink(any(TeamDTO.class))).thenAnswer(i -> i.getArguments()[0]);

        // Prepare request body
        TeamDTO input = new TeamDTO();
        input.setIdTeam(1);
        input.setTestBookLink("https://www.google.com");

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(input, HttpStatus.OK);

        // Call the method to test
        ResponseEntity<TeamDTO> result = teamController.addTestBookLink(input);

        // Verify the result
        verify(teamService, times(1)).addTestBookLink(any(TeamDTO.class));
        assertEquals(expectedAnswer, result);
    }

    @Test
    void testAddTestBookLink_TeamNotFound() throws CustomRuntimeException {
        // Mock teamService.addTestBookLink() method
        when(teamService.addTestBookLink(any(TeamDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND));

        // Prepare request body
        TeamDTO input = new TeamDTO();
        input.setIdTeam(1);
        input.setTestBookLink("https://www.google.com");

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<TeamDTO> result = teamController.addTestBookLink(input);

        // Verify the result
        verify(teamService, times(1)).addTestBookLink(any(TeamDTO.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testAddTestBookLink_InvalidLink() throws CustomRuntimeException{
        // Mock teamService.addTestBookLink() method
        when(teamService.addTestBookLink(any(TeamDTO.class))).thenThrow(new CustomRuntimeException(CustomRuntimeException.INVALID_LINK));

        // Prepare request body
        TeamDTO input = new TeamDTO();
        input.setIdTeam(1);
        input.setTestBookLink("https://www.google.com");

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        // Call the method to test
        ResponseEntity<TeamDTO> result = teamController.addTestBookLink(input);

        // Verify the result
        verify(teamService, times(1)).addTestBookLink(any(TeamDTO.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testAddTestBookLink_UnexpectedError() throws CustomRuntimeException {
        // Mock teamService.addTestBookLink() method
        when(teamService.addTestBookLink(any(TeamDTO.class))).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Prepare request body
        TeamDTO input = new TeamDTO();
        input.setIdTeam(1);
        input.setTestBookLink("https://www.google.com");

        // Define the expected response
        ResponseEntity<TeamDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<TeamDTO> result = teamController.addTestBookLink(input);

        // Verify the result
        verify(teamService, times(1)).addTestBookLink(any(TeamDTO.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTestBookLinkDev_Nominal() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkDev() method
        String mockedTestBookLinkDev = "https://example.com/testbooklinkdev";
        when(teamService.getTestBookLinkDev(1)).thenReturn(mockedTestBookLinkDev);

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(mockedTestBookLinkDev, HttpStatus.OK);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkDev(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkDev(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTestBookLinkDev_TeamNotFound() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkDev() method
        when(teamService.getTestBookLinkDev(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND));

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkDev(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkDev(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTestBookLinkDev_LinkNotFound() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkDev() method
        when(teamService.getTestBookLinkDev(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.LINK_NOT_FOUND));

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkDev(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkDev(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTestBookLinkDev_UnexpectedError() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkDev() method
        when(teamService.getTestBookLinkDev(1)).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkDev(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkDev(anyInt());
        assertEquals(expectedAnswer, result);
    }

    @Test
    void testGetTestBookLinkValidation_Nominal() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkValidation() method
        String mockedTestBookLinkValidation = "https://example.com/testbooklinkvalidation";
        when(teamService.getTestBookLinkValidation(1)).thenReturn(mockedTestBookLinkValidation);

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(mockedTestBookLinkValidation, HttpStatus.OK);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkValidation(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkValidation(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTestBookLinkValidation_TeamNotFound() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkValidation() method
        when(teamService.getTestBookLinkValidation(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND));

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkValidation(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkValidation(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTestBookLinkValidation_LinkNotFound() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkValidation() method
        when(teamService.getTestBookLinkValidation(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.LINK_NOT_FOUND));

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkValidation(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkValidation(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTestBookLinkValidation_UnexpectedError() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkValidation() method
        when(teamService.getTestBookLinkValidation(1)).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkValidation(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkValidation(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTestBookLinkDev_LinkNotFound2() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkDev() method
        when(teamService.getTestBookLinkDev(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.LINK_NOT_FOUND));

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkDev(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkDev(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTestBookLinkValidation_LinkNotFound2() throws CustomRuntimeException {
        // Mock teamService.getTestBookLinkValidation() method
        when(teamService.getTestBookLinkValidation(1)).thenThrow(new CustomRuntimeException(CustomRuntimeException.LINK_NOT_FOUND));

        // Define the expected response
        ResponseEntity<String> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<String> result = teamController.getTestBookLinkValidation(1);

        // Verify the result
        verify(teamService, times(1)).getTestBookLinkValidation(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testSaveFile_Nominal() throws CustomRuntimeException {
        // Mock fileService.saveFile() method
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some xml".getBytes());
        doNothing().when(fileService).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));

        // Define the expected response
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.ACCEPTED);

        // Call the method to test
        ResponseEntity<HttpStatus> result = teamController.saveFile(file, 1, "test");

        // Verify the result
        verify(fileService, times(1)).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testSaveFile_ServiceError() throws CustomRuntimeException {
        // Mock fileService.saveFile() method
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some xml".getBytes());
        doThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR)).when(fileService).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));

        // Define the expected response
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        // Call the method to test
        ResponseEntity<HttpStatus> result = teamController.saveFile(file, 1, "test");

        // Verify the result
        verify(fileService, times(1)).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testSaveFile_TeamNotFound() throws CustomRuntimeException {
        // Mock fileService.saveFile() method
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some xml".getBytes());
        doThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND)).when(fileService).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));

        // Define the expected response
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        // Call the method to test
        ResponseEntity<HttpStatus> result = teamController.saveFile(file, 1, "test");

        // Verify the result
        verify(fileService, times(1)).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testSaveFile_IncorrectFileFormat() throws CustomRuntimeException {
        // Mock fileService.saveFile() method
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some xml".getBytes());
        doThrow(new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT)).when(fileService).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));

        // Define the expected response
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);

        // Call the method to test
        ResponseEntity<HttpStatus> result = teamController.saveFile(file, 1, "test");

        // Verify the result
        verify(fileService, times(1)).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testSaveFile_UnexpectedError() throws CustomRuntimeException {
        // Mock fileService.saveFile() method
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some xml".getBytes());
        doThrow(new CustomRuntimeException("Unexpected error")).when(fileService).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));

        // Define the expected response
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Call the method to test
        ResponseEntity<HttpStatus> result = teamController.saveFile(file, 1, "test");

        // Verify the result
        verify(fileService, times(1)).saveFile(any(MultipartFile.class), any(Integer.class), any(String.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testViewFile_Nominal() throws CustomRuntimeException, IOException {
        Resource mockedResource = new UrlResource(Paths.get("test").toAbsolutePath().normalize().toUri());
        // Mock fileService.saveFile() method
        when(fileService.loadFileAsResource(any(Integer.class), any(String.class))).thenReturn(mockedResource);

        InputStreamResource mockedStream = new InputStreamResource(mockedResource.getInputStream());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", "Equipe-1_test.pdf");

        // Define the expected response
        ResponseEntity<InputStreamResource> expectedAnswer = new ResponseEntity<>(mockedStream, headers, HttpStatus.ACCEPTED);

        // Call the method to test
        ResponseEntity<InputStreamResource> result = teamController.viewFile(1, "test");

        // Verify the result
        verify(fileService, times(1)).loadFileAsResource(any(Integer.class), any(String.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testViewFile_ServiceError() throws CustomRuntimeException, IOException {
        // Mock fileService.saveFile() method
        doThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR)).when(fileService).loadFileAsResource(any(Integer.class), any(String.class));

        // Define the expected response
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        // Call the method to test
        ResponseEntity<InputStreamResource> result = teamController.viewFile(1, "test");

        // Verify the result
        verify(fileService, times(1)).loadFileAsResource(any(Integer.class), any(String.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testViewFile_FileNotFound() throws CustomRuntimeException, IOException {
        // Mock fileService.saveFile() method
        doThrow(new CustomRuntimeException(CustomRuntimeException.FILE_NOT_FOUND)).when(fileService).loadFileAsResource(any(Integer.class), any(String.class));

        // Define the expected response
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // Call the method to test
        ResponseEntity<InputStreamResource> result = teamController.viewFile(1, "test");

        // Verify the result
        verify(fileService, times(1)).loadFileAsResource(any(Integer.class), any(String.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testViewFile_UnexpectedError() throws CustomRuntimeException, IOException {
        // Mock fileService.saveFile() method
        doThrow(new CustomRuntimeException("Unexpected error")).when(fileService).loadFileAsResource(any(Integer.class), any(String.class));

        // Define the expected response
        ResponseEntity<HttpStatus> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        // Call the method to test
        ResponseEntity<InputStreamResource> result = teamController.viewFile(1, "test");

        // Verify the result
        verify(fileService, times(1)).loadFileAsResource(any(Integer.class), any(String.class));
        assertEquals(expectedAnswer.toString(), result.toString());
    }
    @Test
    public void testSetTeamMarks() throws CustomRuntimeException {
        // Mock the required objects and data
        Integer teamId = 1;
        Integer teamWorkMark = 80;
        Integer teamValidationMark = 90;
        TeamDTO team = new TeamDTO();
        // ... set up the team object
        
        // Set up the mock behavior
        when(teamService.setTeamWorkMarkById(teamId, teamWorkMark)).thenReturn(team);
        when(teamService.setTeamValidationMarkById(teamId, teamValidationMark)).thenReturn(team);
        
        // Call the method to be tested
        ResponseEntity<TeamDTO> response = teamController.setTeamMarks(teamId, teamWorkMark, teamValidationMark);
        
        // Verify the behavior
        verify(teamService).setTeamWorkMarkById(teamId, teamWorkMark);
        verify(teamService).setTeamValidationMarkById(teamId, teamValidationMark);
        
    }
    
}
