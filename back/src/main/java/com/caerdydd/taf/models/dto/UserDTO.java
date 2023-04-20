package com.caerdydd.taf.models.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Integer id;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private String email;
    private String speciality;
    
    @JsonManagedReference
    private List<RoleDTO> roles;

    @JsonBackReference
    private TeamMemberDTO teamMember;
    
    // @JsonBackReference
    // private TeachingStaffDTO teachingStaff;

    public UserDTO() {
    }

    public UserDTO(Integer id, String firstname, String lastname, String login, String password, String email,
            String speciality) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "UserDTO [email=" + email + ", firstname=" + firstname + ", id=" + id + ", lastname=" + lastname
                + ", login=" + login + ", password=" + password + ", speciality=" + speciality + "]";
    }

}
