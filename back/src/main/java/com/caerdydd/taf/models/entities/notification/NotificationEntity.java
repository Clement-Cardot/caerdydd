package com.caerdydd.taf.models.entities.notification;

import java.io.Serializable;

import javax.persistence.*;

import com.caerdydd.taf.models.entities.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class NotificationEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String message;
    private String link;
    @Column(name = "is_read")
    private Boolean isRead = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    public NotificationEntity() {
    }

    public NotificationEntity(String message, String link, UserEntity user, Boolean isRead) {
        this.message = message;
        this.link = link;
        this.user = user;
        this.isRead = isRead;
    }

    public NotificationEntity(Integer id, String message, String link, UserEntity user, Boolean isRead) {
        this.id = id;
        this.message = message;
        this.link = link;
        this.user = user;
        this.isRead = isRead;
    }
}
