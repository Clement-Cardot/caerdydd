package com.caerdydd.taf.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.entities.TeamMemberEntity;
import com.caerdydd.taf.repositories.TeamMemberRepository;

@Service
@Transactional
public class TeamMemberService {
    
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    public List<TeamMemberDTO> listAllTeamMembers() {
        return teamMemberRepository.findAll().stream()
        .map(user -> modelMapper.map(user, TeamMemberDTO.class))
        .collect(Collectors.toList()) ;
    }

    public TeamMemberDTO getTeamMemberById(Integer id) throws NoSuchElementException {
        Optional<TeamMemberEntity> teamMember = teamMemberRepository.findById(id);
        if (teamMember.isEmpty()) {
            throw new NoSuchElementException();
        }
        return  modelMapper.map(teamMember.get(), TeamMemberDTO.class);
    }

    public void saveTeamMember(TeamMemberDTO teamMember) {
        TeamMemberEntity teamMemberEntity = modelMapper.map(teamMember, TeamMemberEntity.class);

        teamMemberRepository.save(teamMemberEntity);
    }
}
