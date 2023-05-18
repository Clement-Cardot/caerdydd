package com.caerdydd.taf.models.dto.user;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamMemberDTO {

    private Integer idTeam;
    private Integer individualMark;
    private Integer bonusPenalty;

    @JsonManagedReference(value = "teamMember")
    private UserDTO user;
    
    @JsonBackReference(value = "team")
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
