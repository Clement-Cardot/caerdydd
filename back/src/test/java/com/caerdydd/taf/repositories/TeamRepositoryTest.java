package com.caerdydd.taf.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.caerdydd.taf.models.entities.TeamEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;


    @Test
    public void testCreateReadDelete() {        
        TeamEntity team = new TeamEntity(1, "Team 1");

        teamRepository.save(team);

        Iterable<TeamEntity> teams = teamRepository.findAll();
        Assertions.assertThat(teams).extracting(TeamEntity::getName).containsOnly("Team 1");

        teamRepository.delete(team);
        Assertions.assertThat(teamRepository.findAll()).isEmpty();
    }

}
