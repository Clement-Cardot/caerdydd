package com.caerdydd.taf.models.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MapsId;
import javax.persistence.JoinColumn;
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
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private String email;
    private String speciality;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RoleEntity> roleEntities;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TeamMemberEntity teamMember;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id_user")
    private UserEntity user;

    // @OneToOne(mappedBy = "user")
    // @PrimaryKeyJoinColumn
    // private TeachingStaffEntity teachingStaff;

    // @OneToMany(mappedBy = "user")
    // private List<NotificationEntity> notifications;

    public UserEntity() {
    }

    public UserEntity( String firstname, String lastname, String login, String password, String email, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.email = email;
    }

}
