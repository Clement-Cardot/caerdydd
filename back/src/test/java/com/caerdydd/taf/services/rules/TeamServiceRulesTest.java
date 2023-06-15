package com.caerdydd.taf.services.rules;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;

@ExtendWith(MockitoExtension.class)
public class TeamServiceRulesTest {

    @InjectMocks
    private TeamServiceRules teamServiceRules;

    @Mock
    SecurityConfig securityConfig;

    @Test
    void checkTeamIsFull_TeamIsFull() {
        // Create a full team
        TeamDTO team = new TeamDTO();
        team.setTeamMembers(new ArrayList<>());
        team.getTeamMembers().add(null);
        team.getTeamMembers().add(null);
        team.getTeamMembers().add(null);
        team.getTeamMembers().add(null);
        team.getTeamMembers().add(null);
        team.getTeamMembers().add(null);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamServiceRules.checkTeamIsFull(team);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.TEAM_IS_FULL, exception.getMessage());
    }

    @Test
    void checkTeamIsFull_TeamIsEmpty() {
        // Create aa empty team
        TeamDTO team = new TeamDTO();
        team.setTeamMembers(new ArrayList<>());

        // Call method to test
        try {
            teamServiceRules.checkTeamIsFull(team);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // If nothing throw : success
    }
    
    @ParameterizedTest
    @ValueSource(strings = {"CSS", "LD"})
    void checkSpecialityRatio_TeamMaxSpe(String speciality) throws CustomRuntimeException {
        // Mock securityConfig.getCurrentUser()
        UserDTO mockedUser = new UserDTO();
        mockedUser.setSpeciality(speciality);
        when(securityConfig.getCurrentUser()).thenReturn(mockedUser);

        // Create a team
        TeamDTO team = new TeamDTO();
        team.setTeamMembers(new ArrayList<>());

        int nbUser = (speciality.equals("CSS")) ? 2 : 4;
        for (int i = 0; i < nbUser ; i++) {
            UserDTO user = new UserDTO();
            user.setSpeciality(speciality);
            team.getTeamMembers().add(new TeamMemberDTO(user , team));
        }

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamServiceRules.checkSpecialityRatio(team);
        });

        // Verify the result
        if (speciality.equals("LD")) {
            assertEquals(CustomRuntimeException.TEAM_ALREADY_HAS_4_LD, exception.getMessage());
        } else if (speciality.equals("CSS")) {
        assertEquals(CustomRuntimeException.TEAM_ALREADY_HAS_2_CSS, exception.getMessage());
        }
        
    }

    @ParameterizedTest
    @ValueSource(strings = {"CSS", "LD"})
    void checkSpecialityRatio_TeamMaxSpe_ButUserIsOtherSpe(String speciality) throws CustomRuntimeException {
        // Mock securityConfig.getCurrentUser()
        UserDTO mockedUser = new UserDTO();
        mockedUser.setSpeciality((speciality.equals("CSS")) ? "LD" : "CSS");
        when(securityConfig.getCurrentUser()).thenReturn(mockedUser);

        // Create a team
        TeamDTO team = new TeamDTO();
        team.setTeamMembers(new ArrayList<>());

        int nbUser = (speciality.equals("CSS")) ? 2 : 4;
        for (int i = 0; i < nbUser ; i++) {
            UserDTO user = new UserDTO();
            user.setSpeciality(speciality);
            team.getTeamMembers().add(new TeamMemberDTO(user , team));
        }

        // Call method to test
        try {
            teamServiceRules.checkSpecialityRatio(team);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // If nothing throw : success
        
    }


    @ParameterizedTest
    @ValueSource(strings = {"CSS", "LD"})
    void checkSpecialityRatio_TeamisEmpty(String speciality) throws CustomRuntimeException {
        // Create an empty team
        TeamDTO team = new TeamDTO();
        team.setTeamMembers(new ArrayList<>());

        // Call method to test
        try {
            teamServiceRules.checkSpecialityRatio(team);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // If nothing throw : success
    }

    @ParameterizedTest
    @ValueSource(strings = {"CSS", "LD"})
    void checkSpecialityRatio_TeamHasSlot(String speciality) throws CustomRuntimeException {
        // Create a team
        TeamDTO team = new TeamDTO();
        team.setTeamMembers(new ArrayList<>());

        UserDTO user1 = new UserDTO();
        user1.setSpeciality(speciality);

        team.getTeamMembers().add(new TeamMemberDTO(user1 , team));

        // Call method to test
        try {
            teamServiceRules.checkSpecialityRatio(team);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }

        // If nothing throw : success
    }

    @Test
    void testIsValidLink_ValidLink() {
        // Prepare Input
        String link = "http://example.com";

        // Call method to test
        assertDoesNotThrow(() -> teamServiceRules.isValidLink(link));
    }


    @Test
    void testIsValidLink_InvalidLink() {
        // Prepare Input
        String link = "Invalid Link";

        // Call method to test and Verify the result
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamServiceRules.isValidLink(link);
        });
        assertEquals(CustomRuntimeException.INVALID_LINK, exception.getMessage());
    }

    @Test
    void testCheckIfUserIsMemberOfTeam_UserIsMember() throws CustomRuntimeException {
        // Prepare Input
        UserDTO currentUser = new UserDTO();
        currentUser.setId(1);
        when(securityConfig.getCurrentUser()).thenReturn(currentUser);
        TeamMemberDTO teamMember = new TeamMemberDTO();
        teamMember.setUser(currentUser);
        List<TeamMemberDTO> teamMembers = new ArrayList<>();
        teamMembers.add(teamMember);
        TeamDTO team = new TeamDTO();
        team.setTeamMembers(teamMembers);

        // Call method to test
        teamServiceRules.checkIfUserIsMemberOfTeam(team);
    }

    @Test
    void testCheckIfUserIsMemberOfTeam_UserIsNotMember() throws CustomRuntimeException {
        // Prepare Input
        UserDTO currentUser = new UserDTO();
        currentUser.setId(1);
        when(securityConfig.getCurrentUser()).thenReturn(currentUser);
        UserDTO anotherUser = new UserDTO();
        anotherUser.setId(2);
        TeamMemberDTO teamMember = new TeamMemberDTO();
        teamMember.setUser(anotherUser);
        List<TeamMemberDTO> teamMembers = new ArrayList<>();
        teamMembers.add(teamMember);
        TeamDTO team = new TeamDTO();
        team.setTeamMembers(teamMembers);

        // Call method to test and Verify the result
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamServiceRules.checkIfUserIsMemberOfTeam(team);
        });
        assertEquals(CustomRuntimeException.USER_NOT_IN_ASSOCIATED_TEAM, exception.getMessage());
    }


}
