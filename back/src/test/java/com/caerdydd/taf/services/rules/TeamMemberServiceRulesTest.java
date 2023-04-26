package com.caerdydd.taf.services.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.caerdydd.taf.models.dto.TeamDTO;
import com.caerdydd.taf.models.dto.TeamMemberDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class TeamMemberServiceRulesTest {

    @InjectMocks
    private TeamMemberServiceRules teamMemberServiceRules;

    @Test
    public void checkTeamMemberBonusValue_IncorrectValue(){
        int bonusToAdd = 5;
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamMemberServiceRules.checkTeamMemberBonusValue(bonusToAdd);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.TEAM_MEMBER_INCORRECT_BONUS_PENALTY, exception.getMessage());
    }

    @Test
    public void checkTeamMemberTotalMark_ImpossibleMark(){
        UserDTO user = new UserDTO();
        TeamDTO team = new TeamDTO();
        team.setTeamValidationMark(5);
        team.setTeamWorkMark(5);

        TeamMemberDTO teamMember = new TeamMemberDTO(user, team);
        teamMember.setIndividualMark(18);

        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamMemberServiceRules.checkTeamMemberMarkAfterBonus(teamMember, 3);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.TEAM_MEMBER_IMPOSSIBLE_TOTAL_MARK, exception.getMessage());
    }
}