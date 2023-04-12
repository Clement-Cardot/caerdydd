package com.caerdydd.taf.models.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "teaching_staff")

public class TeachingStaffEntity {

  @Id
  private Integer idUser;
  private Integer isInfrastructureSpecialist;
  private Integer isDevelopmentSpecialist;
  private Integer isModelingSpecialist;
  private Integer isOptionLeader;
  private Integer isSubjectValidator;

  @OneToOne
  @MapsId
  @JoinColumn(name = "id_user")
  private UserEntity user;

  public TeachingStaffEntity(){

  }

  public TeachingStaffEntity(UserEntity user, Integer isInfrastructureSpecialist, Integer isDevelopmentSpecialist, Integer isModelingSpecialist, Integer isOptionLeader, Integer isSubjectValidator){
    this.user = user;
    this.isInfrastructureSpecialist = isInfrastructureSpecialist;
    this.isDevelopmentSpecialist = isDevelopmentSpecialist;
    this.isModelingSpecialist = isModelingSpecialist;
    this.isOptionLeader = isOptionLeader;
    this.isSubjectValidator = isSubjectValidator;
  }
}
