package com.caerdydd.taf.services.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.caerdydd.taf.models.dto.consulting.ConsultingDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingConsultingDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class ConsultingRulesTest {

    @InjectMocks
    private ConsultingRules consultingRules;
    
    @Test
    void testCheckUserIsOwnerOfAvailability_True() {
        // Prepare Input
        TeachingStaffDTO teachingStaffDTO = new TeachingStaffDTO(
            new UserDTO(
                1,
                "fistname",
                "lastname",
                "login",
                "password",
                "email",
                null
            )
        );
        PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO = new PlannedTimingAvailabilityDTO();
        plannedTimingAvailabilityDTO.setTeachingStaff(teachingStaffDTO);

        // Call method to test
        try {
            consultingRules.checkUserIsOwnerOfAvailability(teachingStaffDTO, plannedTimingAvailabilityDTO);
        } catch (CustomRuntimeException e) {
            fail(e);
        }
    }

    @Test
    void testCheckUserIsOwnerOfAvailability_False() {
        // Prepare Input
        TeachingStaffDTO teachingStaffDTO1 = new TeachingStaffDTO(
            new UserDTO(
                1,
                "fistname",
                "lastname",
                "login",
                "password",
                "email",
                null
            )
        );

        TeachingStaffDTO teachingStaffDTO2 = new TeachingStaffDTO(
            new UserDTO(
                2,
                "fistname",
                "lastname",
                "login",
                "password",
                "email",
                null
            )
        );

        PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO = new PlannedTimingAvailabilityDTO();
        plannedTimingAvailabilityDTO.setTeachingStaff(teachingStaffDTO2);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingRules.checkUserIsOwnerOfAvailability(teachingStaffDTO1, plannedTimingAvailabilityDTO);
        });
            
        // Verify the result
        assertEquals(CustomRuntimeException.USER_IS_NOT_OWNER_OF_AVAILABILITY, exception.getMessage());
    }

    @Test
    void testCheckPlannedTimingIsNotInPast_True() {
        // Prepare Input
        PlannedTimingConsultingDTO plannedTimingConsultingDTO = new PlannedTimingConsultingDTO();
        plannedTimingConsultingDTO.setDatetimeEnd(LocalDateTime.now().plusDays(1));
        PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO = new PlannedTimingAvailabilityDTO();
        plannedTimingAvailabilityDTO.setPlannedTimingConsulting(plannedTimingConsultingDTO);

        // Call method to test
        try {
            consultingRules.checkPlannedTimingIsNotInPast(plannedTimingAvailabilityDTO);
        } catch (CustomRuntimeException e) {
            fail(e);
        }
    }

    @Test
    void testCheckPlannedTimingIsNotInPast_False() {
        // Prepare Input
        PlannedTimingConsultingDTO plannedTimingConsultingDTO = new PlannedTimingConsultingDTO();
        plannedTimingConsultingDTO.setDatetimeEnd(LocalDateTime.now().minusDays(1));
        PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO = new PlannedTimingAvailabilityDTO();
        plannedTimingAvailabilityDTO.setPlannedTimingConsulting(plannedTimingConsultingDTO);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingRules.checkPlannedTimingIsNotInPast(plannedTimingAvailabilityDTO);
        });
            
        // Verify the result
        assertEquals(CustomRuntimeException.PLANNED_TIMING_IS_IN_PAST, exception.getMessage());
    }

    @Test
    void testCheckTeachingStaffIsAvailable_True() {
        // Prepare Input
        PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO = new PlannedTimingAvailabilityDTO();

        // Call method to test
        try {
            consultingRules.checkTeachingStaffIsAvailable(plannedTimingAvailabilityDTO);
        } catch (CustomRuntimeException e) {
            fail(e);
        }
    }

    @Test
    void testCheckTeachingStaffIsAvailable_False() {
        // Prepare Input
        PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO = new PlannedTimingAvailabilityDTO();
        plannedTimingAvailabilityDTO.setIsAvailable(false);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingRules.checkTeachingStaffIsAvailable(plannedTimingAvailabilityDTO);
        });
            
        // Verify the result
        assertEquals(CustomRuntimeException.TEACHING_STAFF_IS_NOT_AVAILABLE, exception.getMessage());
    }


    @Test
    void testCheckConsultingIsNotInPast_True() {
        // Prepare Input
        ConsultingDTO consultingDTO = new ConsultingDTO();
        PlannedTimingConsultingDTO plannedTimingConsultingDTO = new PlannedTimingConsultingDTO();
        plannedTimingConsultingDTO.setDatetimeEnd(LocalDateTime.now().plusDays(1));
        consultingDTO.setPlannedTimingConsulting(plannedTimingConsultingDTO);

        // Call method to test
        try {
            consultingRules.checkConsultingIsNotInPast(consultingDTO);
        } catch (CustomRuntimeException e) {
            fail(e);
        }
    }

    @Test
    void testCheckConsultingIsNotInPast_False() {
        // Prepare Input
        ConsultingDTO consultingDTO = new ConsultingDTO();
        PlannedTimingConsultingDTO plannedTimingConsultingDTO = new PlannedTimingConsultingDTO();
        plannedTimingConsultingDTO.setDatetimeEnd(LocalDateTime.now().minusDays(1));
        consultingDTO.setPlannedTimingConsulting(plannedTimingConsultingDTO);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            consultingRules.checkConsultingIsNotInPast(consultingDTO);
        });
            
        // Verify the result
        assertEquals(CustomRuntimeException.CONSULTING_IS_IN_PAST, exception.getMessage());
    }


    @Test
    void testCheckTeachingStaffIsAvailableForConsulting_True() {
        // Prepare Input
        TeachingStaffDTO teachingStaffDTO = new TeachingStaffDTO(
            new UserDTO(
                1,
                "fistname",
                "lastname",
                "login",
                "password",
                "email",
                null
            )
        );
        ConsultingDTO consultingDTO = new ConsultingDTO();
        PlannedTimingConsultingDTO plannedTimingConsultingDTO = new PlannedTimingConsultingDTO();
        PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO = new PlannedTimingAvailabilityDTO();
        plannedTimingAvailabilityDTO.setTeachingStaff(teachingStaffDTO);
        plannedTimingConsultingDTO.setTeachingStaffAvailabilities(Arrays.asList(plannedTimingAvailabilityDTO));
        consultingDTO.setPlannedTimingConsulting(plannedTimingConsultingDTO);

        // Call method to test
        try {
            consultingRules.checkTeachingStaffIsAvailableForConsulting(teachingStaffDTO, consultingDTO);
        } catch (CustomRuntimeException e) {
            fail(e);
        }
    }




    @Test
