package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caerdydd.taf.models.entities.TeamEntity;

@SpringBootTest
public class TeamServiceTest {

    @Autowired
    private TeamService teamService;


    @Test
    public void testListAllUser() throws Exception {        
        List<TeamEntity> teams = teamService.listAllTeams();

        assertEquals(8, teams.size());
        assertEquals("Equipe 1", teams.get(0).getName());
        assertEquals("Equipe 2", teams.get(1).getName());
        assertEquals("Equipe 3", teams.get(2).getName());
    }


    @Test
    public void testGetTeamById_ExistingId() throws Exception {
        TeamEntity team = teamService.getTeamById(3);

        assertEquals(3, team.getIdTeam());
    }

}
