package com.caerdydd.taf.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.caerdydd.taf.models.entities.project.PresentationEntity;
import com.caerdydd.taf.models.entities.project.ProjectEntity;

public interface PresentationRepository extends JpaRepository<PresentationEntity, Integer> {
    List<PresentationEntity> findByProject(ProjectEntity project);

    @Query("SELECT p FROM PresentationEntity p WHERE p.jury.idJury IN (SELECT j.idJury FROM JuryEntity j WHERE j.ts1.idUser = :idTeachingStaff OR j.ts2.idUser = :idTeachingStaff) AND p.datetimeBegin < :end AND p.datetimeEnd > :begin")
    List<PresentationEntity> findTeachingStaffPresentationsInTimeframe(@Param("idTeachingStaff") Integer idTeachingStaff, @Param("end") LocalDateTime end, @Param("begin") LocalDateTime begin);

    @Query("SELECT p FROM PresentationEntity p JOIN p.jury j WHERE j.ts1.idUser = :staffId OR j.ts2.idUser = :staffId")
    List<PresentationEntity> findByTeachingStaff(@Param("staffId") Integer staffId);
}