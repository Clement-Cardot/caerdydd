package com.caerdydd.taf.models.entities.consulting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.caerdydd.taf.models.entities.project.TeamEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;

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

        @ManyToOne
        @JoinColumn(name = "id_planned_timing_consulting")
        private PlannedTimingConsultingEntity plannedTimingConsulting;

        @ManyToOne
        @JoinColumn(name = "id_team")
        private TeamEntity team;

        @ManyToOne
        @JoinColumn(name = "id_ts")
        private TeachingStaffEntity ts;

        public ConsultingEntity(){
        }

        @Override
        public String toString() {
                return "ConsultingEntity [idConsulting=" + idConsulting + ", speciality=" + speciality + ", notes=" + notes
                                + ", isValidated=" + isValidated + ", isReserved=" + isReserved + ", team=" + team + "]";
        }
        
}
