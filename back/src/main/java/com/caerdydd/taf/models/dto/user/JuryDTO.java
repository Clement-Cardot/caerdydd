package com.caerdydd.taf.models.dto.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class JuryDTO {
    private Integer idJury;

    @JsonIgnore
    private TeachingStaffDTO ts1;
    
    @JsonIgnore
    private TeachingStaffDTO ts2;

    @JsonGetter("teachingStaff")
    private List<TeachingStaffDTO> teachingStaff(){
        List<TeachingStaffDTO> teachingStaff = new ArrayList<>();
        teachingStaff.add(ts1);
        teachingStaff.add(ts2);
        return teachingStaff;
    }

    public JuryDTO() {
    }

    public JuryDTO(Integer idJury) {
        this.idJury = idJury;
    }

    public JuryDTO(TeachingStaffDTO ts1, TeachingStaffDTO ts2) {
        this.ts1 = ts1;
        this.ts2 = ts2;
    }

    @Override
    public String toString() {
        return "{" +
            " idJury='" + getIdJury() + "'" +
            ", ts1='" + getTs1() + "'" +
            ", ts2='" + getTs2() + "'" +
            "}";
    }

}
