package com.caerdydd.taf.models.dto.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Array;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeachingStaffDTO {

    private Integer idUser;

    @JsonManagedReference(value = "teachingStaff")
    private UserDTO user;

    private Boolean isInfrastructureSpecialist = false;
    private Boolean isDevelopmentSpecialist = false;
    private Boolean isModelingSpecialist = false;
    private Boolean isOptionLeader = false;
    private Boolean isSubjectValidator = false;

    @JsonIgnore
    private List<JuryDTO> juries1;

    @JsonIgnore
    private List<JuryDTO> juries2;

    @JsonGetter("juries")
    private List<JuryDTO> juries(){
        List<JuryDTO> juries = new ArrayList<>();
        juries.addAll(juries1);
        juries.addAll(juries2);
        return juries;
    }

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
