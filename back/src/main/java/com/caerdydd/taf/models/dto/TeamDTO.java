package com.caerdydd.taf.models.dto;

import java.util.List;
import org.springframework.stereotype.Component;
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
    private Integer idProjectDev;
    private Integer idProjectValidation;

    @JsonManagedReference
    List<TeamMemberDTO> teamMembers;

}
