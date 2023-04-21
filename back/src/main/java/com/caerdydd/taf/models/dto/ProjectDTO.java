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
    private Boolean isValidated = false;

    @JsonBackReference(value="projectDev")
    private TeamDTO teamDev;

    @JsonBackReference(value="projectValidation")
    private TeamDTO teamValidation;

    public ProjectDTO() {
    }

    public ProjectDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }


    @Override
    public String toString() {
        return "ProjectDTO [idProject=" + idProject + ", name=" + name + ", description=" + description
                + ", isValidated=" + isValidated + "]";
    }
}
