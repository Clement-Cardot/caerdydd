package com.caerdydd.taf.models.entities.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.caerdydd.taf.models.entities.project.PresentationEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "jury")
public class JuryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJury;
    
    @OneToOne
    @JoinColumn(name = "id_ts1")
    private UserEntity ts1;

    @OneToOne
    @JoinColumn(name = "id_ts2")
    private UserEntity ts2;

    @OneToMany
    @JoinColumn(name = "id_jury")
    private List<PresentationEntity> presentations;


    public JuryEntity() {
    }

    public JuryEntity(UserEntity ts1, UserEntity ts2) {
        this.ts1 = ts1;
        this.ts2 = ts2;
    }


}