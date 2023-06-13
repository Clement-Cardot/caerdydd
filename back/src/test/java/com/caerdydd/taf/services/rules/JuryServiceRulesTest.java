package com.caerdydd.taf.services.rules;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.TeachingStaffRepository;
import com.caerdydd.taf.repositories.UserRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.JuryService;
import com.caerdydd.taf.services.TeamService;

@ExtendWith(MockitoExtension.class)
public class JuryServiceRulesTest {

    @InjectMocks
    private JuryServiceRules juryServiceRules;

    @Mock
    private JuryService juryService;

    @Mock
    private TeamService teamService;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    TeachingStaffRepository teachingStaffRepositoryMock;

    @Mock
    JuryRepository juryRepositoryMock;

    @Test
    void testCheckDifferentTeachingStaff_DifferentTeachingStaff(){
        Integer teachingStaffId = 1;
        Integer otherTeachingStaffId = 2;
        assertDoesNotThrow(() -> juryServiceRules.checkDifferentTeachingStaff(teachingStaffId, otherTeachingStaffId));
    }

    @Test
    void testCheckDifferentTeachingStaff_SameTeachingStaff(){
        Integer teachingStaffId = 1;
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            juryServiceRules.checkDifferentTeachingStaff(teachingStaffId, teachingStaffId);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.TEACHING_STAFF_ARE_THE_SAME, exception.getMessage());
    }

    @Test
    void testCheckJuryMemberManageTeam_Managing() throws CustomRuntimeException {
        Integer idJury = 1;
        Integer idTeam = 1;
        ProjectDTO mockedProject = new ProjectDTO(null, null);
        mockedProject.setJury(new JuryDTO(new TeachingStaffDTO(new UserDTO(1, null, null, null, null, null, null)), new TeachingStaffDTO(new UserDTO(2, null, null, null, null, null, null))));
        TeamDTO mockedTeam = new TeamDTO(1, null, mockedProject, null);

        when(teamService.getTeamById(any(Integer.class))).thenReturn(mockedTeam);

        assertDoesNotThrow(() -> juryServiceRules.checkJuryMemberManageTeam(idJury, idTeam));
    }

    @Test
    void testCheckJuryMemberManageTeam_NotManaging() throws CustomRuntimeException {
        Integer idJury = 3;
        Integer idTeam = 1;
        ProjectDTO mockedProject = new ProjectDTO(null, null);
        mockedProject.setJury(new JuryDTO(new TeachingStaffDTO(new UserDTO(1, null, null, null, null, null, null)), new TeachingStaffDTO(new UserDTO(2, null, null, null, null, null, null))));
        TeamDTO mockedTeam = new TeamDTO(1, null, mockedProject, null);

        when(teamService.getTeamById(any(Integer.class))).thenReturn(mockedTeam);

        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            juryServiceRules.checkJuryMemberManageTeam(idJury, idTeam);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.USER_IS_NOT_AUTHORIZED, exception.getMessage());
    }
}