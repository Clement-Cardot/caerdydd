package com.caerdydd.taf.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.caerdydd.taf.models.dto.project.PresentationDTO;
import com.caerdydd.taf.models.entities.project.PresentationEntity;
import com.caerdydd.taf.repositories.PresentationRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@Service
@Transactional
public class PresentationService {
    
    @Autowired
    private PresentationRepository presentationRepository;

    @Autowired
    private ModelMapper modelMapper;

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
    
}
