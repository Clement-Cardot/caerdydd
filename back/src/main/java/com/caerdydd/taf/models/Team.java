package com.caerdydd.taf.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team {

    Integer idTeam;
    String name;
    Integer teamWorkMark;
    Integer teamValidationMark;
    String testBookLink;
    String filePathScopeStatement;
    String filePathFinalScopeStatement;
    String filePathScopeStatementAnalysis;
    String filePathReport;
    Integer idProjectDev;
    Integer idProjectValidation;

    @OneToMany(mappedBy = "team")
    List<UserEntity> teamMembers;

    public Team() {
    }

    public Team(Integer idTeam, String name) {
        this.idTeam = idTeam;
        this.name = name;
    }

    public Team(Integer idTeam, String name, Integer teamWorkMark, Integer teamValidationMark, String testBookLink,
            String filePathScopeStatement, String filePathFinalScopeStatement, String filePathScopeStatementAnalysis,
            String filePathReport, Integer idProjectDev, Integer idProjectValidation) {

        this.idTeam = idTeam;
        this.name = name;
        this.teamWorkMark = teamWorkMark;
        this.teamValidationMark = teamValidationMark;
        this.testBookLink = testBookLink;
        this.filePathScopeStatement = filePathScopeStatement;
        this.filePathFinalScopeStatement = filePathFinalScopeStatement;
        this.filePathScopeStatementAnalysis = filePathScopeStatementAnalysis;
        this.filePathReport = filePathReport;
        this.idProjectDev = idProjectDev;
        this.idProjectValidation = idProjectValidation;
    }

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(Integer idTeam) {
        this.idTeam = idTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTeamWorkMark() {
        return teamWorkMark;
    }

    public void setTeamWorkMark(Integer teamWorkMark) {
        this.teamWorkMark = teamWorkMark;
    }

    public Integer getTeamValidationMark() {
        return teamValidationMark;
    }

    public void setTeamValidationMark(Integer teamValidationMark) {
        this.teamValidationMark = teamValidationMark;
    }

    public String getTestBookLink() {
        return testBookLink;
    }

    public void setTestBookLink(String testBookLink) {
        this.testBookLink = testBookLink;
    }

    public String getFilePathScopeStatement() {
        return filePathScopeStatement;
    }

    public void setFilePathScopeStatement(String filePathScopeStatement) {
        this.filePathScopeStatement = filePathScopeStatement;
    }

    public String getFilePathFinalScopeStatement() {
        return filePathFinalScopeStatement;
    }

    public void setFilePathFinalScopeStatement(String filePathFinalScopeStatement) {
        this.filePathFinalScopeStatement = filePathFinalScopeStatement;
    }

    public String getFilePathScopeStatementAnalysis() {
        return filePathScopeStatementAnalysis;
    }

    public void setFilePathScopeStatementAnalysis(String filePathScopeStatementAnalysis) {
        this.filePathScopeStatementAnalysis = filePathScopeStatementAnalysis;
    }

    public String getFilePathReport() {
        return filePathReport;
    }

    public void setFilePathReport(String filePathReport) {
        this.filePathReport = filePathReport;
    }

    public Integer getIdProjectDev() {
        return idProjectDev;
    }

    public void setIdProjectDev(Integer idProjectDev) {
        this.idProjectDev = idProjectDev;
    }

    public Integer getIdProjectValidation() {
        return idProjectValidation;
    }

    public void setIdProjectValidation(Integer idProjectValidation) {
        this.idProjectValidation = idProjectValidation;
    }

    @Override
    public String toString() {
        return "Team [idTeam=" + idTeam + ", name=" + name + ", teamWorkMark=" + teamWorkMark + ", teamValidationMark="
                + teamValidationMark + ", testBookLink=" + testBookLink + ", filePathScopeStatement="
                + filePathScopeStatement + ", filePathFinalScopeStatement=" + filePathFinalScopeStatement
                + ", filePathScopeStatementAnalysis=" + filePathScopeStatementAnalysis + ", filePathReport="
                + filePathReport + ", idProjectDev=" + idProjectDev + ", idProjectValidation=" + idProjectValidation
                + "]";
    }

}

