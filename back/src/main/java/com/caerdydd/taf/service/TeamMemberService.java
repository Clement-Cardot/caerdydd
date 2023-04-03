package com.caerdydd.taf.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.TeamMember;
import com.caerdydd.taf.repository.TeamMemberRepository;

@Service
@Transactional
public class TeamMemberService {
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    
    public List<TeamMember> listAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public TeamMember getTeamMemberById(Integer id) throws NotFoundException {
        Optional<TeamMember> teamMember = teamMemberRepository.findById(id);
        if (teamMember.isEmpty()) {
            throw new NotFoundException();
        }
        return teamMember.get();
    }

    public void saveTeamMember(TeamMember teamMember) {
        teamMemberRepository.save(teamMember);
    }
}
