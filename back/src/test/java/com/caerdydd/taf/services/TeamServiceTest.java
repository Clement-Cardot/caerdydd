package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.TeamRepository;

class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // void testListAllTeams() {
    //     List<TeamEntity> teams = new ArrayList<TeamEntity>();
    //     teams.add(new TeamEntity(1, "Team A"));
    //     teams.add(new TeamEntity(2, "Team B"));
    //     when(teamRepository.findAll()).thenReturn(teams);

    //     List<TeamDTO> result = teamService.listAllTeams();

    //     verify(teamRepository, times(1)).findAll();
    //     assertEquals(2, result.size());
    // }

    // @Test
    // void testGetTeamById() {
    //     TeamEntity team = new TeamEntity(1, "Team A");
    //     Optional<TeamEntity> optionalTeam = Optional.of(team);
    //     when(teamRepository.findById(1)).thenReturn(optionalTeam);

    //     TeamDTO result = teamService.getTeamById(1);

    //     verify(teamRepository, times(1)).findById(1);
    //     assertEquals(team, result);
    // }

    // @Test
    // void testGetTeamByIdWithNoSuchElementException() {
    //     Optional<TeamEntity> optionalTeam = Optional.empty();
    //     when(teamRepository.findById(anyInt())).thenReturn(optionalTeam);

    //     assertThrows(NoSuchElementException.class, () -> {
    //         teamService.getTeamById(1);
    //     });
    // }

    // @Test
    // void testSaveTeam() {
    //     TeamDTO team = new TeamDTO(1, "Team A");

    //     teamService.saveTeam(team);

    //     verify(teamRepository, times(1)).save(any(TeamEntity.class));
    // }
}
