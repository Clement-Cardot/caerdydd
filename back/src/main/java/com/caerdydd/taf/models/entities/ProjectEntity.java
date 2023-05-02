package com.caerdydd.taf.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProject;
    private String name;
    private String description;
    private Boolean isValidated;

    public ProjectEntity() {
    }

    public ProjectEntity(String name, String description) {
        this.name=name;
        this.description=description;
        this.isValidated=false;
    }

    public ProjectEntity(Integer idProject, String name, String description, Boolean isValidated) {
        this.idProject=idProject;
        this.name=name;
        this.description=description;
        this.isValidated=isValidated;
    }
}
