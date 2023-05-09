package com.caerdydd.taf.models.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class TeamEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private List<TeamMemberEntity> teamMembers;

    @OneToOne
    @JoinColumn(name = "id_project_dev")
    private ProjectEntity projectDev;

    @OneToOne
    @JoinColumn(name = "id_project_validation")
    private ProjectEntity projectValidation;

    public TeamEntity() {
    }

    public TeamEntity(Integer idTeam, String name, ProjectEntity projectDev, ProjectEntity projectValidation) {
        this.idTeam = idTeam;
        this.name = name;
        this.projectDev = projectDev;
        this.projectValidation = projectValidation;
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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TeamEntity)) {
            return false;
        }
        TeamEntity teamEntity = (TeamEntity) o;
        return idTeam == teamEntity.idTeam && name.equals(teamEntity.name) && teamWorkMark == teamEntity.teamWorkMark
                && teamValidationMark == teamEntity.teamValidationMark && testBookLink.equals(teamEntity.testBookLink)
                && filePathScopeStatement.equals(teamEntity.filePathScopeStatement)
                && filePathFinalScopeStatement.equals(teamEntity.filePathFinalScopeStatement)
                && filePathScopeStatementAnalysis.equals(teamEntity.filePathScopeStatementAnalysis)
                && filePathReport.equals(teamEntity.filePathReport) && projectDev.equals(teamEntity.projectDev)
                && projectValidation.equals(teamEntity.projectValidation)
                && teamMembers.equals(teamEntity.teamMembers);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(idTeam, name, teamWorkMark, teamValidationMark, testBookLink,
                filePathScopeStatement, filePathFinalScopeStatement, filePathScopeStatementAnalysis, filePathReport,
                projectDev, projectValidation, teamMembers);
    }

}