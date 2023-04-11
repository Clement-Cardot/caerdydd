package com.caerdydd.taf.models.dto;

import java.util.List;
import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.entities.ProjectEntity;
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

    @JsonManagedReference
    List<TeamMemberDTO> teamMembers;

    @JsonManagedReference
    private ProjectEntity projectDev;

    @JsonManagedReference
    private ProjectEntity projectValidation;

}
