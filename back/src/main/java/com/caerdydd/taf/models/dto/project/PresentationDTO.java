package com.caerdydd.taf.models.dto.project;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PresentationDTO {
    
    public static final String INTERMEDIATE = "INTERMEDIATE";
    public static final String FINAL = "FINAL";
    
    private Integer idPresentation;

    private String type;

    @CsvDate("yyyyMMdd\'T\'HHmmss")
    @CsvBindByPosition(position = 0, required = true)
    private LocalDateTime  datetimeBegin; 

    @CsvDate("yyyyMMdd\'T\'HHmmss")
    @CsvBindByPosition(position = 1, required = true)
    private LocalDateTime  datetimeEnd;

    private String room;
    private String jury1Notes;
    private String jury2Notes;

    private JuryDTO jury;

    @JsonBackReference(value="project")
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
