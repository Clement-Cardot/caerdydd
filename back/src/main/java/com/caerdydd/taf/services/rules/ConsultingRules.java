package com.caerdydd.taf.services.rules;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public void checkTeachingStaffIsAvailable(PlannedTimingAvailabilityDTO plannedTimingAvailability) throws CustomRuntimeException{
        if (Boolean.FALSE.equals(plannedTimingAvailability.getIsAvailable())){
            throw new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_IS_NOT_AVAILABLE);
        }
    }

    public void checkConsultingIsNotInPast(ConsultingDTO consultingDTO) throws CustomRuntimeException{
        if (consultingDTO.getPlannedTimingConsulting().getDatetimeEnd().isBefore(LocalDateTime.now())){
            throw new CustomRuntimeException(CustomRuntimeException.CONSULTING_IS_IN_PAST);
        }
    }

    public void checkTeachingStaffIsAvailableForConsulting(TeachingStaffDTO teachingStaff, ConsultingDTO consulting) throws CustomRuntimeException{

        Optional<PlannedTimingAvailabilityDTO> optAvailability = consulting.getPlannedTimingConsulting().getTeachingStaffAvailabilities().stream()
                .filter(availability -> availability.getTeachingStaff().getIdUser().equals(teachingStaff.getIdUser()))
                .findFirst();

        if (optAvailability.isPresent()){
            checkTeachingStaffIsAvailable(optAvailability.get());
        } else {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }
    
}
