package com.caerdydd.taf.services.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.UserEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.UserRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeachingStaffService;

@Component
public class JuryServiceRules {

    @Autowired
    TeachingStaffService teachingStaffService;
    
    public void checkDifferentTeachingStaff(Integer ts1, Integer ts2) throws CustomRuntimeException {
        if (ts1.equals(ts2)) {
            throw new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_ARE_THE_SAME);
        }
    }
}
