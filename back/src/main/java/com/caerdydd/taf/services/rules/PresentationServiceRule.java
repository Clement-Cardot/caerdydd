package com.caerdydd.taf.services.rules;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.entities.project.PresentationEntity;
import com.caerdydd.taf.models.entities.project.ProjectEntity;
import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.PresentationRepository;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PresentationServiceRule {
    @Autowired
    private JuryRepository juryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    PresentationRepository presentationRepository;

    @Autowired
    ModelMapper modelMapper;

    public void checkJuryExists(Integer idJury) throws CustomRuntimeException {
        Optional<JuryEntity> optionalJury = juryRepository.findById(idJury);
        if (optionalJury.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.JURY_NOT_FOUND);
        }
    }
    
    public void checkProjectExists(Integer idProject) throws CustomRuntimeException {
        Optional<ProjectEntity> optionalProject = projectRepository.findById(idProject);
        if (optionalProject.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.PROJECT_NOT_FOUND);
        }
    }


    public void checkTeachingStaffAvailability(Integer idJury, LocalDateTime begin, LocalDateTime end) throws CustomRuntimeException {
        List<TeachingStaffEntity> teachingStaffMembers = juryRepository.findTeachingStaffMembers(idJury);
    
        for (TeachingStaffEntity teachingStaffMember : teachingStaffMembers) {
            List<PresentationEntity> presentations = presentationRepository.findTeachingStaffPresentationsInTimeframe(teachingStaffMember.getIdUser(), end, begin);
    
            if (!presentations.isEmpty()) {
                throw new CustomRuntimeException(CustomRuntimeException.TEACHING_STAFF_NOT_AVAILABLE);
            }
        }
    }
    
    public void checkPresentationTimeframe(LocalDateTime begin, LocalDateTime end) throws CustomRuntimeException {
        if (end.isBefore(begin)) {
            throw new CustomRuntimeException(CustomRuntimeException.PRESENTATION_END_BEFORE_BEGIN);
        }
    }

    public void checkDateBeginPassed(LocalDateTime dateBegin) throws CustomRuntimeException{
        if(LocalDateTime.now().isBefore(dateBegin)){
            throw new CustomRuntimeException(CustomRuntimeException.PRESENTATION_DID_NOT_BEGIN);
        }
    }

    public void checkDateEndPassed(LocalDateTime dateEnd) throws CustomRuntimeException{
        if(LocalDateTime.now().isBefore(dateEnd)){
            throw new CustomRuntimeException(CustomRuntimeException.PRESENTATION_DID_NOT_FINISH);
        }
    }
}
