package com.caerdydd.taf.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.entities.TeamMemberEntity;
import com.caerdydd.taf.repositories.TeamMemberRepository;

@Service
@Transactional
public class TeamMemberService {
    
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    
    public List<TeamMemberEntity> listAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public TeamMemberEntity getTeamMemberById(Integer id) throws NoSuchElementException {
        Optional<TeamMemberEntity> teamMember = teamMemberRepository.findById(id);
        if (teamMember.isEmpty()) {
            throw new NoSuchElementException();
        }
        return teamMember.get();
    }

    public void saveTeamMember(TeamMemberEntity teamMember) {
        teamMemberRepository.save(teamMember);
    }
}
