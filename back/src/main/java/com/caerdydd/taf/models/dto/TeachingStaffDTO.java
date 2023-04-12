package com.caerdydd.taf.models.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component

public class TeachingStaffDTO {

  private Integer idUser;
  private Integer isInfrastructureSpecialist;
  private Integer isDevelopmentSpecialist;
  private Integer isModelingSpecialist;
  private Integer isOptionLeader;
  private Integer isSubjectValidator;

  @JsonManagedReference
  private UserDTO user;

}
