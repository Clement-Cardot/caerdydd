package com.caerdydd.taf.models.entities;

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
public class JuryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idJury;
    
    @OneToOne
    @JoinColumn(name = "id_ts1")
    private UserEntity juryMemberLD;

    @OneToOne
    @JoinColumn(name = "id_ts2")
    private UserEntity juryMemberCSS;


    public JuryEntity() {
    }

    public JuryEntity(UserEntity juryMemberLD, UserEntity juryMemberCSS) {
        this.juryMemberLD = juryMemberLD;
        this.juryMemberCSS = juryMemberCSS;
    }


}
