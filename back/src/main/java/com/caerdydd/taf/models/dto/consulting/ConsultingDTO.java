package com.caerdydd.taf.models.dto.consulting;

import org.springframework.stereotype.Component;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ConsultingDTO {

    private Integer idConsulting;
    private String speciality;
    private String notes;

    @JsonManagedReference(value = "availabilities")
    private PlannedTimingAvailabilityDTO plannedTimingAvailability;

    @JsonManagedReference(value = "consulting")
    private PlannedTimingConsultingDTO plannedTimingConsulting;

    private TeamDTO team;

    public ConsultingDTO() {
    }

    @Override
    public String toString() {
        String idPlannedTimingConsulting = "null";
        String idPlannedTimingAvailability = "null";

        if (this.plannedTimingConsulting != null){
            idPlannedTimingConsulting = String.valueOf(this.plannedTimingConsulting.getIdPlannedTimingConsulting());
        }

        if (this.plannedTimingAvailability != null){
            idPlannedTimingAvailability = String.valueOf(this.plannedTimingAvailability.getIdPlannedTimingAvailability());
        }
        return "ConsultingDTO [idConsulting=" + idConsulting + ", speciality=" + speciality + ", notes=" + notes + ", team=" + team + ", idPlannedTimingConsulting=" + idPlannedTimingConsulting + ", idPlannedTimingAvailability" + idPlannedTimingAvailability + "]";
    }
}
