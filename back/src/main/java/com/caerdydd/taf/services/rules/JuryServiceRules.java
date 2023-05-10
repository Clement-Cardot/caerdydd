package com.caerdydd.taf.services.rules;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.entities.JuryEntity;
import com.caerdydd.taf.models.entities.UserEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.UserRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@Component
public class JuryServiceRules {
    @Autowired
    JuryRepository juryRepository;

    @Autowired
    UserRepository userRepository;
    
    public void checkDifferentTeachingStaff(Integer ts1, Integer ts2) throws CustomRuntimeException {
        if (ts1.equals(ts2)) {
            throw new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_ARE_THE_SAME);
        }
    }

    public void checkJuryExists(Integer idTs1, Integer idTs2) throws CustomRuntimeException {
        Optional<UserEntity> optTs1 = userRepository.findById(idTs1);
        Optional<UserEntity> optTs2 = userRepository.findById(idTs2);
        
        UserEntity ts1 = optTs1.orElseThrow(() -> new IllegalArgumentException("UserEntity not found for idTs1: " + idTs1));
        UserEntity ts2 = optTs2.orElseThrow(() -> new IllegalArgumentException("UserEntity not found for idTs2: " + idTs2));

        Optional<JuryEntity> optionalJury = Optional.empty();
        try {
            optionalJury = juryRepository.findByTs1AndTs2(ts1, ts2);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        if(optionalJury.isPresent()){
            throw new CustomRuntimeException(CustomRuntimeException.JURY_ALREADY_EXISTS);
        }
    }
}
