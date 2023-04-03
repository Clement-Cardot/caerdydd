package com.caerdydd.taf.models.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamMemberDTO {

    @JsonManagedReference
    private UserDTO user;

    private Integer idTeam;
    private String speciality;
    private Integer individualMark;
    private Integer bonusPenalty;
    
    @JsonBackReference
    private TeamDTO team;

}
