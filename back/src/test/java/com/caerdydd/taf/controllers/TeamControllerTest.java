package com.caerdydd.taf.controllers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.services.TeamService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TeamController.class)
public class TeamControllerTest {

    @MockBean
    TeamService teamService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testList() throws Exception {
        TeamEntity team = new TeamEntity(1, "Team 1");
        List<TeamEntity> teams = new ArrayList<TeamEntity>();
        teams.add(team);
        
        Mockito.when(teamService.listAllTeams()).thenReturn(teams);

        // TO DEBUG
        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("Team 1")));
    }
    
}
