package com.caerdydd.taf.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRole;
    private String role;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    public RoleEntity() {
    }

    public RoleEntity(Integer idRole, String role, UserEntity user) {
        this.idRole = idRole;
        this.role = role;
        this.user = user;
    }
    
}
