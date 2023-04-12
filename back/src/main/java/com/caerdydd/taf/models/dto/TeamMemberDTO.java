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

    @JsonManagedReference(value="teamMember")
    private UserDTO user;

    private Integer idTeam;
    private Integer individualMark;
    private Integer bonusPenalty;
    
    @JsonBackReference
    private TeamDTO team;

    public TeamMemberDTO() {
    }

    public TeamMemberDTO(UserDTO user, TeamDTO team) {
        this.user = user;
        this.team = team;
        this.idTeam = team.getIdTeam();
    }

    @Override
    public String toString() {
        return "TeamMemberDTO [user=" + user.getId() + ", idTeam=" + idTeam + ", individualMark=" + individualMark
                + ", bonusPenalty=" + bonusPenalty + ", team=" + team.getIdTeam() + "]";
    }

}
