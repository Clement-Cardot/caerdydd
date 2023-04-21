package com.caerdydd.taf.models.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    private Integer idProject;
    private String name;
    private String description;
    private Boolean isValidated;

    public ProjectEntity() {
    }

    public ProjectEntity(Integer idProject, String name) {
        this.idProject=idProject;
        this.name=name;
    }

    public ProjectEntity(Integer idProject, String name, String description) {
        this.idProject=idProject;
        this.name=name;
        this.description=description;
    }

    public ProjectEntity(Integer idProject, String name, String description, Boolean isValidated) {
        this.idProject=idProject;
        this.name=name;
        this.description=description;
        this.isValidated=isValidated;
    }
}
