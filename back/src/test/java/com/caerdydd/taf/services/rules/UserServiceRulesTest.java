package com.caerdydd.taf.services.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.caerdydd.taf.models.dto.RoleDTO;
import com.caerdydd.taf.models.dto.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;

@ExtendWith(MockitoExtension.class)
public class UserServiceRulesTest {

    @InjectMocks
    private UserServiceRules userServiceRules;

    @Mock
    SecurityConfig securityConfig;

    @ParameterizedTest
    @ValueSource(strings = {"STUDENT_ROLE", "TEACHING_STAFF_ROLE", "OPTION_LEADER_ROLE", "TEAM_MEMBER_ROLE", "PLANNING_ROLE"})
    public void checkUserRoleTest_UserHasRole(String role) {
        // Create User
        UserDTO user = new UserDTO();
        user.setRoles(new ArrayList<>());
        user.getRoles().add(new RoleDTO(1, role, user));

        // Call method to test
        try {
            userServiceRules.checkUserRole(user, role);
        } catch (Exception e) {
            fail("Exception thrown when it shouldn't have been : " + e.getMessage());
        }

        // If nothing throw : success
    }

    @ParameterizedTest
    @ValueSource(strings = {"STUDENT_ROLE", "TEACHING_STAFF_ROLE", "OPTION_LEADER_ROLE", "TEAM_MEMBER_ROLE", "PLANNING_ROLE"})
    public void checkUserRoleTest_UserHasNotRole(String role) {
        // Create User
        UserDTO user = new UserDTO();
        user.setRoles(new ArrayList<>());
        user.getRoles().add(new RoleDTO(1, "USELESS_ROLE", user));

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            userServiceRules.checkUserRole(user, role);
        });

        // Check exception
        switch (role) {
            case "STUDENT_ROLE":
                assertEquals(CustomRuntimeException.USER_IS_NOT_A_STUDENT, exception.getMessage());
                break;
            case "TEACHING_STAFF_ROLE":
                assertEquals(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF, exception.getMessage());
                break;
            case "OPTION_LEADER_ROLE":
                assertEquals(CustomRuntimeException.USER_IS_NOT_AN_OPTION_LEADER, exception.getMessage());
                break;
            case "TEAM_MEMBER_ROLE":
                assertEquals(CustomRuntimeException.USER_IS_NOT_A_TEAM_MEMBER, exception.getMessage());
                break;
            case "PLANNING_ROLE":
                assertEquals(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT, exception.getMessage());
                break;
            default:
                fail();
                break;
        }
        
    }

    @Test
    public void checkUserRoleTest_UnexpectedRole() {
        // Create User
        UserDTO user = new UserDTO();
        user.setRoles(new ArrayList<>());
        user.getRoles().add(new RoleDTO(1, "STUDENT_ROLE", user));

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            userServiceRules.checkUserRole(user, "UNEXPECTED_ROLE");
        });

        // Check exception
        assertEquals("Unexpected role value: UNEXPECTED_ROLE", exception.getMessage());

    }
    
    @Test
    public void checkCurrentUser_UserIsNotCurrentUser() throws CustomRuntimeException {
        // Create Users
        UserDTO user1 = new UserDTO();
        user1.setId(1);
        UserDTO user2 = new UserDTO();
        user2.setId(2);

        // Mock securityConfig.getCurrentUser()
        when(securityConfig.getCurrentUser()).thenReturn(user2);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            userServiceRules.checkCurrentUser(user1);
        });

        // Check exception
        assertEquals(CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER, exception.getMessage());

    }

    @Test
    public void checkCurrentUser_UserIsCurrentUser() throws CustomRuntimeException {
        // Create Users
        UserDTO user1 = new UserDTO();
        user1.setId(1);

        // Mock securityConfig.getCurrentUser()
        when(securityConfig.getCurrentUser()).thenReturn(user1);

        // Call method to test
        try {
            userServiceRules.checkCurrentUser(user1);
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
        
        // If nothing throw : success

    }
}