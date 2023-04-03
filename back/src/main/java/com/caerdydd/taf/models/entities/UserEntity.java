package com.caerdydd.taf.models.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String name;
    private String surname;
    private String login;
    private String password;
    private String email;
    private String role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TeamMemberEntity teamMember;

    // @OneToOne(mappedBy = "user")
    // @PrimaryKeyJoinColumn
    // private TeachingStaffEntity teachingStaff;

    // @OneToMany(mappedBy = "user")
    // private List<NotificationEntity> notifications;

    public UserEntity() {
    }

    public UserEntity( String name, String surname, String login, String password, String email, String role) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
    }

}
