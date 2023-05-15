package com.caerdydd.taf.models.dto.consulting;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ConsultingDTO {

    private Integer idConsulting;

    @CsvDate("yyyyMMdd\'T\'HHmmss")
    @CsvBindByPosition(position = 0, required = true)
    private LocalDateTime  datetimeBegin;

    @CsvDate("yyyyMMdd\'T\'HHmmss")
    @CsvBindByPosition(position = 1, required = true)
    private LocalDateTime  datetimeEnd;

    private String speciality;
    private String notes;
    private Boolean isValidated = false;
    private Boolean isReserved = false;

    //@JsonIdentityReference
    private TeamDTO team;

    public ConsultingDTO() {
    }

    public ConsultingDTO(LocalDateTime  datetimeBegin, LocalDateTime  datetimeEnd) {
        this.datetimeBegin = datetimeBegin;
        this.datetimeEnd = datetimeEnd;
    }

    @Override
    public String toString() {
        return "ConsultingDTO [idConsulting=" + idConsulting + ", datetimeBegin=" + datetimeBegin + ", datetimeEnd="
                + datetimeEnd + ", speciality=" + speciality + ", notes=" + notes + ", isValidated=" + isValidated
                + ", isReserved=" + isReserved + ", team=" + team + "]";
    }
        
}
