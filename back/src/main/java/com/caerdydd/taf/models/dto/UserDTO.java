package com.caerdydd.taf.models.dto;

import java.util.List;
import java.util.stream.Collectors;

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
    @JsonIgnore
    private String password;
    private String email;
    private String role;

    @JsonBackReference
    private TeamMemberDTO teamMember;
    
    // @JsonBackReference
    // private TeachingStaffDTO teachingStaff;

}
