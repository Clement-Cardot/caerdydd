package com.caerdydd.taf.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caerdydd.taf.models.entities.consulting.PlannedTimingAvailabilityEntity;

public interface PlannedTimingAvailabilityRepository extends JpaRepository<PlannedTimingAvailabilityEntity, Integer>{
  @Query("SELECT PTA FROM PlannedTimingAvailabilityEntity PTA WHERE PTA.plannedTimingConsulting.id = :idPlannedTimingConsulting AND PTA.teachingStaff.id = :idUser")
  Optional<PlannedTimingAvailabilityEntity> findByIdPlannedTimingConsultingAndIdUser(@Param("idPlannedTimingConsulting") Integer idPlannedTimingConsulting, @Param("idUser") Integer idUser);  

}