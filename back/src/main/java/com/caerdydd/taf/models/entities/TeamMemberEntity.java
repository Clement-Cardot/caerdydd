package com.caerdydd.taf.models.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "team_member")
public class TeamMemberEntity {

    @Id
    private Integer idUser;
    private String speciality;
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

    public TeamMemberEntity(UserEntity user, String speciality, Integer individualMark, Integer bonusPenalty, TeamEntity team) {
        this.user = user;
        this.speciality = speciality;
        this.individualMark = individualMark;
        this.bonusPenalty = bonusPenalty;
        this.team = team;
    }

}
