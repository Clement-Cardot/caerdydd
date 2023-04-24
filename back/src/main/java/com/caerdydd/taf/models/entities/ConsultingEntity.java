package com.caerdydd.taf.models.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
        private LocalDateTime  datetimeBegin;
        private LocalDateTime  datetimeEnd;
        private String speciality;
        private String notes;
        private Boolean isValidated;
        private Boolean isReserved;
        
        @ManyToOne
        @JoinColumn(name = "id_team")
        private TeamEntity team;

        public ConsultingEntity() {
        }

        public ConsultingEntity(Integer idConsulting, LocalDateTime datetimeBegin, LocalDateTime datetimeEnd){
                this.idConsulting = idConsulting;
                this.datetimeBegin = datetimeBegin;
                this.datetimeEnd = datetimeEnd;
                this.isValidated = false;
                this.isReserved = false;
        }

        @Override
        public String toString() {
                return "ConsultingEntity [idConsulting=" + idConsulting + ", datetimeBegin=" + datetimeBegin
                                + ", datetimeEnd=" + datetimeEnd + ", speciality=" + speciality + ", notes=" + notes
                                + ", isValidated=" + isValidated + ", isReserved=" + isReserved + ", team=" + team + "]";
        }
        
}
