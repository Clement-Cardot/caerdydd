package com.caerdydd.taf.models.entities.user;

import java.io.Serializable;
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

import com.caerdydd.taf.models.entities.notification.NotificationEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity implements Serializable {

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
    private List<RoleEntity> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TeamMemberEntity teamMember;

    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private TeachingStaffEntity teachingStaff;

    @OneToMany(mappedBy = "user")
    private List<NotificationEntity> notifications;

    public UserEntity() {
    }

    public UserEntity(String firstname, String lastname, String login, String password, String email, String speciality) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.speciality = speciality;
    }

    public UserEntity(Integer id, String firstname, String lastname, String login, String password, String email, String speciality) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.login = login;
        this.password = password;
        this.email = email;
        this.speciality = speciality;
    }

    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (o.getClass() != UserEntity.class) {
            return false;
        }
        UserEntity userEntity = (UserEntity) o;
        return this.id.equals(userEntity.getId())
            && this.firstname.equals(userEntity.getFirstname())
            && this.lastname.equals(userEntity.getLastname())
            && this.login.equals(userEntity.getLogin())
            && this.password.equals(userEntity.getPassword())
            && this.email.equals(userEntity.getEmail())
            && this.speciality.equals(userEntity.getSpeciality());
    }

    @Override
    public int hashCode(){
        return this.id.hashCode();
    }

}
