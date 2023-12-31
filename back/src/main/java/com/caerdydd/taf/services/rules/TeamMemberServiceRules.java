package com.caerdydd.taf.services.rules;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@Component
public class TeamMemberServiceRules {
    
    public void checkTeamMemberMarkAfterBonus(TeamMemberDTO teamMember, int bonusToAdd) throws CustomRuntimeException {
        int teamMemberFinalMark =   teamMember.getIndividualMark() 
                                    + teamMember.getTeam().getTeamValidationMark() 
                                    + teamMember.getTeam().getTeamWorkMark();

        if (bonusToAdd + teamMemberFinalMark < 0 || bonusToAdd + teamMemberFinalMark > 20) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_IMPOSSIBLE_TOTAL_MARK);
        }
    }

    // May be remove it, not sure about this rule.
    public void checkTeamMemberBonusValue(int bonusToAdd) throws CustomRuntimeException {
        if (bonusToAdd > 4 || bonusToAdd < -4) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_INCORRECT_BONUS_PENALTY);
        }
    }

    public void checkTeamMemberIndividualMark( int individualMark) throws CustomRuntimeException{
        if (individualMark > 10 || individualMark < 0) {
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_MEMBER_INCORRECT_INDIVIDUAL_MARK);
        }
    }
}