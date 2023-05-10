package com.caerdydd.taf.models.dto;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.entities.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeachingStaffDTO {

    private Integer idUser;

    private UserEntity user;

    private Boolean isInfrastructureSpecialist = false;
    private Boolean isDevelopmentSpecialist = false;
    private Boolean isModelingSpecialist = false;
    private Boolean isOptionLeader = false;
    private Boolean isSubjectValidator = false;

    public TeachingStaffDTO() {
    }

    public TeachingStaffDTO(UserEntity user) {
        this.idUser = user.getId();
        this.user = user;
    }

    @Override
    public String toString() {
        return "TeachingStaffEntity [idUser=" + idUser + ", user=" + user + ", isInfrastructureSpecialist="
                + isInfrastructureSpecialist + ", isDevelopmentSpecialist=" + isDevelopmentSpecialist
                + ", isModelingSpecialist=" + isModelingSpecialist + ", isOptionLeader=" + isOptionLeader
                + ", isSubjectValidator=" + isSubjectValidator + "]";
    }
    
}