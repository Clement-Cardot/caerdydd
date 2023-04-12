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


//@JsonBackReference
//private JuryDTO jury;
}
