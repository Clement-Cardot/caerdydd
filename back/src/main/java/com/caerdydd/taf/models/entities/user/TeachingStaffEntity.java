package com.caerdydd.taf.models.entities.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caerdydd.taf.models.entities.consulting.PlannedTimingAvailabilityEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "teaching_staff")
public class TeachingStaffEntity implements Serializable{

    @Id
    private Integer idUser;

    @OneToOne
    @JoinColumn(name = "id_user")
    private UserEntity user;

    private Boolean isInfrastructureSpecialist = false;
    private Boolean isDevelopmentSpecialist = false;
    private Boolean isModelingSpecialist = false;
    private Boolean isOptionLeader = false;
    private Boolean isSubjectValidator = false;

    @OneToMany(mappedBy = "teachingStaff")
    private List<PlannedTimingAvailabilityEntity> availabilities;

    public TeachingStaffEntity() {
    }

    public TeachingStaffEntity(UserEntity user) {
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

    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (o.getClass() != TeachingStaffEntity.class) {
            return false;
        }
        TeachingStaffEntity teachingStaffEntity = (TeachingStaffEntity) o;
        return this.getIdUser().equals(teachingStaffEntity.getIdUser())
            && this.getUser().equals(teachingStaffEntity.getUser())
            && this.getIsInfrastructureSpecialist().equals(teachingStaffEntity.getIsInfrastructureSpecialist())
            && this.getIsDevelopmentSpecialist().equals(teachingStaffEntity.getIsDevelopmentSpecialist())
            && this.getIsModelingSpecialist().equals(teachingStaffEntity.getIsModelingSpecialist())
            && this.getIsOptionLeader().equals(teachingStaffEntity.getIsOptionLeader())
            && this.getIsSubjectValidator().equals(teachingStaffEntity.getIsSubjectValidator());
    }
    
    @Override
    public int hashCode() {
        return this.idUser.hashCode();
    }
    
}
