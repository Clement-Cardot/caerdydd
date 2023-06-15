package com.caerdydd.taf.models.dto.project;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "idPresentation")
@Getter
@Setter
@Component
public class PresentationDTO {
    
    public static final String PRESENTATION_INTERMEDAIRE = "PRESENTATION_INTERMEDAIRE";
    public static final String PRESENTATION_FINALE = "PRESENTATION_FINALE";
    public static final String AUDIT_CSS = "AUDIT_CSS";
    public static final String AUDIT_LD = "AUDIT_LD";
    
    private Integer idPresentation;

    private String type;
    private LocalDateTime  datetimeBegin; 
    private LocalDateTime  datetimeEnd;
    private String room;
    private String jury1Notes;
    private String jury2Notes;
    private String validationTeamNotes;

    private JuryDTO jury;

    private ProjectDTO project;

    public PresentationDTO() {
    }

    public PresentationDTO(Integer idPresentation) {
        this.idPresentation = idPresentation;
    }

    public PresentationDTO(Integer idPresentation, String type) {
        this.idPresentation = idPresentation;
        this.type=type;
    }

    @Override
    public String toString() {
        return "{" +
            " idPresentation='" + getIdPresentation() + "'" +
            ", type='" + getType() + "'" +
            ", datetimeBegin='" + getDatetimeBegin() + "'" +
            ", datetimeEnd='" + getDatetimeEnd() + "'" +
            ", room='" + getRoom() + "'" +
            ", jury1Notes='" + getJury1Notes() + "'" +
            ", jury2Notes='" + getJury2Notes() + "'" +
            ", validationTeamNotes='" + getValidationTeamNotes() + "'" +
            ", jury='" + getJury() + "'" +
            ", project='" + getProject() + "'" +
            "}";
    }
    
}
