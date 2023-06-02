package com.caerdydd.taf.models.dto.user;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class JuryDTO {
    private Integer idJury;

    private TeachingStaffDTO ts1;
    
    private TeachingStaffDTO ts2;


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
