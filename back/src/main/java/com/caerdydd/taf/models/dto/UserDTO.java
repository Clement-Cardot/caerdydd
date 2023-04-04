package com.caerdydd.taf.models.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Integer id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private String speciality;
    private String role;

    @JsonBackReference
    private TeamMemberDTO teamMember;
    
    // @JsonBackReference
    // private TeachingStaffDTO teachingStaff;

}
