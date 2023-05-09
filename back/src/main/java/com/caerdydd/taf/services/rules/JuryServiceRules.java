package com.caerdydd.taf.services.rules;

import java.util.List;
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
        if (ts1 == ts2) {
            throw new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_ARE_THE_SAME);
        }
    }

    public void checkJuryExists(Integer idTs1, Integer idTs2) throws CustomRuntimeException {
        /* 
        UserDTO ts1 = userService.getUserById(idTs1);
        UserDTO ts2 = userService.getUserById(idTs2);

        List<JuryEntity> result = juryRepository.findByTs1AndTs2(ts1, ts2);
        */
        
        Optional<UserEntity> optTs1 = userRepository.findById(idTs1);
        Optional<UserEntity> optTs2 = userRepository.findById(idTs2);
        
        UserEntity ts1 = optTs1.orElseThrow(() -> new IllegalArgumentException("UserEntity not found for idTs1: " + idTs1));
        UserEntity ts2 = optTs2.orElseThrow(() -> new IllegalArgumentException("UserEntity not found for idTs2: " + idTs2));

        List<JuryEntity> result = juryRepository.findByTs1AndTs2(ts1, ts2);

        if(!result.isEmpty()){
            throw new CustomRuntimeException(CustomRuntimeException.JURY_ALREADY_EXISTS);
        }
    }
}
