package com.caerdydd.taf.models.dto;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.entities.JuryEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class JuryDTO {
    private Integer idJury;

    @JsonManagedReference(value="ts1")
    private UserDTO ts1;
    
    @JsonManagedReference(value="ts2")
    private UserDTO ts2;

    public JuryDTO() {
    }

    public JuryDTO(UserDTO ts1, UserDTO ts2) {
        this.ts1 = ts1;
        this.ts2 = ts2;
    }

    public JuryEntity toJuryEntity(){
        JuryEntity juryEntity = new JuryEntity();
        juryEntity.setIdJury(this.idJury);
        //juryEntity.setTs1(this.ts1);
        //juryEntity.setTs2(this.ts2);

        return juryEntity;
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
