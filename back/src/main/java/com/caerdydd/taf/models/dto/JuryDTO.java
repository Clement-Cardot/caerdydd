package com.caerdydd.taf.models.dto;

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

    @JsonManagedReference
    private UserDTO juryMemberLD;
    
    @JsonBackReference
    private UserDTO juryMemberCSS;

    public JuryDTO() {
    }

    public JuryDTO(UserDTO juryMemberLD, UserDTO juryMemberCSS) {
        this.juryMemberLD = juryMemberLD;
        this.juryMemberCSS = juryMemberCSS;
    }

    @Override
    public String toString() {
        return "{" +
            " juryMemberLD='" + getJuryMemberLD() + "'" +
            ", juryMemberCSS='" + getJuryMemberCSS() + "'" +
            "}";
    }

}
