package com.caerdydd.taf.models.entities.consulting;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "planned_timing_availability")
public class PlannedTimingAvailabilityEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlannedTimingAvailability;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_planned_timing_consulting", referencedColumnName = "idPlannedTimingConsulting", nullable = false)
    private PlannedTimingConsultingEntity plannedTimingConsulting;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_ts", referencedColumnName = "idUser", nullable = false)
    private TeachingStaffEntity teachingStaff;

    @OneToOne(mappedBy = "plannedTimingAvailability")
    private ConsultingEntity consulting;

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