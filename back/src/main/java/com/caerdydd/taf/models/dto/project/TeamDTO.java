package com.caerdydd.taf.models.dto.project;

import java.util.List;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamDTO {
    
    private Integer idTeam;
    private String name;
    private Integer teamWorkMark;
    private Integer teamValidationMark;
    private String testBookLink;
    private String filePathScopeStatement;
    private String filePathFinalScopeStatement;
    private String filePathScopeStatementAnalysis;
    private String filePathReport;
    private Boolean isReportAnnotation;
    private String reportComments;

    @JsonManagedReference(value="team")
    List<TeamMemberDTO> teamMembers;

    @JsonManagedReference(value="projectDev")
    private ProjectDTO projectDev;

    @JsonManagedReference(value="projectValidation")
    private ProjectDTO projectValidation;
    
    public TeamDTO() {
    }

    public TeamDTO(Integer idTeam, String name, ProjectDTO projectDev, ProjectDTO projectValidation) {
        this.idTeam = idTeam;
        this.name = name;
        this.projectDev = projectDev;
        this.projectValidation = projectValidation;
    }

    @Override
    public String toString() {
        return "TeamDTO [idTeam=" + idTeam + ", name=" + name + ", teamWorkMark=" + teamWorkMark
                + ", teamValidationMark=" + teamValidationMark + ", testBookLink=" + testBookLink
                + ", filePathScopeStatement=" + filePathScopeStatement + ", filePathFinalScopeStatement="
                + filePathFinalScopeStatement + ", filePathScopeStatementAnalysis=" + filePathScopeStatementAnalysis
                + ", filePathReport=" + filePathReport + ", isReportAnnotation=" + isReportAnnotation + ", idProjectDev=" + projectDev.getIdProject() + ", idProjectValidation="
                + projectValidation.getIdProject() + ", teamMembers=" + teamMembers + "]";
    }

}
