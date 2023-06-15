package com.caerdydd.taf.services.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class TeamMemberServiceRulesTest {

    @InjectMocks
    private TeamMemberServiceRules teamMemberServiceRules;

    @Test
    void checkTeamMemberBonusValue_IncorrectValue(){
        int bonusToAdd = 5;
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamMemberServiceRules.checkTeamMemberBonusValue(bonusToAdd);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.TEAM_MEMBER_INCORRECT_BONUS_PENALTY, exception.getMessage());
    }

    @Test
    void checkTeamMemberTotalMark_ImpossibleMark(){
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

    @Test
    void testCheckTeamMemberIndividualMark_GreaterThan10() {
        // Prepare Input
        int individualMark = 11;

        // Call method to test and Verify the result
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamMemberServiceRules.checkTeamMemberIndividualMark(individualMark);
        });
        assertEquals(CustomRuntimeException.TEAM_MEMBER_INCORRECT_INDIVIDUAL_MARK, exception.getMessage());
    }

@Test
void testCheckTeamMemberIndividualMark_LessThan0() {
    // Prepare Input
    int individualMark = -1;

    // Call method to test and Verify the result
    CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
        teamMemberServiceRules.checkTeamMemberIndividualMark(individualMark);
    });
    assertEquals(CustomRuntimeException.TEAM_MEMBER_INCORRECT_INDIVIDUAL_MARK, exception.getMessage());
}

@Test
void testCheckTeamMemberIndividualMark_Between0And10() throws CustomRuntimeException {
    // Prepare Input
    int individualMark = 5;

    // Call method to test
    teamMemberServiceRules.checkTeamMemberIndividualMark(individualMark);
}

@Test
void testCheckTeamMemberMarkAfterBonus_ValidTotalMark() throws CustomRuntimeException {
    // Prepare Input
    TeamMemberDTO teamMember = new TeamMemberDTO();
    teamMember.setIndividualMark(5);
    TeamDTO team = new TeamDTO();
    team.setTeamValidationMark(5);
    team.setTeamWorkMark(5);
    teamMember.setTeam(team);
    int bonusToAdd = 2;

    // Call method to test
    teamMemberServiceRules.checkTeamMemberMarkAfterBonus(teamMember, bonusToAdd);
}

@Test
void testCheckTeamMemberMarkAfterBonus_InvalidTotalMark() {
    // Prepare Input
    TeamMemberDTO teamMember = new TeamMemberDTO();
    teamMember.setIndividualMark(10);
    TeamDTO team = new TeamDTO();
    team.setTeamValidationMark(10);
    team.setTeamWorkMark(10);
    teamMember.setTeam(team);
    int bonusToAdd = 2;

    // Call method to test and Verify the result
    CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
        teamMemberServiceRules.checkTeamMemberMarkAfterBonus(teamMember, bonusToAdd);
    });
    assertEquals(CustomRuntimeException.TEAM_MEMBER_IMPOSSIBLE_TOTAL_MARK, exception.getMessage());
}

@Test
void testCheckTeamMemberBonusValue_ValidBonus() throws CustomRuntimeException {
    // Prepare Input
    int bonusToAdd = 3;

    // Call method to test
    teamMemberServiceRules.checkTeamMemberBonusValue(bonusToAdd);
}

@Test
void testCheckTeamMemberBonusValue_InvalidBonus() {
    // Prepare Input
    int bonusToAdd = 5;

    // Call method to test and Verify the result
    CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
        teamMemberServiceRules.checkTeamMemberBonusValue(bonusToAdd);
    });
    assertEquals(CustomRuntimeException.TEAM_MEMBER_INCORRECT_BONUS_PENALTY, exception.getMessage());
}




}