package com.caerdydd.taf.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caerdydd.taf.models.dto.notification.NotificationDTO;
import com.caerdydd.taf.models.entities.notification.NotificationEntity;
import com.caerdydd.taf.repositories.NotificationRepository;

@Service
@Transactional
public class NotificationService {
    

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public NotificationDTO createNotification(NotificationDTO notification) {
        NotificationEntity notificationEntity = modelMapper.map(notification, NotificationEntity.class);
        NotificationEntity savedNotification = notificationRepository.save(notificationEntity);
        return modelMapper.map(savedNotification, NotificationDTO.class);
    }


}
