package com.caerdydd.taf.models.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.opencsv.bean.CsvBindByPosition;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Integer id;
    @CsvBindByPosition(position = 0)
    private String firstname;
    @CsvBindByPosition(position = 1)
    private String lastname;
    @CsvBindByPosition(position = 2)
    private String login;
    @CsvBindByPosition(position = 3)
    private String password;
    @CsvBindByPosition(position = 4)
    private String email;
    @CsvBindByPosition(position = 5)
    private String speciality;
    
    @JsonManagedReference
    private List<RoleDTO> roles = new ArrayList<>();

    @JsonBackReference
    private TeamMemberDTO teamMember;
    
    // @JsonBackReference
    // private TeachingStaffDTO teachingStaff;

    public UserDTO() {
    }

    public UserDTO(String firstname, String lastname, String login, String password, String email, String speciality) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.speciality = speciality;
    }

    @Override
    public String toString() {
        return "UserDTO [email=" + email + ", firstname=" + firstname + ", lastname=" + lastname
                + ", login=" + login + ", password=" + password + ", speciality=" + speciality + "]";
    }

}
