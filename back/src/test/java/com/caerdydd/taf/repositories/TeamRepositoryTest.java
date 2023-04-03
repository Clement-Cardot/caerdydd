package com.caerdydd.taf.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.caerdydd.taf.models.entities.TeamEntity;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;


    @Test
    public void listAllUserTest() throws Exception {        
        List<TeamEntity> teams = teamRepository.findAll();

        assertEquals(8, teams.size());
        assertEquals("Equipe 1", teams.get(0).getName());
        assertEquals("Equipe 2", teams.get(1).getName());
        assertEquals("Equipe 3", teams.get(2).getName());
    }


    @Test
    public void findByIdTest() throws Exception {
        Optional<TeamEntity> team3 = teamRepository.findById(3);
        
        assertEquals("Equipe 3", team3.get().getName());
    }

}
