package com.caerdydd.taf.services.rules;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class JuryServiceRulesTest {

    @InjectMocks
    private JuryServiceRules juryServiceRules;

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
    void testCheckJuryExists_JuryDoNotExist(){
        
        TeachingStaffEntity ts1 = new TeachingStaffEntity(new UserEntity(1, "firstname", "lastname", "login", "password", "email", null));
        TeachingStaffEntity ts2 = new TeachingStaffEntity(new UserEntity(2, "firstname", "lastname", "login", "password", "email", null));

        when(teachingStaffRepositoryMock.findById(1)).thenReturn(Optional.of(ts1));
        when(teachingStaffRepositoryMock.findById(2)).thenReturn(Optional.of(ts2));
        when(juryRepositoryMock.findByTs1AndTs2(ts1, ts2)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> juryServiceRules.checkJuryExists(1, 2));
    }

    @Test
    void testCheckJuryExists_JuryAlreadyExists() throws CustomRuntimeException {
        // mock de userRepository
        TeachingStaffEntity ts1 = new TeachingStaffEntity(new UserEntity(1, "firstname", "lastname", "login", "password", "email", null));
        TeachingStaffEntity ts2 = new TeachingStaffEntity(new UserEntity(2, "firstname", "lastname", "login", "password", "email", null));

        when(teachingStaffRepositoryMock.findById(1)).thenReturn(Optional.of(ts1));
        when(teachingStaffRepositoryMock.findById(2)).thenReturn(Optional.of(ts2));

        // mock de juryRepository
        JuryEntity jury = new JuryEntity();
        when(juryRepositoryMock.findByTs1AndTs2(ts1, ts2)).thenReturn(Optional.of(jury));

        // appel de la méthode et vérification de l'exception
        assertThrows(CustomRuntimeException.class, () -> {
            juryServiceRules.checkJuryExists(1, 2);
        }, CustomRuntimeException.JURY_ALREADY_EXISTS);
    }
}