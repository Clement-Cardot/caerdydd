package com.caerdydd.taf.models.entities.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caerdydd.taf.models.entities.project.TeamEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "team_member")
public class TeamMemberEntity implements Serializable {

    @Id
    private Integer idUser;
    private Integer individualMark;
    private Integer bonusPenalty;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_user")
    private UserEntity user;
    
    @ManyToOne
    @JoinColumn(name = "id_team")
    private TeamEntity team;

    public TeamMemberEntity() {
    }

    public TeamMemberEntity(UserEntity user, TeamEntity team) {
        this.user = user;
        this.team = team;
        this.idUser = user.getId();
    }

    @Override
    public String toString() {
        return "TeamMemberEntity [bonusPenalty=" + bonusPenalty + ", idUser=" + idUser + ", individualMark="
                + individualMark + ", team=" + team + ", user=" + user + "]";
    }

    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (o.getClass() != TeamMemberEntity.class) {
            return false;
        }
        TeamMemberEntity teamMemberEntity = (TeamMemberEntity) o;
        return this.getIdUser().equals(teamMemberEntity.getIdUser())
            && this.getUser().equals(teamMemberEntity.getUser())
            && this.getTeam().equals(teamMemberEntity.getTeam())
            && this.getIndividualMark().equals(teamMemberEntity.getIndividualMark())
            && this.getBonusPenalty().equals(teamMemberEntity.getBonusPenalty());
    }

    @Override
    public int hashCode() {
        return this.getIdUser();
    }

}
