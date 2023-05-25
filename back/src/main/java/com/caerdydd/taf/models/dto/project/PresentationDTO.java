package com.caerdydd.taf.models.dto.project;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "idPresentation")
  @Getter
@Setter
@Component
public class PresentationDTO {
    
    public static final String INTERMEDIATE = "INTERMEDIATE";
    public static final String FINAL = "FINAL";
    
    private Integer idPresentation;

    private String type;
    private LocalDateTime  datetimeBegin; 
    private LocalDateTime  datetimeEnd;
    private String room;
    private String jury1Notes;
    private String jury2Notes;

    private JuryDTO jury;

    private ProjectDTO project;

    public PresentationDTO() {
    }

    public PresentationDTO(Integer idPresentation) {
        this.idPresentation = idPresentation;
    }

    @Override
    public String toString() {
        return "PresentationDTO [id=" + idPresentation + ", type=" + type + ", datetimeBegin=" + datetimeBegin + ", datetimeEnd="
                + datetimeEnd + ", room=" + room + ", jury1Notes=" + jury1Notes + ", jury2Notes=" + jury2Notes
                + ", idJury=" + jury + ", idProject=" + project + "]";
    }
}
