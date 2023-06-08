package com.caerdydd.taf.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.caerdydd.taf.models.dto.notification.NotificationDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    
    private static final Logger logger = LogManager.getLogger(NotificationController.class);
    private static final String UNEXPECTED_EXCEPTION = "Unexpected Exception : {}";

    @Autowired
    NotificationService notificationService;


    @PostMapping("/create")
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notification) {
        logger.info("Process request : Création de Notification");
        try {
            NotificationDTO savedNotification = notificationService.createNotification(notification);
            return new ResponseEntity<>(savedNotification, HttpStatus.CREATED);
        } catch (CustomRuntimeException e) {
            logger.warn(e.getMessage());
            switch (e.getMessage()) {
                case CustomRuntimeException.USER_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.SERVICE_ERROR:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<NotificationDTO> changeStatusRead(@PathVariable Integer id) {
        logger.info("Process request : Changement du statut de la Notification");
        try {
            NotificationDTO updatedNotification = notificationService.changeStatusRead(id);
            return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            logger.warn(e.getMessage());
            switch (e.getMessage()) {
                case CustomRuntimeException.NOTIFICATION_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.SERVICE_ERROR:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Integer id) {
        logger.info("Process request : Obtention de toutes les Notifications");
        try {
            List<NotificationDTO> userNotifications = notificationService.getNotificationsByUserId(id);
            return new ResponseEntity<>(userNotifications, HttpStatus.OK);
        } catch (CustomRuntimeException e) {
            logger.warn(e.getMessage());
            switch (e.getMessage()) {
                case CustomRuntimeException.USER_NOT_FOUND:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                case CustomRuntimeException.SERVICE_ERROR:
                    logger.warn(e.getMessage());
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                default:
                    logger.error(UNEXPECTED_EXCEPTION, e.getMessage());
                    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        logger.info("Process request : Obtention de toutes les Notifications de l'IdUser entré");
        List<NotificationDTO> allNotifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(allNotifications, HttpStatus.OK);
    }
}
