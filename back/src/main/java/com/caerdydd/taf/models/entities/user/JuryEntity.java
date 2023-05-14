package com.caerdydd.taf.models.entities.user;

import java.io.Serializable;
import java.util.List;

import java.io.Serializable;

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
public class JuryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJury;
    
    @OneToOne
    @JoinColumn(name = "id_ts1")
    private TeachingStaffEntity ts1;

    @OneToOne
    @JoinColumn(name = "id_ts2")
    private TeachingStaffEntity ts2;

    @OneToMany
    @JoinColumn(name = "id_jury")
    private List<PresentationEntity> presentations;


    public JuryEntity() {
    }

    public JuryEntity(Integer idJury) {
        this.idJury = idJury;
    }

    public JuryEntity(TeachingStaffEntity ts1, TeachingStaffEntity ts2) {
        this.ts1 = ts1;
        this.ts2 = ts2;
    }


}
