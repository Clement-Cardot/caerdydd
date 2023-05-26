package com.caerdydd.taf.services.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.security.SecurityConfig;
import java.util.Objects;

@Component
public class UserServiceRules {

    @Autowired
    SecurityConfig securityConfig;
    
    public void checkUserRole(UserDTO user, String role) throws CustomRuntimeException{
        if(user.getRoles().stream().noneMatch(r -> r.getRole().equals(role))){
            switch (role) {
                case "STUDENT_ROLE":
                    throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_STUDENT);
                case "TEACHING_STAFF_ROLE":
                    throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEACHING_STAFF);
                case "OPTION_LEADER_ROLE":
                    throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AN_OPTION_LEADER);
                case "TEAM_MEMBER_ROLE":
                    throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_TEAM_MEMBER);
                case "PLANNING_ROLE":
                    throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT);
                case "JURY_MEMBER_ROLE":
                    throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_JURY_MEMBER);
                default:
                    throw new CustomRuntimeException("Unexpected role value: " + role);
            }
        }
    }

    public void checkCurrentUser(UserDTO user) throws CustomRuntimeException{
        if(!Objects.equals(user.getId(), securityConfig.getCurrentUser().getId())){
            throw new CustomRuntimeException(CustomRuntimeException.CURRENT_USER_IS_NOT_REQUEST_USER);
        }
    }

    public void checkCurrentUserRole(String role) throws CustomRuntimeException {
        checkUserRole(securityConfig.getCurrentUser(), role);
    }

    public UserDTO getCurrentUser() throws CustomRuntimeException {
        return securityConfig.getCurrentUser();
    }
}
