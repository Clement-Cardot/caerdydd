package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.TeamRepository;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @InjectMocks
    TeamService teamService;

    @Mock
    TeamRepository teamRepository;


    @Test
    public void testFindAllTeams() {
        List<TeamEntity> teams = new ArrayList<TeamEntity>();
        TeamEntity team1 = new TeamEntity(1, "Team 1");
        TeamEntity team2 = new TeamEntity(2, "Team 2");
        TeamEntity team3 = new TeamEntity(3, "Team 3");

        teams.add(team1);
        teams.add(team2);
        teams.add(team3);

        when(teamRepository.findAll()).thenReturn(teams);

        // test
        List<TeamEntity> result = teamService.listAllTeams();

        assertEquals(3, result.size());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    public void testCreateOrSaveTeam() {
        TeamEntity team = new TeamEntity(1, "Team 1");

        teamService.saveTeam(team);

        verify(teamRepository, times(1)).save(team);
    }

}
