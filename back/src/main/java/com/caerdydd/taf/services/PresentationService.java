package com.caerdydd.taf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.project.PresentationDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.entities.project.PresentationEntity;
import com.caerdydd.taf.models.entities.project.ProjectEntity;
import com.caerdydd.taf.models.entities.project.TeamEntity;
import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.repositories.PresentationRepository;
import com.caerdydd.taf.repositories.ProjectRepository;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.PresentationServiceRule;
import com.caerdydd.taf.services.rules.UserServiceRules;

@Service
@Transactional
public class PresentationService {
    private static final Logger logger = LogManager.getLogger(PresentationService.class);
    
    @Autowired
    private PresentationRepository presentationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PresentationServiceRule presentationServiceRule;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private JuryRepository juryRepository;

    @Autowired
    private UserServiceRules userServiceRules;

    @Autowired
    private TeamMemberService teamMemberService;

    public List<PresentationDTO> listAllPresentations() throws CustomRuntimeException {
        try {
            return presentationRepository.findAll().stream()
                    .map(presentation -> modelMapper.map(presentation, PresentationDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public PresentationDTO getPresentationById(Integer presentationId) throws CustomRuntimeException {
        Optional<PresentationEntity> optionalPresentation;
        try {
            optionalPresentation = presentationRepository.findById(presentationId);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        if (optionalPresentation.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.PRESENTATION_NOT_FOUND);
        }
        return modelMapper.map(optionalPresentation.get(), PresentationDTO.class);
    }

    public PresentationDTO savePresentation(PresentationDTO presentation) {
        PresentationEntity presentationEntity = modelMapper.map(presentation, PresentationEntity.class);
        PresentationEntity response = presentationRepository.save(presentationEntity);
        return modelMapper.map(response, PresentationDTO.class);
    }

    public PresentationDTO updatePresentation(PresentationDTO presentation) throws CustomRuntimeException {
        PresentationEntity teamMemberEntity = modelMapper.map(presentation, PresentationEntity.class);
        
        Optional<PresentationEntity> optionalUser = presentationRepository.findById(teamMemberEntity.getIdPresentation());
        if (optionalUser.isEmpty()) {
            logger.error("Presentation not found");
            throw new CustomRuntimeException(CustomRuntimeException.USER_NOT_FOUND);
        }

        PresentationEntity response = null;
        try {
            response = presentationRepository.save(teamMemberEntity);
        } catch (Exception e) {
            logger.error("Error updating presentation", e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, PresentationDTO.class);
    }

    public PresentationDTO setJury1Notes(PresentationDTO presentation, String notes) throws CustomRuntimeException{
        presentation.setJury1Notes(notes);
        return updatePresentation(presentation);
    }

    public PresentationDTO setJury2Notes(PresentationDTO presentation, String notes) throws CustomRuntimeException{
        presentation.setJury2Notes(notes);
        return updatePresentation(presentation);
    }

    public PresentationDTO setJuryNotes(Integer id, String notes) throws CustomRuntimeException{
        PresentationDTO presentation = getPresentationById(id);

        presentationServiceRule.checkDateBeginPassed(presentation.getDatetimeBegin());

        if(userServiceRules.getCurrentUser().getId().equals(presentation.getJury().getTs1().getIdUser())){
            return setJury1Notes(presentation, notes);
        }
        else if(userServiceRules.getCurrentUser().getId().equals(presentation.getJury().getTs2().getIdUser())){
            return setJury2Notes(presentation, notes);
        }
        throw new CustomRuntimeException(CustomRuntimeException.JURY_NOT_FOUND);
    }

    public PresentationDTO setTeamNotes(Integer id, String notes) throws CustomRuntimeException{
        PresentationDTO presentation = getPresentationById(id);

        presentationServiceRule.checkDateEndPassed(presentation.getDatetimeEnd());

        presentation.setValidationTeamNotes(notes);
        return updatePresentation(presentation);
    }

    public PresentationDTO createPresentation(PresentationDTO presentation) throws CustomRuntimeException {
        
        //Check existing jury and project 
        presentationServiceRule.checkJuryExists(presentation.getJury().getIdJury());
        presentationServiceRule.checkProjectExists(presentation.getProject().getIdProject());

        // Verify that user is a Planning assistant
        userServiceRules.checkCurrentUserRole("PLANNING_ROLE");

        // Check Presentation timeframe
        presentationServiceRule.checkPresentationTimeframe(presentation.getDatetimeBegin(), presentation.getDatetimeEnd());

        // Check Teaching Staff availability
        presentationServiceRule.checkTeachingStaffAvailability(presentation.getJury().getIdJury(), presentation.getDatetimeBegin(), presentation.getDatetimeEnd());
    
        // Get the corresponding project entity
        ProjectEntity projectEntity = projectRepository.findById(presentation.getProject().getIdProject()).orElse(null);

        // Get the corresponding jury entity
        JuryEntity juryEntity = juryRepository.findById(presentation.getJury().getIdJury()).orElse(null);

        if(projectEntity != null && juryEntity != null){
            // Set the jury to the one provided in the presentation
            projectEntity.setJury(juryEntity);

            // Save the project
            projectRepository.save(projectEntity);
        }

        // Save Presentation (Convert the saved PresentationEntity back to PresentationDTO)
        return savePresentation(presentation);
    }

    public List<PresentationDTO> getTeamPresentations(Integer teamId) throws CustomRuntimeException {
        TeamEntity team = teamRepository.findById(teamId).orElseThrow(() -> new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND));
        ProjectEntity projectDev = team.getProjectDev();
        ProjectEntity projectValidation = team.getProjectValidation();
    
        List<PresentationEntity> presentations = new ArrayList<>();
        presentations.addAll(presentationRepository.findByProject(projectDev));
        presentations.addAll(presentationRepository.findByProject(projectValidation));
    
        try {
            return presentations.stream()
                    .map(presentation -> modelMapper.map(presentation, PresentationDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public List<PresentationDTO> getTeachingStaffPresentations(Integer staffId) throws CustomRuntimeException {
        List<PresentationEntity> presentations = presentationRepository.findByTeachingStaff(staffId);
    
        try {
            return presentations.stream()
                    .map(presentation -> modelMapper.map(presentation, PresentationDTO.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    public List<PresentationDTO> getTeamMemberPresentations(Integer userId) throws CustomRuntimeException {
        TeamMemberDTO teamMember = teamMemberService.getTeamMemberById(userId);
        return this.getTeamPresentations(teamMember.getTeam().getIdTeam());
    }

}