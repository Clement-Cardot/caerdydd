package com.caerdydd.taf.models.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    @OneToMany
    @JoinColumn(name = "id_team")
    List<TeamMemberEntity> teamMembers;

    @OneToOne
    @JoinColumn(name = "id_project_dev")
    private ProjectEntity projectDev;

    @OneToOne
    @JoinColumn(name = "id_project_validation")
    private ProjectEntity projectValidation;

    public TeamEntity() {
    }

    public TeamEntity(Integer idTeam, String name) {
        this.idTeam = idTeam;
        this.name = name;
    }

    public TeamEntity(Integer idTeam, String name, Integer teamWorkMark, Integer teamValidationMark, String testBookLink,
            String filePathScopeStatement, String filePathFinalScopeStatement, String filePathScopeStatementAnalysis,
            String filePathReport, ProjectEntity projectDev, ProjectEntity projectValidation) {

        this.idTeam = idTeam;
        this.name = name;
        this.teamWorkMark = teamWorkMark;
        this.teamValidationMark = teamValidationMark;
        this.testBookLink = testBookLink;
        this.filePathScopeStatement = filePathScopeStatement;
        this.filePathFinalScopeStatement = filePathFinalScopeStatement;
        this.filePathScopeStatementAnalysis = filePathScopeStatementAnalysis;
        this.filePathReport = filePathReport;
        this.projectDev=projectDev;
        this.projectValidation=projectValidation;
    }

    @Override
    public String toString() {
        return "TeamEntity [idTeam=" + idTeam + ", name=" + name + ", teamWorkMark=" + teamWorkMark
                + ", teamValidationMark=" + teamValidationMark + ", testBookLink=" + testBookLink
                + ", filePathScopeStatement=" + filePathScopeStatement + ", filePathFinalScopeStatement="
                + filePathFinalScopeStatement + ", filePathScopeStatementAnalysis=" + filePathScopeStatementAnalysis
                + ", filePathReport=" + filePathReport + ", idProjectDev=" + projectDev.getIdProject() + ", idProjectValidation="
                + projectValidation.getIdProject() + ", teamMembers=" + teamMembers + "]";
    }

}