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
    void testList_Nominal() throws CustomRuntimeException{
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
    void testList_Empty() throws CustomRuntimeException{
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
    void testList_ServiceError() throws CustomRuntimeException{
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
    void testList_UnexpectedError() throws CustomRuntimeException{
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
    void testGet_Nominal() throws CustomRuntimeException{
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
    void testGet_UserNotFound() throws CustomRuntimeException{
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
    void testGet_ServiceError() throws CustomRuntimeException{
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
    void testGet_UnexpectedError() throws CustomRuntimeException{
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
    void testSetBonus_Nominal() throws CustomRuntimeException{
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
    void testSetBonusPenalty_TeamMemberNotFound() throws CustomRuntimeException {
        // Mock teamMemberService.setBonusPenaltyById()
        when(teamMemberService.setBonusPenaltyById(1, 2)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND));

        // Call the method to test
        ResponseEntity<TeamMemberDTO> response = teamMemberController.setBonusPenalty(1, 2);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testSetBonus_ServiceError() throws CustomRuntimeException{
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
    void testSetBonus_UnexpectedError() throws CustomRuntimeException{
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

    @Test
    public void setIndividualMarkById_success() throws CustomRuntimeException {
        // Créer un objet TeamMemberDTO factice
        UserDTO user = new UserDTO();
        TeamMemberDTO teamMember = new TeamMemberDTO();
        teamMember.setUser(user);
        teamMember.setIndividualMark(80);

        // Simuler l'appel du service et vérifier le résultat
        when(teamMemberService.setIndividualMarkById(1, 90)).thenReturn(teamMember);
        ResponseEntity<TeamMemberDTO> response = teamMemberController.setIndividualMarkById(1, 90);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(80, response.getBody().getIndividualMark()); // Vérifier que la note individuelle a été mise à jour
    }

    @Test
    public void setIndividualMarkById_teamMemberNotFound() throws CustomRuntimeException {
        // Simuler une exception personnalisée pour le cas où l'id de l'équipe n'existe pas
        when(teamMemberService.setIndividualMarkById(1, 90)).thenThrow(new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND));

        // Appel de la méthode de contrôleur et vérification de la réponse
        ResponseEntity<TeamMemberDTO> response = teamMemberController.setIndividualMarkById(1, 90);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void setIndividualMarkById_serviceError() throws CustomRuntimeException {
        // Simuler une exception personnalisée pour le cas où une erreur de service se produit
        when(teamMemberService.setIndividualMarkById(1, 90)).thenThrow(new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR));

        // Appel de la méthode de contrôleur et vérification de la réponse
        ResponseEntity<TeamMemberDTO> response = teamMemberController.setIndividualMarkById(1, 90);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void setIndividualMarkById_userNotAJuryMember() throws CustomRuntimeException {
        // Simuler une exception personnalisée pour le cas où l'utilisateur n'est pas membre du jury
        when(teamMemberService.setIndividualMarkById(1, 90)).thenThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_JURY_MEMBER));

        // Appel de la méthode de contrôleur et vérification de la réponse
        ResponseEntity<TeamMemberDTO> response = teamMemberController.setIndividualMarkById(1, 90);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void setIndividualMarkById_unexpectedException() throws CustomRuntimeException {
        // Simuler une exception inattendue
        when(teamMemberService.setIndividualMarkById(1, 90)).thenThrow(new CustomRuntimeException("Unexpected error"));

        ResponseEntity<TeamMemberDTO> expectedAnswer = new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);

        // Appel de la méthode de contrôleur et vérification de la réponse
        ResponseEntity<TeamMemberDTO> response = teamMemberController.setIndividualMarkById(1, 90);
        
        assertEquals(expectedAnswer, response);
    }
}
