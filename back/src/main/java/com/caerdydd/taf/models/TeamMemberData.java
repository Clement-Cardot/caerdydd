package com.caerdydd.taf.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "team_member")
public class TeamMemberData extends UserAdditionalData{

    private Integer idUser;
    private String speciality;
    private Integer individualMark;
    private Integer bonusPenalty;
    private Integer idTeam;

    public TeamMemberData() {
    }

    public TeamMemberData(Integer idUser, String speciality, Integer individualMark, Integer bonusPenalty,
            Integer idTeam) {
        this.idUser = idUser;
        this.speciality = speciality;
        this.individualMark = individualMark;
        this.bonusPenalty = bonusPenalty;
        this.idTeam = idTeam;
    }

    // Getters and Setters

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Integer getIndividualMark() {
        return individualMark;
    }

    public void setIndividualMark(Integer individualMark) {
        this.individualMark = individualMark;
    }

    public Integer getBonusPenalty() {
        return bonusPenalty;
    }

    public void setBonusPenalty(Integer bonusPenalty) {
        this.bonusPenalty = bonusPenalty;
    }

    public Integer getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Integer idTeam) {
        this.idTeam = idTeam;
    }

    // Methods
    public String toString() {
        return "TeamMemberData [idUser=" + idUser + ", speciality=" + speciality + ", individualMark=" + individualMark
                + ", bonusPenalty=" + bonusPenalty + ", idTeam=" + idTeam + "]";
    }
}
