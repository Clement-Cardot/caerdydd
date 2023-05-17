package com.caerdydd.taf.models.entities.consulting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caerdydd.taf.models.entities.project.TeamEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consulting")
public class ConsultingEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer idConsulting;
        private String speciality;
        private String notes;
        private Boolean isValidated = false;
        private Boolean isReserved = false;

        @OneToOne
        @JoinColumn(name = "id_planned_timing_availability")
        private PlannedTimingAvailabilityEntity plannedTimingAvailability;

        @ManyToOne
        @JoinColumn(name = "id_team")
        private TeamEntity team;

        public ConsultingEntity(){
        }

        @Override
        public String toString() {
                return "ConsultingEntity [idConsulting=" + idConsulting + ", speciality=" + speciality + ", notes=" + notes
                                + ", isValidated=" + isValidated + ", isReserved=" + isReserved + ", team=" + team + "]";
        }
        
}
