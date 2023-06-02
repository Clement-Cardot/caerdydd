package com.caerdydd.taf.models.entities.consulting;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.caerdydd.taf.models.entities.project.TeamEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consulting")
public class ConsultingEntity implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idConsulting;
        private String speciality;
        private String notes;

        @OneToOne
        @JoinColumn(name = "id_planned_timing_availability")
        private PlannedTimingAvailabilityEntity plannedTimingAvailability;

        @OneToOne
        @JoinColumn(name = "id_planned_timing_consulting")
        private PlannedTimingConsultingEntity plannedTimingConsulting;

        @ManyToOne
        @JoinColumn(name = "id_planned_timing_consulting")
        private PlannedTimingConsultingEntity plannedTimingConsulting;

        @ManyToOne
        @JoinColumn(name = "id_team")
        private TeamEntity team;

        @ManyToOne
        @JoinColumn(name = "id_ts")
        private TeachingStaffEntity teachingStaff;

        public ConsultingEntity(){
        }

        @Override
        public String toString() {
                return "ConsultingEntity [idConsulting=" + idConsulting + ", speciality=" + speciality + ", notes=" + notes
                        + ", team=" + team + "]";
        }
        
}
