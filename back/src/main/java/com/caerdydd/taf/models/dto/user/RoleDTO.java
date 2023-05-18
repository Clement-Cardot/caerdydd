package com.caerdydd.taf.models.dto.user;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {

    public static final String STUDENT_ROLE = "STUDENT_ROLE";
    public static final String TEAM_MEMBER_ROLE = "TEAM_MEMBER_ROLE";
    public static final String TEACHING_STAFF_ROLE = "TEACHING_STAFF_ROLE";
    public static final String OPTION_LEADER_ROLE = "OPTION_LEADER_ROLE";
    public static final String PLANNING_ROLE = "PLANNING_ROLE";
    
    private Integer idRole;
    private String role;

    @JsonBackReference(value = "roles")
    private UserDTO user;

    public RoleDTO() {
    }

    public RoleDTO(String role, UserDTO user) {
        this.role = role;
        this.user = user;
    }

    public RoleDTO(Integer idRole, String role, UserDTO user) {
        this.idRole = idRole;
        this.role = role;
        this.user = user;
    }

    @Override
    public String toString() {
        return "RoleDTO [idRole=" + idRole + ", role=" + role + ", user=" + user + "]";
    }
}
