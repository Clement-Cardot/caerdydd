package com.caerdydd.taf.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.Team;
import com.caerdydd.taf.repository.TeamRepository;

@Service
@Transactional
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    
    public List<Team> listAllTeams() {
        return teamRepository.getAllTeamsNamesAndIDs();
    }

}
