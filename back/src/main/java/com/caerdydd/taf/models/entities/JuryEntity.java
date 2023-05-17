package com.caerdydd.taf.models.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
    private UserEntity ts1;

    @OneToOne
    @JoinColumn(name = "id_ts2")
    private UserEntity ts2;


    public JuryEntity() {
    }

    public JuryEntity(UserEntity ts1, UserEntity ts2) {
        this.ts1 = ts1;
        this.ts2 = ts2;
    }


}
