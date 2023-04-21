package com.caerdydd.taf.models.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ProjectDTO {

    private Integer idProject;
    private String name;
    private String description;
    private boolean isValidated;

    public ProjectDTO() {
    }

    public ProjectDTO(Integer idProject, String name, String description, boolean isValidated) {
        this.idProject = idProject;
        this.name = name;
        this.description = description;
        this.isValidated = isValidated;
    }

    @Override
    public String toString() {
        return "ProjectDTO [idProject=" + idProject + ", name=" + name + ", description=" + description
                + ", isValidated=" + isValidated + "]";
    }
}
