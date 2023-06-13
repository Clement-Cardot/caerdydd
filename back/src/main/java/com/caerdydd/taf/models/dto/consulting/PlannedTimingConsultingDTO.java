package com.caerdydd.taf.models.dto.consulting;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PlannedTimingConsultingDTO {
    
    private Integer idPlannedTimingConsulting;

    @CsvDate("yyyyMMdd\'T\'HHmmss")
    @CsvBindByPosition(position = 0, required = true)
    private LocalDateTime  datetimeBegin;

    @CsvDate("yyyyMMdd\'T\'HHmmss")
    @CsvBindByPosition(position = 1, required = true)
    private LocalDateTime  datetimeEnd;

    @JsonManagedReference(value = "teachingStaffAvailabilities")
    private List<PlannedTimingAvailabilityDTO> teachingStaffAvailabilities;

    @JsonBackReference(value = "consulting")
    private ConsultingDTO consulting;

    public PlannedTimingConsultingDTO() {
    }

    public PlannedTimingConsultingDTO(LocalDateTime datetimeBegin, LocalDateTime datetimeEnd) {
        this.datetimeBegin = datetimeBegin;
        this.datetimeEnd = datetimeEnd;
    }

    @Override
    public String toString() {
        return "PlannedTimingConsultingDTO [idPlannedTimingConsulting=" + idPlannedTimingConsulting + ", datetimeBegin=" + datetimeBegin + ", datetimeEnd="
                + datetimeEnd + "]";
    }
}
