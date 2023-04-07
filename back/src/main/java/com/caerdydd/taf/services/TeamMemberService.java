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
import com.caerdydd.taf.security.CustomRuntimeException;

@Service
@Transactional
public class TeamMemberService {
    
    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    public List<TeamMemberDTO> listAllTeamMembers() throws CustomRuntimeException {
        try {
            return teamMemberRepository.findAll().stream()
            .map(user -> modelMapper.map(user, TeamMemberDTO.class))
            .collect(Collectors.toList()) ;
        } catch (NoSuchElementException e) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND);
        }
    }

    public TeamMemberDTO getTeamMemberById(Integer id) throws CustomRuntimeException {
        Optional<TeamMemberEntity> teamMember = teamMemberRepository.findById(id);
        if (teamMember.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND);
        }
        return  modelMapper.map(teamMember.get(), TeamMemberDTO.class);
    }

    public void saveTeamMember(TeamMemberDTO teamMember) {
        TeamMemberEntity teamMemberEntity = modelMapper.map(teamMember, TeamMemberEntity.class);

        teamMemberRepository.save(teamMemberEntity);
    }
}
