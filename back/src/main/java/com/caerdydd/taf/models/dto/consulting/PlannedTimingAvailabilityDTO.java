package com.caerdydd.taf.models.dto.consulting;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PlannedTimingAvailabilityDTO {

    private Integer idPlannedTimingAvailability;

    @JsonBackReference(value = "teachingStaffAvailabilities")
    private PlannedTimingConsultingDTO plannedTimingConsulting;

    @JsonManagedReference(value = "availabilities")
    private TeachingStaffDTO teachingStaff;

    private Boolean isAvailable = true;

    public PlannedTimingAvailabilityDTO() {
    }
    
}
