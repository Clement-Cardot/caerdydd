package com.caerdydd.taf.models.dto.project;

import java.util.List;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;


@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "idProject")
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

    @JsonManagedReference(value="project")
    private List<PresentationDTO> presentations;

    private JuryDTO jury;

    public ProjectDTO() {
    }

    public ProjectDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }


    @Override
    public String toString() {
        return "ProjectDTO [" +
                "idProject=" + idProject +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isValidated=" + isValidated +
                ", idTeamDev=" + teamDev +
                ", idTeamValidation=" + teamValidation +
                ']';
    }
}
