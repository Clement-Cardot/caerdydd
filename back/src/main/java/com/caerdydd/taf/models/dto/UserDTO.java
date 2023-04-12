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
    private List<RoleDTO> roleEntities;

    @JsonManagedReference
    private TeachingStaffDTO teachingStaff;

    @JsonBackReference
    private TeamMemberDTO teamMember;
    
}
