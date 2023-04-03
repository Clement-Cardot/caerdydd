package com.caerdydd.taf.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.entities.TeamEntity;
import com.caerdydd.taf.repositories.TeamRepository;

@Service
@Transactional
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    
    public List<TeamEntity> listAllTeams() {
        return teamRepository.findAll();
    }

    public TeamEntity getTeamById(Integer id) throws NoSuchElementException {
        Optional<TeamEntity> team = teamRepository.findById(id);
        if (team.isEmpty()) {
            throw new NoSuchElementException();
        }
        return team.get();
    }

    public TeamEntity saveTeam(TeamEntity team) {
        return teamRepository.save(team);
    }

}
