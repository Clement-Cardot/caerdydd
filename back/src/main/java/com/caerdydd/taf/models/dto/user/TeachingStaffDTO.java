package com.caerdydd.taf.models.dto.user;

import java.util.List;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeachingStaffDTO {

    private Integer idUser;

    @JsonManagedReference
    private UserDTO user;

    private Boolean isInfrastructureSpecialist = false;
    private Boolean isDevelopmentSpecialist = false;
    private Boolean isModelingSpecialist = false;
    private Boolean isOptionLeader = false;
    private Boolean isSubjectValidator = false;

    @JsonBackReference(value = "availabilities")
    private List<PlannedTimingAvailabilityDTO> availabilities;

    public TeachingStaffDTO() {
    }

    public TeachingStaffDTO(UserDTO user) {
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
