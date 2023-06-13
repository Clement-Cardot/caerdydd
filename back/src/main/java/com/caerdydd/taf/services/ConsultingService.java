package com.caerdydd.taf.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
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

import com.caerdydd.taf.models.dto.consulting.ConsultingDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingAvailabilityDTO;
import com.caerdydd.taf.models.dto.consulting.PlannedTimingConsultingDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.entities.consulting.ConsultingEntity;
import com.caerdydd.taf.models.entities.consulting.PlannedTimingAvailabilityEntity;
import com.caerdydd.taf.models.entities.consulting.PlannedTimingConsultingEntity;
import com.caerdydd.taf.repositories.ConsultingRepository;
import com.caerdydd.taf.repositories.PlannedTimingAvailabilityRepository;
import com.caerdydd.taf.repositories.PlannedTimingConsultingRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.ConsultingRules;
import com.caerdydd.taf.services.rules.FileRules;
import com.caerdydd.taf.services.rules.TeamServiceRules;
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
    private TeamServiceRules teamServiceRules;


    @Autowired
    private ConsultingRules consultingRules;
    
    @Autowired
    private TeamService teamService;

    @Autowired
    private FileRules fileRules;

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

    // List all ConsulingDTO
    public List<ConsultingDTO> listAllConsultings() throws CustomRuntimeException {
        try {
            return consultingRepository.findAll().stream()
                        .map(consultingEntity -> modelMapper.map(consultingEntity, ConsultingDTO.class))
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

    // Get a consulting by id
    public ConsultingDTO getConsultingById(Integer id) throws CustomRuntimeException {
        Optional<ConsultingEntity> optionalConsulting;
        try {
            optionalConsulting = consultingRepository.findById(id);
        } catch (Exception e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
    
        if (optionalConsulting.isEmpty()) {
            throw new CustomRuntimeException(CustomRuntimeException.PLANNED_TIMING_AVAILABILITY_NOT_FOUND);
        }
    
        ConsultingEntity consultingEntity = optionalConsulting.get();
        return modelMapper.map(consultingEntity, ConsultingDTO.class);
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

     //Save a consulting
    public ConsultingDTO saveConsulting(ConsultingDTO consulting) {
        ConsultingEntity consultingEntity = modelMapper.map(consulting, ConsultingEntity.class);

        ConsultingEntity response = consultingRepository.save(consultingEntity);

        return modelMapper.map(response, ConsultingDTO.class);
    }

    // Get all the finished consultings of the current teaching staff
    public List<ConsultingDTO> getConsultingsForCurrentTeachingStaff() throws CustomRuntimeException {

        // Verify that user is a Teaching staff
        userServiceRules.checkCurrentUserRole("TEACHING_STAFF_ROLE");

        // Get all consultings
        List<ConsultingDTO> consultings = listAllConsultings();

        // Get all consultings for the current teaching staff
        List<ConsultingDTO> consultingsCurrentTeachingStaff = new ArrayList<>();
        for(ConsultingDTO consulting : consultings) {
            if(consulting.getPlannedTimingAvailability().getTeachingStaff().getIdUser().equals(userServiceRules.getCurrentUser().getId())) {
                consultingsCurrentTeachingStaff.add(consulting);
            }
        }

        return consultingsCurrentTeachingStaff;
    }

    // Get all the consultings for a team
    public List<ConsultingDTO> getConsultingsForATeam(Integer idTeam) throws CustomRuntimeException {
        
        TeamDTO teamDTO = teamService.getTeamById(idTeam);

        // Verify that user has access to the information
        List<String> roles = new ArrayList<>();
        roles.add("TEACHING_STAFF_ROLE");
        roles.add("TEAM_MEMBER_ROLE");
        userServiceRules.checkCurrentUserRoles(roles);

        // Verify that user is a member of the team
        if(userServiceRules.getCurrentUser().getRoles().stream().anyMatch(r -> r.getRole().equals("TEAM_MEMBER_ROLE"))) {
            teamServiceRules.checkIfUserIsMemberOfTeam(teamDTO);
        }

        // Get all consultings
        List<ConsultingDTO> consultings = listAllConsultings();

        // Get all consultings for a team
        List<ConsultingDTO> consultingsForTeam = new ArrayList<>();
        for(ConsultingDTO consulting : consultings) {
            if(consulting.getTeam().getIdTeam().equals(teamDTO.getIdTeam())) {
                consultingsForTeam.add(consulting);
            }
        }

        return consultingsForTeam;
    }

    // Add notes to a consulting
    public ConsultingDTO setNotesConsulting(String idConsulting, String notesConsulting) throws CustomRuntimeException {
        
        // Check if the user is teaching staff
        userServiceRules.checkCurrentUserRole("TEACHING_STAFF_ROLE");

        // Get consulting
        ConsultingDTO consulting = getConsultingById(Integer.parseInt(idConsulting));

        // Check if the user is the teaching staff assigned to the consulting
        if(consulting.getPlannedTimingAvailability().getTeachingStaff().getIdUser().equals(userServiceRules.getCurrentUser().getId())) {
            // Vérifier que le consulting est fini
            if(consulting.getPlannedTimingConsulting().getDatetimeEnd().isBefore(LocalDateTime.now())) {
                consulting.setNotes(notesConsulting);
                saveConsulting(consulting);
                return consulting;
            } else {
                throw new CustomRuntimeException(CustomRuntimeException.CONSULTING_NOT_FINISHED);
            }
        } else {
            throw new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_OWNER_OF_CONSULTING);
        }

    }
        
}
