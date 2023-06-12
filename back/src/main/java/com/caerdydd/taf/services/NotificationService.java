package com.caerdydd.taf.services;

import java.util.List;
import java.lang.reflect.Type;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.caerdydd.taf.models.dto.notification.NotificationDTO;
import com.caerdydd.taf.models.entities.notification.NotificationEntity;
import com.caerdydd.taf.repositories.NotificationRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.NotificationServiceRules;
import com.caerdydd.taf.services.rules.UserServiceRules;

@Service
@Transactional
public class NotificationService {
    
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotificationServiceRules notificationServiceRules;

    @Autowired
    private UserServiceRules userServiceRules;

    public NotificationDTO createNotification(NotificationDTO notification) throws CustomRuntimeException {

        //Vérifier que l'utilisateur existe
        userServiceRules.checkUserExists(notification.getUser().getId());

        NotificationEntity notificationEntity = modelMapper.map(notification, NotificationEntity.class);
        NotificationEntity savedNotification = notificationRepository.save(notificationEntity);
        return modelMapper.map(savedNotification, NotificationDTO.class);
    }

    public NotificationDTO changeStatusRead(Integer id) throws CustomRuntimeException {

        //Vérifier que la notification existe
        notificationServiceRules.checkNotificationExists(id);

        NotificationEntity notification = notificationRepository.findById(id).get();
        notification.setIsRead(true);
        NotificationEntity updatedNotification = notificationRepository.save(notification);
        return modelMapper.map(updatedNotification, NotificationDTO.class);
    }
    

        public List<NotificationDTO> getNotificationsByUserId(Integer idUser) throws CustomRuntimeException {

        //Vérifier que l'utilisateur existe
        userServiceRules.checkUserExists(idUser);

        List<NotificationEntity> notificationEntities = notificationRepository.findByUserId(idUser);
        Type listType = new TypeToken<List<NotificationDTO>>(){}.getType();
        return modelMapper.map(notificationEntities, listType);
    }

    public List<NotificationDTO> getAllNotifications() {
        List<NotificationEntity> notificationEntities = notificationRepository.findAll();
        Type listType = new TypeToken<List<NotificationDTO>>(){}.getType();
        return modelMapper.map(notificationEntities, listType);
    }


}


