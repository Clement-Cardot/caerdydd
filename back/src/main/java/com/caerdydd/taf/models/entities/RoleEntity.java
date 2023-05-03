package com.caerdydd.taf.models.entities;

import java.io.Serializable;

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
public class RoleEntity implements Serializable {

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

    @Override
    public String toString() {
        return "RoleEntity [idRole=" + idRole + ", role=" + role + ", user=" + user + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != RoleEntity.class) {
            return false;
        }
        RoleEntity roleEntity = (RoleEntity) o;
        return this.getIdRole().equals(roleEntity.getIdRole()) 
            && this.getRole().equals(roleEntity.getRole())
            && this.getUser().equals(roleEntity.getUser());
    }

    @Override
    public int hashCode() {
        return this.getIdRole().hashCode();
    }
    
}
