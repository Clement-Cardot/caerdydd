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

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeamMemberService;

@ExtendWith(MockitoExtension.class)
public class TeamMemberControllerTest {
    @InjectMocks
    private TeamMemberController teamMemberController;

    @Mock
    private TeamMemberService teamMemberService;

    @Test
    public void testList_Nominal() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        List<TeamMemberDTO> mockedAnswer = new ArrayList<TeamMemberDTO>();

        UserDTO user1 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        UserDTO user2 = new UserDTO(2, "firstName2", "lastName2", "login2", "password2", "email2", null);

        TeamDTO team1 = new TeamDTO();
        TeamDTO team2 = new TeamDTO();

        mockedAnswer.add(new TeamMemberDTO(user1, team1));
        mockedAnswer.add(new TeamMemberDTO(user2, team2));
        when(teamMemberService.listAllTeamMembers()).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<List<TeamMemberDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<List<TeamMemberDTO>> result = teamMemberController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).listAllTeamMembers();
    }

    @Test
    public void testList_Empty() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        List<TeamMemberDTO> mockedAnswer = new ArrayList<TeamMemberDTO>();
        when(teamMemberService.listAllTeamMembers()).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<List<TeamMemberDTO>> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<List<TeamMemberDTO>> result = teamMemberController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).listAllTeamMembers();
    }

    @Test
    public void testList_ServiceError() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        when(teamMemberService.listAllTeamMembers()).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<List<TeamMemberDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
        // Call the method to test
        ResponseEntity<List<TeamMemberDTO>> result = teamMemberController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).listAllTeamMembers();
    }

    @Test
    public void testList_UnexpectedError() throws CustomRuntimeException{
        // Mock userService.listAllUsers() method
        when(teamMemberService.listAllTeamMembers()).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected answer
        ResponseEntity<List<TeamMemberDTO>> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        
        // Call the method to test
        ResponseEntity<List<TeamMemberDTO>> result = teamMemberController.list();

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).listAllTeamMembers();
    }
    
    @Test
    public void testGet_Nominal() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        UserDTO user1 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        TeamDTO team1 = new TeamDTO();

        TeamMemberDTO mockedAnswer = new TeamMemberDTO(user1, team1);
        when(teamMemberService.getTeamMemberById(anyInt())).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<TeamMemberDTO> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<TeamMemberDTO> result = teamMemberController.get(1);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).getTeamMemberById(anyInt());
    }

    @Test
    public void testGet_UserNotFound() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        when(teamMemberService.getTeamMemberById(anyInt())).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND));

        // Define the expected answer
        ResponseEntity<TeamMemberDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        // Call the method to test
        ResponseEntity<TeamMemberDTO> result = teamMemberController.get(1);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).getTeamMemberById(anyInt());
    }

    @Test
    public void testGet_ServiceError() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        when(teamMemberService.getTeamMemberById(anyInt())).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<TeamMemberDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
        // Call the method to test
        ResponseEntity<TeamMemberDTO> result = teamMemberController.get(1);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).getTeamMemberById(anyInt());
    }

    @Test
    public void testGet_UnexpectedError() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        when(teamMemberService.getTeamMemberById(anyInt())).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected answer
        ResponseEntity<TeamMemberDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        
        // Call the method to test
        ResponseEntity<TeamMemberDTO> result = teamMemberController.get(1);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).getTeamMemberById(anyInt());
    }

    @Test
    public void testSetBonus_Nominal() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        UserDTO user1 = new UserDTO(1, "firstName1", "lastName1", "login1", "password1", "email1", "LD");
        TeamDTO team1 = new TeamDTO();

        TeamMemberDTO mockedAnswer = new TeamMemberDTO(user1, team1);
        when(teamMemberService.setBonusPenaltyById(1, 2)).thenReturn(mockedAnswer);

        // Define the expected answer
        ResponseEntity<TeamMemberDTO> expectedAnswer = new ResponseEntity<>(mockedAnswer, HttpStatus.OK);
        
        // Call the method to test
        ResponseEntity<TeamMemberDTO> result = teamMemberController.setBonusPenalty(1, 2);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).setBonusPenaltyById(1, 2);
    }

    @Test
    public void testSetBonus_ServiceError() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        when(teamMemberService.setBonusPenaltyById(1, 2)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Define the expected answer
        ResponseEntity<TeamMemberDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        
        // Call the method to test
        ResponseEntity<TeamMemberDTO> result = teamMemberController.setBonusPenalty(1, 2);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).setBonusPenaltyById(1, 2);
    }

    @Test
    public void testSetBonus_UnexpectedError() throws CustomRuntimeException{
        // Mock userService.getUserById() method
        when(teamMemberService.setBonusPenaltyById(1, 2)).thenThrow(new CustomRuntimeException("Unexpected error"));

        // Define the expected answer
        ResponseEntity<TeamMemberDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        
        // Call the method to test
        ResponseEntity<TeamMemberDTO> result = teamMemberController.setBonusPenalty(1, 2);

        // Check the result
        assertEquals(expectedAnswer.toString(), result.toString());
        verify(teamMemberService, times(1)).setBonusPenaltyById(1, 2);
    }
}
