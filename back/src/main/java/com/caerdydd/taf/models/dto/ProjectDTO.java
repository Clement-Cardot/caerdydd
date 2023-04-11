package com.caerdydd.taf.models.dto;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ProjectDTO {

    private Integer id;
    private String name;
    private String description;
    private String is_validated;
    private String id_jury;
    
}
