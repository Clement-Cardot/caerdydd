package com.caerdydd.taf.models.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonBackReference(value="jury")
    private List<PresentationDTO> presentations;

    public JuryDTO() {
    }

    public JuryDTO(UserDTO ts1, UserDTO ts2) {
        this.ts1 = ts1;
        this.ts2 = ts2;
    }

    @Override
    public String toString() {
        return "{" +
            " idJury='" + getIdJury() + "'" +
            ", ts1='" + getTs1() + "'" +
            ", ts2='" + getTs2() + "'" +
            ", presentations='" + getPresentations() + "'" +
            "}";
    }

}
