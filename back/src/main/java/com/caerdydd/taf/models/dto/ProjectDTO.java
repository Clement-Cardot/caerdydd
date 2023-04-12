package com.caerdydd.taf.models.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ProjectDTO {
    private Integer idProject;
    private String name;
    private String description;

    @JsonBackReference(value="projectDev")
    private TeamDTO teamDev;

    @JsonBackReference(value="projectValidation")
    private TeamDTO teamValidation;

    public ProjectDTO() {
    }




//@JsonBackReference
//private JuryDTO jury;
}
