package com.caerdydd.taf.models.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

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
    @CsvBindByPosition(position = 0)
    private Date datetimeBegin;

    @CsvDate("yyyyMMdd\'T\'HHmmss")
    @CsvBindByPosition(position = 1)
    private Date datetimeEnd;

    private String speciality;
    private String notes;
    private Boolean isValidated;
    private Boolean isReserved;

    //@JsonIdentityReference
    private TeamDTO team;

    public ConsultingDTO() {
    }

    public ConsultingDTO(Date datetimeBegin, Date datetimeEnd) {
        this.datetimeBegin = datetimeBegin;
        this.datetimeEnd = datetimeEnd;
        this.isValidated = false;
        this.isReserved = false;
    }

    @Override
    public String toString() {
        return "ConsultingDTO [idConsulting=" + idConsulting + ", datetimeBegin=" + datetimeBegin + ", datetimeEnd="
                + datetimeEnd + ", speciality=" + speciality + ", notes=" + notes + ", isValidated=" + isValidated
                + ", isReserved=" + isReserved + ", team=" + team + "]";
    }
        
}
