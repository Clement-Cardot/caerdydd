package com.caerdydd.taf.services.rules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.caerdydd.taf.models.dto.project.PresentationDTO;
import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.entities.project.PresentationEntity;
import com.caerdydd.taf.models.entities.project.ProjectEntity;
import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.PresentationRepository;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PresentationServiceRulesTest {

    @InjectMocks
    private PresentationServiceRule presentationServiceRule;
    
    @Mock
    private JuryRepository juryRepository;
    
    @Mock
    private ProjectRepository projectRepository;
    
    @Mock
    private PresentationRepository presentationRepository;
    
    @Test
    void testCheckJuryExists_True() {
        // Prepare Input
        Integer idJury = 1;
        JuryEntity juryEntity = new JuryEntity();
        when(juryRepository.findById(idJury)).thenReturn(Optional.of(juryEntity));

        // Call method to test
        try {
            presentationServiceRule.checkJuryExists(idJury);
        } catch (CustomRuntimeException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void testCheckJuryExists_False() {
        // Prepare Input
        Integer idJury = 1;
        when(juryRepository.findById(idJury)).thenReturn(Optional.empty());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrows(CustomRuntimeException.class, () -> {
            presentationServiceRule.checkJuryExists(idJury);
        });

        // Verify the result
        Assertions.assertEquals(CustomRuntimeException.JURY_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testCheckProjectExists_True() {
        // Prepare Input
        Integer idProject = 1;
        ProjectEntity projectEntity = new ProjectEntity();
        when(projectRepository.findById(idProject)).thenReturn(Optional.of(projectEntity));

        // Call method to test
        try {
            presentationServiceRule.checkProjectExists(idProject);
        } catch (CustomRuntimeException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void testCheckProjectExists_False() {
        // Prepare Input
        Integer idProject = 1;
        when(projectRepository.findById(idProject)).thenReturn(Optional.empty());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrows(CustomRuntimeException.class, () -> {
            presentationServiceRule.checkProjectExists(idProject);
        });

        // Verify the result
        Assertions.assertEquals(CustomRuntimeException.PROJECT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testCheckTeachingStaffAvailability_True() {
        // Prepare Input
        Integer idJury = 1;
        LocalDateTime begin = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        List<TeachingStaffEntity> teachingStaffMembers = new ArrayList<>();
        TeachingStaffEntity teachingStaffEntity = new TeachingStaffEntity();
        teachingStaffEntity.setIdUser(1);
        teachingStaffMembers.add(teachingStaffEntity);
        when(juryRepository.findTeachingStaffMembers(idJury)).thenReturn(teachingStaffMembers);
        when(presentationRepository.findTeachingStaffPresentationsInTimeframe(teachingStaffEntity.getIdUser(), end, begin)).thenReturn(new ArrayList<>());

        // Call method to test
        try {
            presentationServiceRule.checkTeachingStaffAvailability(idJury, begin, end);
        } catch (CustomRuntimeException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void testCheckTeachingStaffAvailability_False() {
        // Prepare Input
        Integer idJury = 1;
        LocalDateTime begin = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        List<TeachingStaffEntity> teachingStaffMembers = new ArrayList<>();
        TeachingStaffEntity teachingStaffEntity = new TeachingStaffEntity();
        teachingStaffEntity.setIdUser(1);
        teachingStaffMembers.add(teachingStaffEntity);
        when(juryRepository.findTeachingStaffMembers(idJury)).thenReturn(teachingStaffMembers);
        List<PresentationEntity> presentations = new ArrayList<>();
        presentations.add(new PresentationEntity());
        when(presentationRepository.findTeachingStaffPresentationsInTimeframe(teachingStaffEntity.getIdUser(), end, begin)).thenReturn(presentations);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrows(CustomRuntimeException.class, () -> {
            presentationServiceRule.checkTeachingStaffAvailability(idJury, begin, end);
        });

        // Verify the result
        Assertions.assertEquals(CustomRuntimeException.TEACHING_STAFF_NOT_AVAILABLE, exception.getMessage());
    }

    @Test
    void testCheckPresentationTimeframe_True() {
        // Prepare Input
        LocalDateTime begin = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);

        // Call method to test
        try {
            presentationServiceRule.checkPresentationTimeframe(begin, end);
        } catch (CustomRuntimeException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void testCheckPresentationTimeframe_False() {
        // Prepare Input
        LocalDateTime begin = LocalDateTime.now().plusDays(2);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrows(CustomRuntimeException.class, () -> {
            presentationServiceRule.checkPresentationTimeframe(begin, end);
        });

        // Verify the result
        Assertions.assertEquals(CustomRuntimeException.PRESENTATION_END_BEFORE_BEGIN, exception.getMessage());
    }

    @Test
    public void checkDateBeginPassed_DateBeforeNow_ThrowsCustomRuntimeException() {
        LocalDateTime dateBegin = LocalDateTime.now().plusHours(1);
        assertThrows(CustomRuntimeException.class, () -> presentationServiceRule.checkDateBeginPassed(dateBegin));
    }

    @Test
    public void checkDateBeginPassed_DateAfterNow_NoExceptionThrown() throws CustomRuntimeException {
        LocalDateTime dateBegin = LocalDateTime.now().minusHours(1);
        assertDoesNotThrow(() -> presentationServiceRule.checkDateBeginPassed(dateBegin));
    }

    @Test
    public void checkDateEndPassed_DateBeforeNow_ThrowsCustomRuntimeException() {
        LocalDateTime dateEnd = LocalDateTime.now().plusHours(1);
        assertThrows(CustomRuntimeException.class, () -> presentationServiceRule.checkDateEndPassed(dateEnd));
    }

    @Test
    public void checkDateEndPassed_DateAfterNow_NoExceptionThrown() throws CustomRuntimeException {
        LocalDateTime dateEnd = LocalDateTime.now().minusHours(1);
        assertDoesNotThrow(() -> presentationServiceRule.checkDateEndPassed(dateEnd));
    }


        @Test
    void testCheckPresentationDoesNotExist_True() throws CustomRuntimeException {
        // Prepare Input
        ProjectDTO projectDTO = new ProjectDTO();
        List<PresentationDTO> presentations = new ArrayList<>();
        presentations.add(new PresentationDTO(1, "type1"));
        projectDTO.setPresentations(presentations);

        // Call method to test
        presentationServiceRule.checkPresentationDoesNotExist("type2", projectDTO);
    }

    @Test
    void testCheckPresentationDoesNotExist_False() {
        // Prepare Input
        ProjectDTO projectDTO = new ProjectDTO();
        List<PresentationDTO> presentations = new ArrayList<>();
        presentations.add(new PresentationDTO(1, "type1"));
        projectDTO.setPresentations(presentations);

        // Call method to test and Verify the result
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            presentationServiceRule.checkPresentationDoesNotExist("type1", projectDTO);
        });
        assertEquals(CustomRuntimeException.PRESENTATION_ALREADY_EXISTS, exception.getMessage());
    }

    @Test
    void testCheckDateIsNotPassed_True() throws CustomRuntimeException {
        // Prepare Input
        LocalDateTime begin = LocalDateTime.now().plusDays(1);

        // Call method to test
        presentationServiceRule.checkDateIsNotPassed(begin);
    }

    @Test
    void testCheckDateIsNotPassed_False() {
        // Prepare Input
        LocalDateTime begin = LocalDateTime.now().minusDays(1);

        // Call method to test and Verify the result
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            presentationServiceRule.checkDateIsNotPassed(begin);
        });
        assertEquals(CustomRuntimeException.PRESENTATION_ALREADY_PASSED, exception.getMessage());
    }

    @Test
    void testCheckIntermediatePresentationIsCreated_True() throws CustomRuntimeException {
        // Prepare Input
        ProjectDTO projectDTO = new ProjectDTO();
        List<PresentationDTO> presentations = new ArrayList<>();
        presentations.add(new PresentationDTO(1, "intermediate"));
        projectDTO.setPresentations(presentations);

        // Call method to test
        presentationServiceRule.checkIntermediatePresentationIsCreated(projectDTO);
    }

    @Test
    void testCheckIntermediatePresentationIsCreated_False() {
        // Prepare Input
        ProjectDTO projectDTO = new ProjectDTO();
        List<PresentationDTO> presentations = new ArrayList<>();
        presentations.add(new PresentationDTO(1, "notIntermediate"));
        projectDTO.setPresentations(presentations);

        // Call method to test and Verify the result
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            presentationServiceRule.checkIntermediatePresentationIsCreated(projectDTO);
        });
        assertEquals(CustomRuntimeException.INTERMEDIATE_PRESENTATION_NOT_CREATED, exception.getMessage());
    }

    


}
