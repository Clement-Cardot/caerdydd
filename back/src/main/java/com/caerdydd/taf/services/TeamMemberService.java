package com.caerdydd.taf.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.models.entities.TeamMemberEntity;
import com.caerdydd.taf.repositories.TeamMemberRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;

@Service
@Transactional
public class TeamMemberService {
    private static final Logger logger = LogManager.getLogger(TeamMemberService.class);

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    SecurityConfig securityConfig;
    
    public List<TeamMemberDTO> listAllTeamMembers() throws CustomRuntimeException {
        try {
            return teamMemberRepository.findAll().stream()
            .map(user -> modelMapper.map(user, TeamMemberDTO.class))
            .collect(Collectors.toList()) ;
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public TeamMemberDTO getTeamMemberById(Integer id) throws CustomRuntimeException {
        Optional<TeamMemberEntity> optionalTeamMember;
        try{
            optionalTeamMember = teamMemberRepository.findById(id);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        if (optionalTeamMember.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND);
        }
        return  modelMapper.map(optionalTeamMember.get(), TeamMemberDTO.class);
    }

    public TeamMemberDTO saveTeamMember(TeamMemberDTO teamMember) {
        TeamMemberEntity teamMemberEntity = modelMapper.map(teamMember, TeamMemberEntity.class);

        TeamMemberEntity response = teamMemberRepository.save(teamMemberEntity);

        return modelMapper.map(response, TeamMemberDTO.class);
    }

    public TeamMemberDTO setBonusPenaltyById(Integer id, Integer bonusPenalty) throws CustomRuntimeException {
        if(securityConfig.getCurrentUser() == null) {
            logger.warn("ILLEGAL API USE: Current user is null");
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }
        
        if(securityConfig.getCurrentUser().getRoles().stream().noneMatch(role -> role.getRole().equals(RoleDTO.OPTION_LEADER_ROLE))){
            logger.warn("ILLEGAL API USE : Current user is not a option leader");
            throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AN_OPTION_LEADER);
        }

        TeamMemberDTO teamMember = getTeamMemberById(id);
        teamMember.setBonusPenalty(bonusPenalty);
        return updateTeamMember(teamMember);
    }

    public TeamMemberDTO updateTeamMember(TeamMemberDTO teamMember) throws CustomRuntimeException {
        TeamMemberEntity teamMemberEntity = modelMapper.map(teamMember, TeamMemberEntity.class);
        
        Optional<TeamMemberEntity> optionalUser = teamMemberRepository.findById(teamMemberEntity.getIdUser());
        if (optionalUser.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }

        TeamMemberEntity response = null;
        try {
            response = teamMemberRepository.save(teamMemberEntity);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, TeamMemberDTO.class);
    }
}
