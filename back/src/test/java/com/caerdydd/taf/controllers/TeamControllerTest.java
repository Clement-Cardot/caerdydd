package com.caerdydd.taf.controllers;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.services.TeamService;

public class TeamControllerTest {

    @InjectMocks
    private TeamController teamController;

    @Mock
    private TeamService teamService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    // @Test
    // void testGetAllTeams() {
    //     List<TeamDTO> teams = new ArrayList<>();
    //     teams.add(new TeamDTO(1, "Team 1"));
    //     teams.add(new TeamDTO(2, "Team 2"));

    //     List<TeamDTO> teamDTOs = new ArrayList<>();
    //     teamDTOs.add(new TeamDTO(1, "Team 1"));
    //     teamDTOs.add(new TeamDTO(2, "Team 2"));

    //     when(teamService.listAllTeams()).thenReturn(teams);
    //     when(modelMapper.map(teams.get(0), TeamDTO.class)).thenReturn(teamDTOs.get(0));
    //     when(modelMapper.map(teams.get(1), TeamDTO.class)).thenReturn(teamDTOs.get(1));

    //     ResponseEntity<List<TeamDTO>> response = teamController.getAllTeams();

    //     Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    //     assert response.getBody().size() == 2;
    //     assert response.getBody().get(0).getIdTeam() == 1;
    //     assert response.getBody().get(0).getName().equals("Team 1");
    //     assert response.getBody().get(1).getIdTeam() == 2;
    //     assert response.getBody().get(1).getName().equals("Team 2");
    // }

    // @Test
    // void testGetTeamById() {
    //     TeamDTO team = new TeamDTO(1, "Team 1");

    //     when(teamService.getTeamById(1)).thenReturn(team);
    //     when(modelMapper.map(team, TeamDTO.class)).thenReturn(team);

    //     ResponseEntity<TeamDTO> response = teamController.getTeamById(1);

    //     assert response.getStatusCode() == HttpStatus.OK;
    //     assert response.getBody().getIdTeam() == 1;
    //     assert response.getBody().getName().equals("Team 1");
    // }

    // @Test
    // void testGetTeamByIdNotFound() {
    //     when(teamService.getTeamById(1)).thenThrow(new NoSuchElementException());

    //     ResponseEntity<TeamDTO> response = teamController.getTeamById(1);

    //     assert response.getStatusCode() == HttpStatus.NOT_FOUND;
    // }

}