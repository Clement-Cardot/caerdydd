package com.caerdydd.taf.models.dto.notification;

import com.caerdydd.taf.models.dto.user.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {

    private Integer idNotification;
    private String message;
    private String link;
    private UserDTO user;
    private Boolean isRead;

    public NotificationDTO() {
    }

    public NotificationDTO(String message, String link, UserDTO user, Boolean isRead) {
        this.message = message;
        this.link = link;
        this.user = user;
        this.isRead = isRead;
    }

    public NotificationDTO(Integer idNotification, String message, String link, UserDTO user, Boolean isRead) {
        this.idNotification = idNotification;
        this.message = message;
        this.link = link;
        this.user = user;
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "NotificationDTO [idNotification=" + idNotification + ", message=" + message + ", link=" + link + ", idUser=" + user
                + ", isRead=" + isRead + "]";
    }
}
