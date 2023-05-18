package com.caerdydd.taf.services.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;

@Component
public class TeamServiceRules {

    @Autowired
    SecurityConfig securityConfig;
    
    public void checkTeamIsFull(TeamDTO team) throws CustomRuntimeException {
        if (team.getTeamMembers().size() == 6) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_IS_FULL);
        }
    }

    // check if the speciality ratio is respected (2CSS/4LD)
    public void checkSpecialityRatio(TeamDTO team) throws CustomRuntimeException{
        if((team.getTeamMembers().stream().filter(teamMember -> teamMember.getUser().getSpeciality().equals("CSS")).count() == 2) 
                && (securityConfig.getCurrentUser().getSpeciality().equals("CSS"))){
                throw new CustomRuntimeException(CustomRuntimeException.TEAM_ALREADY_HAS_2_CSS);
        }
        if((team.getTeamMembers().stream().filter(teamMember -> teamMember.getUser().getSpeciality().equals("LD")).count() == 4) 
                && (securityConfig.getCurrentUser().getSpeciality().equals("LD"))){
                throw new CustomRuntimeException(CustomRuntimeException.TEAM_ALREADY_HAS_4_LD);
        }
    }
    public static void checkTeamWorkMark( int teamWorkMark) throws CustomRuntimeException{
        if (teamWorkMark > 5  || teamWorkMark < 0) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_INCORRECT_TEAM_MARK);
        }
    }

    public static void checkTeamValidationMark( int teamValidationMark) throws CustomRuntimeException{
        if (teamValidationMark > 5  || teamValidationMark < 0) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_INCORRECT_TEAM_MARK);
        }
    }
}