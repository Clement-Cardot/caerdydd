package com.caerdydd.taf.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.ConsultingDTO;
import com.caerdydd.taf.models.entities.ConsultingEntity;
import com.caerdydd.taf.repositories.ConsultingRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.UserServiceRules;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class ConsultingService {

    private static final Logger logger = LogManager.getLogger(ConsultingService.class);

    @Autowired
    private ConsultingRepository consultingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceRules userServiceRules;

    public ConsultingDTO saveConsulting(ConsultingDTO consulting) {
        ConsultingEntity consultingEntity = modelMapper.map(consulting, ConsultingEntity.class);

        ConsultingEntity response = consultingRepository.save(consultingEntity);

        return modelMapper.map(response, ConsultingDTO.class);
    }

    public List<ConsultingDTO> uploadConsultings(MultipartFile consultingFile) throws CustomRuntimeException {

        // Verify that user is a Planning assistant
        userServiceRules.checkCurrentUserRole("PLANNING_ROLE");

        // Verify that file is not empty
        if (consultingFile.isEmpty() || consultingFile.getOriginalFilename() == null) {
            logger.warn("File is empty");
            throw new CustomRuntimeException(CustomRuntimeException.FILE_IS_EMPTY);
        }

        // Verify that file is a .csv
        if (!consultingFile.getOriginalFilename().endsWith(".csv")){
            logger.warn("File is not a .csv");
            throw new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT);
        }
            
        // Read file and save consultings
        List<ConsultingDTO> consultingsFromFile = readCsvFile(consultingFile);
        List<ConsultingDTO> consultingsSaved = new ArrayList<>();

        for (ConsultingDTO consulting : consultingsFromFile) {
            consulting.setIsReserved(false);
            consulting.setIsValidated(false);
            ConsultingDTO consultingSaved = saveConsulting(consulting);
            consultingsSaved.add(consultingSaved);
        }

        return consultingsSaved;
    }

    private List<ConsultingDTO> readCsvFile(MultipartFile file) throws CustomRuntimeException {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            List<ConsultingDTO> consultings = new CsvToBeanBuilder<ConsultingDTO>(reader).withType(ConsultingDTO.class).build().parse();
            reader.close();
            return consultings;
        } catch (IllegalStateException | IOException e) {
            logger.warn("Error reading file: {}", e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.FILE_EXCEPTION);
        }
        
    }
        
}
