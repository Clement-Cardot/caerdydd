package com.caerdydd.taf.services.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caerdydd.taf.repositories.NotificationRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@Component
public class NotificationServiceRules {

    @Autowired
    NotificationRepository notificationRepository;

    public void checkNotificationExists(Integer id) throws CustomRuntimeException {
        if (!notificationRepository.existsById(id)) {
            throw new CustomRuntimeException(CustomRuntimeException.NOTIFICATION_NOT_FOUND);
        }
    }
    
    
}

