package com.caerdydd.taf.models.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
    private Integer id;
    private String name;
    private String description;
    private String is_validated;
    private String id_jury;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<RoleEntity> roleEntities;

    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TeamMemberEntity teamMember;

    public ProjectEntity() {
    }

    public ProjectEntity( String name, String description, String is_validated, String id_jury) {
        this.name = name;
        this.description = description;
        this.is_validated = is_validated;
        this.id_jury = id_jury;
    }
}
