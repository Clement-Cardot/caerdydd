package com.caerdydd.taf.models.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    private Integer idTeam;
    private String name;
    private Integer teamWorkMark;
    private Integer teamValidationMark;
    private String testBookLink;
    private String filePathScopeStatement;
    private String filePathFinalScopeStatement;
    private String filePathScopeStatementAnalysis;
    private String filePathReport;
    private Integer idProjectDev;
    private Integer idProjectValidation;

    @OneToMany
    @JoinColumn(name = "id_team")
    List<TeamMemberEntity> teamMembers;

    // @OneToOne
    // private ProjectEntity projectDev;

    // @OneToOne
    // private ProjectEntity projectValidation;

    public TeamEntity() {
    }

    public TeamEntity(Integer idTeam, String name) {
        this.idTeam = idTeam;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TeamEntity [idTeam=" + idTeam + ", name=" + name + ", teamWorkMark=" + teamWorkMark
                + ", teamValidationMark=" + teamValidationMark + ", testBookLink=" + testBookLink
                + ", filePathScopeStatement=" + filePathScopeStatement + ", filePathFinalScopeStatement="
                + filePathFinalScopeStatement + ", filePathScopeStatementAnalysis=" + filePathScopeStatementAnalysis
                + ", filePathReport=" + filePathReport + ", idProjectDev=" + idProjectDev + ", idProjectValidation="
                + idProjectValidation + ", teamMembers=" + teamMembers + "]";
    }

}