void testCheckPlannedTimingIsNotAlreadyTaken_True() {
    // Prepare Input
    PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO = new PlannedTimingAvailabilityDTO();

    // Call method to test
    try {
        consultingRules.checkPlannedTimingIsNotAlreadyTaken(plannedTimingAvailabilityDTO);
    } catch (CustomRuntimeException e) {
        fail(e);
    }
}

@Test
void testCheckPlannedTimingIsNotAlreadyTaken_False() {
    // Prepare Input
    PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO = new PlannedTimingAvailabilityDTO();
    plannedTimingAvailabilityDTO.setConsulting(new ConsultingDTO());

    // Call method to test
    CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
        consultingRules.checkPlannedTimingIsNotAlreadyTaken(plannedTimingAvailabilityDTO);
    });
        
    // Verify the result
    assertEquals(CustomRuntimeException.PLANNED_TIMING_IS_ALREADY_TAKEN, exception.getMessage());
}

@Test
void testCheckConsultingIsNotAlreadyTaken_True() {
    // Prepare Input
    ConsultingDTO consultingDTO = new ConsultingDTO();

    // Call method to test
    try {
        consultingRules.checkConsultingIsNotAlreadyTaken(consultingDTO);
    } catch (CustomRuntimeException e) {
        fail(e);
    }
}



@Test
void testCheckDemandIsMadeOnTime_True() {
    // Prepare Input
    ConsultingDTO consultingDTO = new ConsultingDTO();
    PlannedTimingConsultingDTO plannedTimingConsultingDTO = new PlannedTimingConsultingDTO();
    plannedTimingConsultingDTO.setDatetimeEnd(LocalDateTime.now().plusDays(3));
    consultingDTO.setPlannedTimingConsulting(plannedTimingConsultingDTO);

    // Call method to test
    try {
        consultingRules.checkDemandIsMadeOnTime(consultingDTO);
    } catch (CustomRuntimeException e) {
        fail(e);
    }
}

@Test
void testCheckDemandIsMadeOnTime_False() {
    // Prepare Input
    ConsultingDTO consultingDTO = new ConsultingDTO();
    PlannedTimingConsultingDTO plannedTimingConsultingDTO = new PlannedTimingConsultingDTO();
    plannedTimingConsultingDTO.setDatetimeEnd(LocalDateTime.now().plusDays(1));
    consultingDTO.setPlannedTimingConsulting(plannedTimingConsultingDTO);

    // Call method to test
    CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
        consultingRules.checkDemandIsMadeOnTime(consultingDTO);
    });
        
    // Verify the result
    assertEquals(CustomRuntimeException.DEMAND_IS_MADE_TOO_LATE, exception.getMessage());
}


}
