package com.caerdydd.taf.models.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDTO {
    
    private Integer idRole;
    private String role;

    @JsonBackReference
    private UserDTO user;

    public RoleDTO() {
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
