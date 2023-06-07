package com.caerdydd.taf.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingConsultingDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.consulting.ConsultingDTO;
import com.caerdydd.taf.models.entities.consulting.ConsultingEntity;
import com.caerdydd.taf.models.entities.consulting.PlannedTimingAvailabilityEntity;
import com.caerdydd.taf.models.entities.consulting.PlannedTimingConsultingEntity;
import com.caerdydd.taf.repositories.ConsultingRepository;
import com.caerdydd.taf.repositories.PlannedTimingAvailabilityRepository;
import com.caerdydd.taf.repositories.PlannedTimingConsultingRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.ConsultingRules;
import com.caerdydd.taf.services.rules.FileRules;
import com.caerdydd.taf.services.rules.UserServiceRules;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class ConsultingService {

    private static final Logger logger = LogManager.getLogger(ConsultingService.class);

    @Autowired
    private ConsultingRepository consultingRepository;

    @Autowired
    private PlannedTimingConsultingRepository plannedTimingConsultingRepository;

    @Autowired
    private PlannedTimingAvailabilityRepository plannedTimingAvailabilityRepository;

    @Autowired
    private TeachingStaffService teachingStaffService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserServiceRules userServiceRules;

    @Autowired
    private FileRules fileRules;

    @Autowired
    private ConsultingRules consultingRules;

    // List all planned timing for consultings
    public List<PlannedTimingConsultingDTO> listAllPlannedTimingConsultings() throws CustomRuntimeException {
        try {
            return plannedTimingConsultingRepository.findAll().stream()
                        .map(plannedTimingConsultingEntity -> modelMapper.map(plannedTimingConsultingEntity, PlannedTimingConsultingDTO.class))
                        .collect(Collectors.toList()) ;
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    }

    // Get a planned timing availability by id
    public PlannedTimingAvailabilityDTO getPlannedTimingAvailabilityById(Integer id) throws CustomRuntimeException {
        Optional<PlannedTimingAvailabilityEntity> optionalAvailability;
        try {
            optionalAvailability = plannedTimingAvailabilityRepository.findById(id);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    
        if (optionalAvailability.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND);
        }
    
        PlannedTimingAvailabilityEntity availabilityEntity = optionalAvailability.get();
        return modelMapper.map(availabilityEntity, PlannedTimingAvailabilityDTO.class);
    }

        // List all consultings
        public List<ConsultingDTO> listAllConsultings() throws CustomRuntimeException {
            try {
                return consultingRepository.findAll().stream()
                            .map(consultingEntity -> modelMapper.map(consultingEntity, ConsultingDTO.class))
                            .collect(Collectors.toList()) ;
            } catch (Exception e) {
                throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
            }
        }

    // Get a consulting by Speciality Infra
    public List<ConsultingDTO> getConsultingsBySpecialityInfra() throws CustomRuntimeException {

        // Verify that user is a Teaching staff
        userServiceRules.checkCurrentUserRole("TEACHING_STAFF_ROLE");

        // Get all consultings
        List<ConsultingDTO> consultings = listAllConsultings();

        // Get all consultings for Speciality infra
        List<ConsultingDTO> consultingsBySpecialityInfra = new ArrayList<>();
        for(ConsultingDTO consulting : consultings) {
            if(consulting.getSpeciality().equals("infrastructure")) {
                consultingsBySpecialityInfra.add(consulting);
            }
        }

        return consultingsBySpecialityInfra;
    }

    // Get a consulting by Speciality Dev
    public List<ConsultingDTO> getConsultingsBySpecialityDevelopment() throws CustomRuntimeException {

        // Verify that user is a Teaching staff
        userServiceRules.checkCurrentUserRole("TEACHING_STAFF_ROLE");

        // Get all consultings
        List<ConsultingDTO> consultings = listAllConsultings();

        // Get all consultings for Speciality dev
        List<ConsultingDTO> consultingsBySpecialityDev = new ArrayList<>();
        for(ConsultingDTO consulting : consultings) {
            if(consulting.getSpeciality().equals("development")) {
                consultingsBySpecialityDev.add(consulting);
            }
        }

        return consultingsBySpecialityDev;
    }

        // Get a consulting by Speciality Modeling
    public List<ConsultingDTO> getConsultingsBySpecialityModeling() throws CustomRuntimeException {

        // Verify that user is a Teaching staff
        userServiceRules.checkCurrentUserRole("TEACHING_STAFF_ROLE");

        // Get all consultings
        List<ConsultingDTO> consultings = listAllConsultings();

        // Get all consultings for Speciality modeling
        List<ConsultingDTO> consultingsBySpecialityModeling = new ArrayList<>();
        for(ConsultingDTO consulting : consultings) {
            if(consulting.getSpeciality().equals("modeling")) {
                consultingsBySpecialityModeling.add(consulting);
            }
        }

        return consultingsBySpecialityModeling;
    }



    // Save a planned Timing for consulting
    public List<PlannedTimingConsultingDTO> savePlannedTimingConsultings(List<PlannedTimingConsultingDTO> plannedTimingConsultings) {
        List<PlannedTimingConsultingEntity> plannedTimingConsultingEntities = plannedTimingConsultings.stream()
            .map(plannedTimingConsulting -> modelMapper.map(plannedTimingConsulting, PlannedTimingConsultingEntity.class))
            .collect(Collectors.toList());

        List<PlannedTimingConsultingEntity> response = plannedTimingConsultingRepository.saveAll(plannedTimingConsultingEntities);

        return response.stream()
            .map(responseElement -> modelMapper.map(responseElement, PlannedTimingConsultingDTO.class))
            .collect(Collectors.toList());
    }

    // Save a planned Timing for consulting
    public PlannedTimingAvailabilityDTO savePlannedTimingAvailability(PlannedTimingAvailabilityDTO consulting) {
        PlannedTimingAvailabilityEntity plannedTimingConsultingEntity = modelMapper.map(consulting, PlannedTimingAvailabilityEntity.class);

        PlannedTimingAvailabilityEntity response = plannedTimingAvailabilityRepository.save(plannedTimingConsultingEntity);

        return modelMapper.map(response, PlannedTimingAvailabilityDTO.class);
    }

    // Upload a file with planned timings for consulting
    public List<PlannedTimingConsultingDTO> uploadPlannedTimingConsultings(MultipartFile consultingFile) throws CustomRuntimeException, IOException {

        // Verify that user is a Planning assistant
        userServiceRules.checkCurrentUserRole("PLANNING_ROLE");

        // Verify that file is not empty
        fileRules.checkFileIsNotEmpty(consultingFile);

        // Verify that file is a .csv
        fileRules.checkFileIsCSV(consultingFile);
            
        // Read file and save consultings
        List<PlannedTimingConsultingDTO> plannedTimingConsultingsFromFile = readCsvFile(consultingFile);
        List<PlannedTimingConsultingDTO> plannedTimingConsultingsToSave = new ArrayList<>();

        List<TeachingStaffDTO> teachingStaffs = teachingStaffService.listAllTeachingStaff();

        for (PlannedTimingConsultingDTO plannedTimingConsulting : plannedTimingConsultingsFromFile) {
            plannedTimingConsulting.setTeachingStaffAvailabilities(new ArrayList<>());
            for (TeachingStaffDTO teachingStaffDTO : teachingStaffs) {
                PlannedTimingAvailabilityDTO availabilityDTO = new PlannedTimingAvailabilityDTO();
                availabilityDTO.setTeachingStaff(teachingStaffDTO);
                availabilityDTO.setPlannedTimingConsulting(plannedTimingConsulting);
                plannedTimingConsulting.getTeachingStaffAvailabilities().add(availabilityDTO);
            }
            plannedTimingConsultingsToSave.add(plannedTimingConsulting);
        }
        return savePlannedTimingConsultings(plannedTimingConsultingsToSave);
    }

    // Read a .csv file with planned timings for consulting
    List<PlannedTimingConsultingDTO> readCsvFile(MultipartFile file) throws CustomRuntimeException, IOException {
        Reader reader = Reader.nullReader();
        try {
            reader = new InputStreamReader(file.getInputStream());
            List<PlannedTimingConsultingDTO> consultings = new CsvToBeanBuilder<PlannedTimingConsultingDTO>(reader).withType(PlannedTimingConsultingDTO.class).build().parse();
            reader.close();
            return consultings;
        } catch (IllegalStateException | IOException e) {
            reader.close();
            logger.warn("Error reading file: {}", e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        } catch (RuntimeException e){
            reader.close();
            logger.warn("Error reading file: {}", e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT);
        } 
        
    }

    // Update a planned timing availability
    public PlannedTimingAvailabilityDTO updatePlannedTimingAvailability(PlannedTimingAvailabilityDTO plannedTimingAvailabilityDTO) throws CustomRuntimeException {

        // Check if planned timing availability exists
        PlannedTimingAvailabilityDTO plannedTimingAvailability = getPlannedTimingAvailabilityById(plannedTimingAvailabilityDTO.getIdPlannedTimingAvailability());

        // Verify that user is a Teaching staff
        userServiceRules.checkCurrentUserRole("TEACHING_STAFF_ROLE");

        // check if user is the owner of the availability
        consultingRules.checkUserIsOwnerOfAvailability(userServiceRules.getCurrentUser().getTeachingStaff(), plannedTimingAvailability);

        // check if planned timing is not in the past
        consultingRules.checkPlannedTimingIsNotInPast(plannedTimingAvailability);

        // check if planned timing is not already taken
        consultingRules.checkPlannedTimingIsNotAlreadyTaken(plannedTimingAvailability);

        // Update entity
        plannedTimingAvailability.setIsAvailable(plannedTimingAvailabilityDTO.getIsAvailable());
        return savePlannedTimingAvailability(plannedTimingAvailability);
    }

    // Create a consulting
    public ConsultingDTO createConsulting(ConsultingDTO consultingDTO) throws CustomRuntimeException {
        ConsultingEntity consultingEntity = modelMapper.map(consultingDTO, ConsultingEntity.class);

        // Verify that user is a Team Member
        userServiceRules.checkCurrentUserRole("TEAM_MEMBER_ROLE");

        // Verify that the demand is made on time
        logger.info("ConsultingDTO1: {}", consultingDTO.toString());
        logger.info("ConsultingDTO2: {}", consultingDTO.getPlannedTimingAvailability().toString());
        logger.info("ConsultingDTO3: {}", consultingDTO.getPlannedTimingAvailability().getPlannedTimingConsulting().toString());
        logger.info("ConsultingDTO4: {}", consultingDTO.getPlannedTimingAvailability().getPlannedTimingConsulting().getDatetimeEnd().toString());
        logger.info("ConsultingDTO5: {}", consultingDTO.getPlannedTimingAvailability().getPlannedTimingConsulting().getDatetimeEnd().minusDays(2).toString());
        consultingRules.checkDemandIsMadeOnTime(consultingDTO);

        ConsultingEntity response = null;
        try {
            response = consultingRepository.save(consultingEntity);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }

        return modelMapper.map(response, ConsultingDTO.class);
    }
}
