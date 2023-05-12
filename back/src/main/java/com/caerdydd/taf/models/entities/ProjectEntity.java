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
@Table(name = "project")
public class ProjectEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProject;
    private String name;
    private String description;
    private Boolean isValidated;

    private Integer idJury;

    @OneToOne(mappedBy = "projectDev")
    private TeamEntity teamDev;

    @OneToOne(mappedBy = "projectValidation")
    private TeamEntity teamValidation;

    @OneToMany
    @JoinColumn(name = "id_project")
    private List<PresentationEntity> presentations;

//    @OneToOne
//    @JoinColumn(name = "id_jury")
//    private JuryEntity jury;


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

    @Override
    public String toString() {
        return "ProjectEntity [description=" + description + ", idProject=" + idProject + ", idJury=" + idJury
                + ", isValidated=" + isValidated + ", name=" + name + ", teamDev=" + teamDev + ", teamValidation="
                + teamValidation + "presentation="+presentations+"]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProjectEntity)) {
            return false;
        }
        ProjectEntity teamEntity = (ProjectEntity) o;
        return this.idProject.equals(teamEntity.getIdProject())
            && this.name.equals(teamEntity.getName())
            && this.description.equals(teamEntity.getDescription())
            && this.isValidated.equals(teamEntity.getIsValidated())
            && this.idJury.equals(teamEntity.getIdJury())
            && this.teamDev.equals(teamEntity.getTeamDev())
            && this.teamValidation.equals(teamEntity.getTeamValidation());
    }
}
