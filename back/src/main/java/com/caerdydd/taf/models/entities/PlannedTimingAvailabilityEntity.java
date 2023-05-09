package com.caerdydd.taf.models.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@IdClass(PlannedTimingAvailabilityEntityId.class)
@Table(name = "planned_timing_availability")
public class PlannedTimingAvailabilityEntity implements Serializable{

    @Id
    @ManyToOne
    @JoinColumn(name = "id_planned_timing_consulting")
    private PlannedTimingConsultingEntity plannedTimingConsulting;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_ts")
    private TeachingStaffEntity teachingStaff;

    private Boolean isAvailable = true;

    public PlannedTimingAvailabilityEntity() {
    }

    public PlannedTimingAvailabilityEntity(PlannedTimingConsultingEntity plannedTimingConsulting, TeachingStaffEntity teachingStaff) {
        this.plannedTimingConsulting = plannedTimingConsulting;
        this.teachingStaff = teachingStaff;
    }

    @Override
    public String toString() {
        return "PlannedTimingAvailabilityEntity [plannedTimingConsulting=" + plannedTimingConsulting + ", teachingStaff="
                + teachingStaff + ", isAvailable=" + isAvailable + "]";
    }
    
}

class PlannedTimingAvailabilityEntityId implements Serializable{
    PlannedTimingConsultingEntity plannedTimingConsulting;
    TeachingStaffEntity teachingStaff;

    @Override
    public boolean equals(Object o){
        if (o == null) {
            return false;
        }
        if (o.getClass() != PlannedTimingAvailabilityEntityId.class) {
            return false;
        }
        PlannedTimingAvailabilityEntityId plannedTimingAvailabilityEntityId = (PlannedTimingAvailabilityEntityId) o;
        return this.teachingStaff.equals(plannedTimingAvailabilityEntityId.teachingStaff)
            && this.plannedTimingConsulting.equals(plannedTimingAvailabilityEntityId.plannedTimingConsulting);
    }

    @Override
    public int hashCode(){
        return this.teachingStaff.hashCode() + this.plannedTimingConsulting.hashCode();
    }
}