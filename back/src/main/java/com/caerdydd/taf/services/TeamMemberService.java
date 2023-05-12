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

import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.entities.user.TeamMemberEntity;
import com.caerdydd.taf.repositories.TeamMemberRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.TeamMemberServiceRules;
import com.caerdydd.taf.services.rules.UserServiceRules;

@Service
@Transactional
public class TeamMemberService {
    private static final Logger logger = LogManager.getLogger(TeamMemberService.class);

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamMemberServiceRules teamMemberServiceRules;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    UserServiceRules userServiceRules;
    
    public List<TeamMemberDTO> listAllTeamMembers() throws CustomRuntimeException {
        try {
            return teamMemberRepository.findAll().stream()
            .map(user -> modelMapper.map(user, TeamMemberDTO.class))
            .collect(Collectors.toList()) ;
        } catch (Exception e) {
            logger.error("Error listing all team members", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public TeamMemberDTO getTeamMemberById(Integer id) throws CustomRuntimeException {
        Optional<TeamMemberEntity> optionalTeamMember;
        try{
            optionalTeamMember = teamMemberRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error getting team member by id", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        if (optionalTeamMember.isEmpty()) {
            logger.error("Team member not found");
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

        // Check if the current user is a option leader
        userServiceRules.checkCurrentUserRole("OPTION_LEADER_ROLE");

        // Check if the value of the bonus is correct.
        teamMemberServiceRules.checkTeamMemberBonusValue(bonusPenalty);

        TeamMemberDTO teamMember = getTeamMemberById(id);

        // Check if the total mark is possible
        teamMemberServiceRules.checkTeamMemberMarkAfterBonus(teamMember, bonusPenalty);

        teamMember.setBonusPenalty(bonusPenalty);
        return updateTeamMember(teamMember);
    }

    public TeamMemberDTO setIndividualMarkById(Integer id, Integer individualMark)throws CustomRuntimeException{
          // Check if the current user is a jury member 
          userServiceRules.checkCurrentUserRole("JURY_MEMBER_ROLE");

        // Check if the value of the bonus is correct.
        teamMemberServiceRules.checkTeamMemberIndividualMark(individualMark);

        TeamMemberDTO teamMember = getTeamMemberById(id);

        teamMember.setIndividualMark(individualMark);
        return updateTeamMember(teamMember);
    }

    public TeamMemberDTO updateTeamMember(TeamMemberDTO teamMember) throws CustomRuntimeException {
        TeamMemberEntity teamMemberEntity = modelMapper.map(teamMember, TeamMemberEntity.class);
        
        Optional<TeamMemberEntity> optionalUser = teamMemberRepository.findById(teamMemberEntity.getIdUser());
        if (optionalUser.isEmpty()) {
            logger.error("User not found");
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }

        TeamMemberEntity response = null;
        try {
            response = teamMemberRepository.save(teamMemberEntity);
        } catch (Exception e) {
            logger.error("Error updating team member", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, TeamMemberDTO.class);
    }
}
