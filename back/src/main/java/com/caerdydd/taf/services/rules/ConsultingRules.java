package com.caerdydd.taf.services.rules;

import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

import com.caerdydd.taf.models.dto.consulting.ConsultingDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@Component
public class ConsultingRules {

	public void checkUserIsOwnerOfAvailability(TeachingStaffDTO teachingStaffDTO, PlannedTimingAvailabilityDTO availability) throws CustomRuntimeException{
        if (!availability.getTeachingStaff().getIdUser().equals(teachingStaffDTO.getIdUser())){
            throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY);
        }
    }

    public void checkPlannedTimingIsNotInPast(PlannedTimingAvailabilityDTO plannedTimingAvailability) throws CustomRuntimeException{
        if (plannedTimingAvailability.getPlannedTimingConsulting().getDatetimeEnd().isBefore(LocalDateTime.now())){
            throw new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_IN_PAST);
        }
    }

    public void checkPlannedTimingIsNotAlreadyTaken(PlannedTimingAvailabilityDTO plannedTimingAvailability) throws CustomRuntimeException{
        if (plannedTimingAvailability.getConsulting() != null){
            throw new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_IS_ALREADY_TAKEN);
        }
    }

    public void checkConsultingIsNotInPast(ConsultingDTO consulting) throws CustomRuntimeException{
        if (consulting.getPlannedTimingConsulting().getDatetimeEnd().isBefore(LocalDateTime.now())){
            throw new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_IN_PAST);
        }
    }

    public void checkConsultingIsNotAlreadyTaken(ConsultingDTO consulting) throws CustomRuntimeException{
        if (consulting.getPlannedTimingAvailability() != null){
            throw new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_ALREADY_TAKEN);
        }
    }

    
}
