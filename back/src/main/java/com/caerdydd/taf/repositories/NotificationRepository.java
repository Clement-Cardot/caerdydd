package com.caerdydd.taf.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.caerdydd.taf.models.entities.notification.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Integer> {
    List<NotificationEntity> findByUserId(Integer idUser);
}
