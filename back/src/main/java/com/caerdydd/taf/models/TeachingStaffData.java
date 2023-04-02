package com.caerdydd.taf.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "teaching_staff")
public class TeachingStaffData extends UserAdditionalData{
    
    private Integer idUser;
    private Boolean isInfrastructureSpecialist;
    private Boolean isDevelopmentSpecialist;
    private Boolean isModelingSpecialist;
    private Boolean isOptionLeader;
    private Boolean isSubjectValidator;

    public TeachingStaffData() {
    }

    public TeachingStaffData(Integer idUser, Boolean isInfrastructureSpecialist, Boolean isDevelopmentSpecialist,
            Boolean isModelingSpecialist, Boolean isOptionLeader, Boolean isSubjectValidator) {
        this.idUser = idUser;
        this.isInfrastructureSpecialist = isInfrastructureSpecialist;
        this.isDevelopmentSpecialist = isDevelopmentSpecialist;
        this.isModelingSpecialist = isModelingSpecialist;
        this.isOptionLeader = isOptionLeader;
        this.isSubjectValidator = isSubjectValidator;
    }

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Boolean getIsInfrastructureSpecialist() {
        return isInfrastructureSpecialist;
    }

    public void setIsInfrastructureSpecialist(Boolean isInfrastructureSpecialist) {
        this.isInfrastructureSpecialist = isInfrastructureSpecialist;
    }

    public Boolean getIsDevelopmentSpecialist() {
        return isDevelopmentSpecialist;
    }

    public void setIsDevelopmentSpecialist(Boolean isDevelopmentSpecialist) {
        this.isDevelopmentSpecialist = isDevelopmentSpecialist;
    }

    public Boolean getIsModelingSpecialist() {
        return isModelingSpecialist;
    }

    public void setIsModelingSpecialist(Boolean isModelingSpecialist) {
        this.isModelingSpecialist = isModelingSpecialist;
    }

    public Boolean getIsOptionLeader() {
        return isOptionLeader;
    }

    public void setIsOptionLeader(Boolean isOptionLeader) {
        this.isOptionLeader = isOptionLeader;
    }

    public Boolean getIsSubjectValidator() {
        return isSubjectValidator;
    }

    public void setIsSubjectValidator(Boolean isSubjectValidator) {
        this.isSubjectValidator = isSubjectValidator;
    }

    // Methods
    public String toString() {
        return "TeachingStaffData [idUser=" + idUser + ", isInfrastructureSpecialist=" + isInfrastructureSpecialist
                + ", isDevelopmentSpecialist=" + isDevelopmentSpecialist + ", isModelingSpecialist=" + isModelingSpecialist
                + ", isOptionLeader=" + isOptionLeader + ", isSubjectValidator=" + isSubjectValidator + "]";
    }

}
