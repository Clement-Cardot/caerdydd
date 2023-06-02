package com.caerdydd.taf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.project.PresentationDTO;
import com.caerdydd.taf.models.entities.project.PresentationEntity;
import com.caerdydd.taf.models.entities.project.ProjectEntity;
import com.caerdydd.taf.models.entities.project.TeamEntity;
import com.caerdydd.taf.models.entities.user.TeachingStaffEntity;
import com.caerdydd.taf.repositories.PresentationRepository;
import com.caerdydd.taf.repositories.TeachingStaffRepository;
import com.caerdydd.taf.repositories.TeamRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.PresentationServiceRule;
import com.caerdydd.taf.services.rules.UserServiceRules;

@Service
@Transactional
public class PresentationService {
    
    @Autowired
    private PresentationRepository presentationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PresentationServiceRule presentationServiceRule;

    @Autowired
    private TeamRepository teamRepository;



    @Autowired
    private UserServiceRules userServiceRules;

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


    public PresentationDTO createPresentation(PresentationDTO presentation) throws CustomRuntimeException {
        
        //Check existing jury and project 
        presentationServiceRule.checkJuryExists(presentation.getJury().getIdJury());
        presentationServiceRule.checkProjectExists(presentation.getProject().getIdProject());

        // Verify that user is a Planning assistant
        userServiceRules.checkCurrentUserRole("PLANNING_ROLE");

        // Check Presentation timeframe
        presentationServiceRule.checkPresentationTimeframe(presentation.getDatetimeBegin(), presentation.getDatetimeEnd());

        // Check  Teaching Staff availability
        presentationServiceRule.checkTeachingStaffAvailability(presentation.getJury().getIdJury(), presentation.getDatetimeBegin(), presentation.getDatetimeEnd());
    
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
    
    
    

    
    
}
