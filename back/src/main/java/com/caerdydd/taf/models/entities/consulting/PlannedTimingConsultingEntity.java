package com.caerdydd.taf.models.entities.consulting;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "planned_timing_consulting")
public class PlannedTimingConsultingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlannedTimingConsulting;
    private LocalDateTime datetimeBegin;
    private LocalDateTime datetimeEnd;

    @OneToMany(mappedBy = "plannedTimingConsulting", cascade = CascadeType.ALL)
    private List<PlannedTimingAvailabilityEntity> teachingStaffAvailabilities = new ArrayList<>();

    public PlannedTimingConsultingEntity() {
    }

    public PlannedTimingConsultingEntity(LocalDateTime datetimeBegin, LocalDateTime datetimeEnd) {
        this.datetimeBegin = datetimeBegin;
        this.datetimeEnd = datetimeEnd;
    }
    
    @Override
    public String toString() {
        return "PlannedTimingConsultingEntity [idPlannedTimingConsulting=" + idPlannedTimingConsulting
                + ", datetimeBegin=" + datetimeBegin + ", datetimeEnd=" + datetimeEnd + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != PlannedTimingConsultingEntity.class) {
            return false;
        }
        PlannedTimingConsultingEntity plannedTimingConsulting = (PlannedTimingConsultingEntity) o;
        return this.idPlannedTimingConsulting.equals(plannedTimingConsulting.getIdPlannedTimingConsulting()) 
            && this.datetimeBegin.equals(plannedTimingConsulting.getDatetimeBegin()) 
            && this.datetimeEnd.equals(plannedTimingConsulting.getDatetimeEnd());
    }

    @Override
    public int hashCode() {
        return this.idPlannedTimingConsulting.hashCode();
    }
}
