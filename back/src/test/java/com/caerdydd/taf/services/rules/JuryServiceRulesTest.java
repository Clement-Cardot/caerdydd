package com.caerdydd.taf.services.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.models.entities.user.UserEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.TeachingStaffRepository;
import com.caerdydd.taf.repositories.UserRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.JuryService;

@ExtendWith(MockitoExtension.class)
public class JuryServiceRulesTest {

    @InjectMocks
    private JuryServiceRules juryServiceRules;

    @Mock
    private JuryService juryService;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    TeachingStaffRepository teachingStaffRepositoryMock;

    @Mock
    JuryRepository juryRepositoryMock;

    @Test
    void checkDifferentTeachingStaff_DifferentTeachingStaff(){
        Integer teachingStaffId = 1;
        Integer otherTeachingStaffId = 2;
        assertDoesNotThrow(() -> juryServiceRules.checkDifferentTeachingStaff(teachingStaffId, otherTeachingStaffId));
    }

    @Test
    void checkDifferentTeachingStaff_SameTeachingStaff(){
        Integer teachingStaffId = 1;
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            juryServiceRules.checkDifferentTeachingStaff(teachingStaffId, teachingStaffId);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.TEACHING_STAFF_ARE_THE_SAME, exception.getMessage());
    }

    @Test
    public void testCheckJuryExists_JuryDoNotExist() throws CustomRuntimeException{
        Integer idTs1 = 1;
        Integer idTs2 = 2;
        UserEntity ts1 = new UserEntity();
        ts1.setId(idTs1);
        UserEntity ts2 = new UserEntity();
        ts2.setId(idTs2);

        assertDoesNotThrow(() -> juryService.checkJuryExists(idTs1, idTs2));
    }
}