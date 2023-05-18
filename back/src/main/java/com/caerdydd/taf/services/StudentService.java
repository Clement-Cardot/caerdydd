package com.caerdydd.taf.services;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.user.RoleDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.FileRules;
import com.caerdydd.taf.services.rules.UserServiceRules;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
@Transactional
public class StudentService {

    private static final Logger logger = LogManager.getLogger(StudentService.class);

    @Autowired
    UserServiceRules userServiceRules;

    @Autowired
    FileRules fileRules;

    @Autowired
    UserService userService;

    public List<UserDTO> uploadStudents(MultipartFile file) throws CustomRuntimeException {
        
        // Verify that user is a Planning assistant
        userServiceRules.checkCurrentUserRole("PLANNING_ROLE");

        // Verify that file is not empty
        fileRules.checkFileIsNotEmpty(file);

        // Verify that file is a .csv
        fileRules.checkFileIsCSV(file);

        // Read file and save students
        List<UserDTO> studentsFromFile = readCsvFile(file);
        List<UserDTO> savedStudents = new ArrayList<>();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (UserDTO student : studentsFromFile) {
            RoleDTO studentRole = new RoleDTO( "STUDENT_ROLE", student);
            student.getRoles().add(studentRole);
            student.setPassword(passwordEncoder.encode(student.getLogin()));
            UserDTO userSaved = userService.saveUser(student);
            savedStudents.add(userSaved);
        }

        return savedStudents;
    }

    List<UserDTO> readCsvFile(MultipartFile file) throws CustomRuntimeException {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            List<UserDTO> consultings = new CsvToBeanBuilder<UserDTO>(reader).withType(UserDTO.class).build().parse();
            reader.close();
            return consultings;
        } catch (IllegalStateException | IOException e) {
            logger.warn("Error reading file: {}", e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        } catch (RuntimeException e){
            logger.warn("Error reading file: {}", e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT);
        } 
    }
    
}
