package com.caerdydd.taf.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

    private Integer idJury;

    @OneToOne(mappedBy = "projectDev")
    private TeamEntity teamDev;

    @OneToOne(mappedBy = "projectValidation")
    private TeamEntity teamValidation;

//    @OneToOne
//    @JoinColumn(name = "id_jury")
//    private JuryEntity jury;


    public ProjectEntity() {
    }

    public ProjectEntity(String name, String description) {
        this.idProject=idProject;
        this.name=name;
        this.description=description;
    }


}
