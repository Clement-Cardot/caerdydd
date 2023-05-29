package com.caerdydd.taf.models.dto.consulting;

import org.springframework.stereotype.Component;
import com.caerdydd.taf.models.dto.project.TeamDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ConsultingDTO {

    private Integer idConsulting;
    private String speciality;
    private String notes;
    private Boolean isValidated = false;
    private Boolean isReserved = false;

    private PlannedTimingAvailabilityDTO plannedTimingAvailability;

    private PlannedTimingConsultingDTO plannedTimingConsulting;

    private TeamDTO team;

    public ConsultingDTO() {
    }

    @Override
    public String toString() {
        return "ConsultingDTO [idConsulting=" + idConsulting + ", speciality=" + speciality + ", notes=" + notes + ", isValidated=" + isValidated
                + ", isReserved=" + isReserved + ", team=" + team + "]";
    }
        
}
