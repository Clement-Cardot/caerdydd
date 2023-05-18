package com.caerdydd.taf.services.rules;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.TeachingStaffRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@Component
public class JuryServiceRules {
    @Autowired
    JuryRepository juryRepository;

    @Autowired
    TeachingStaffRepository teachingStaffRepository;
    
    public void checkDifferentTeachingStaff(Integer ts1, Integer ts2) throws CustomRuntimeException {
        if (ts1.equals(ts2)) {
            throw new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_ARE_THE_SAME);
        }
    }

    public void checkJuryExists(Integer idTs1, Integer idTs2) throws CustomRuntimeException {
        Optional<TeachingStaffEntity> optTs1 = teachingStaffRepository.findById(idTs1);
        Optional<TeachingStaffEntity> optTs2 = teachingStaffRepository.findById(idTs2);
        
        TeachingStaffEntity ts1 = optTs1.orElseThrow(() -> new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_NOT_FOUND));
        TeachingStaffEntity ts2 = optTs2.orElseThrow(() -> new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_NOT_FOUND));

        Optional<JuryEntity> optionalJury1 = Optional.empty();
        Optional<JuryEntity> optionalJury2 = Optional.empty();
        try {
            optionalJury1 = juryRepository.findByTs1AndTs2(ts1, ts2);
            optionalJury2 = juryRepository.findByTs1AndTs2(ts2, ts1);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        if(optionalJury1.isPresent() || optionalJury2.isPresent()){
            throw new CustomRuntimeException(CustomRuntimeException.JURY_ALREADY_EXISTS);
        }
    }
}
