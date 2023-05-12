package com.caerdydd.taf.models.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "presentation")
public class PresentationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPresentation;
    private String type;
    private LocalDateTime dateTimeBegin;
    private LocalDateTime dateTimeEnd;
    private String room;
    private String jury1Notes;
    private String jury2Notes;

    @ManyToOne
    @JoinColumn(name = "id_jury")
    private JuryEntity jury;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private ProjectEntity project;

    public PresentationEntity() {
    }

    public PresentationEntity(Integer idPresentation) {
        this.idPresentation = idPresentation;
    }

    @Override
    public String toString() {
        return "PresentationEntity [idPresentation=" + idPresentation + ", type=" + type + ", dateTimeBegin="
                + dateTimeBegin + ", dateTimeEnd=" + dateTimeEnd + ", room=" + room + ", jury1Notes=" + jury1Notes
                + ", jury2Notes=" + jury2Notes + ", jury=" + jury + ", project=" + project + "]";
    }
    
}
