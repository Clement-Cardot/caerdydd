package com.caerdydd.taf.services.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.TeachingStaffService;
import com.caerdydd.taf.services.TeamService;

@Component
public class JuryServiceRules {

    @Autowired
    TeachingStaffService teachingStaffService;
    
    public void checkDifferentTeachingStaff(Integer ts1, Integer ts2) throws CustomRuntimeException {
        if (ts1.equals(ts2)) {
            throw new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_ARE_THE_SAME);
        }
    }

    public void checkJuryMemberManageTeam(Integer idJury, TeamDTO team) throws CustomRuntimeException {
        Integer idJury1 = team.getProjectDev().getJury().getTs1().getIdUser();
        Integer idJury2 = team.getProjectDev().getJury().getTs2().getIdUser();
        if (!(idJury1.equals(idJury) || idJury2.equals(idJury))) {
            throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AUTHORIZED);
        }
    }
}
