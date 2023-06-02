package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.FileRules;
import com.caerdydd.taf.services.rules.UserServiceRules;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    
    @InjectMocks
    StudentService studentService;

    @Mock
    UserServiceRules userServiceRules;

    @Mock
    FileRules fileRules;

    @Mock
    UserService userService;
    
    @Test
    void uploadStudentsTest_Nominal_1Line() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole();
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        // Mock fileRules.checkFileIsNotEmpty();
        doNothing().when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock fileRules.checkFileIsCSV();
        doNothing().when(fileRules).checkFileIsCSV(any(MultipartFile.class));

        // Mock userService.saveUser();
        when(userService.saveUser(any(UserDTO.class))).thenAnswer(i -> i.getArguments()[0]);

        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "clement,cardot,cardotcl,clement.cardot@reseau.eseo.fr,LD".getBytes());

        // Call the method to test
        List<UserDTO> result = studentService.uploadStudents(file);

        // Assertions
        assertEquals(1, result.size());
        assertEquals("clement", result.get(0).getFirstname());
        assertEquals("cardot", result.get(0).getLastname());
        assertEquals("cardotcl", result.get(0).getLogin());
        assertEquals("clement.cardot@reseau.eseo.fr", result.get(0).getEmail());
        assertEquals("LD", result.get(0).getSpeciality());
        assertTrue(Pattern.matches("\\$[A-Za-z0-9]+\\$[A-Za-z0-9]+\\$.+", result.get(0).getPassword()));
    }

    @Test
    void uploadStudentsTest_Nominal_3Line() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole();
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        // Mock fileRules.checkFileIsNotEmpty();
        doNothing().when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock fileRules.checkFileIsCSV();
        doNothing().when(fileRules).checkFileIsCSV(any(MultipartFile.class));

        // Mock userService.saveUser();
        when(userService.saveUser(any(UserDTO.class))).thenAnswer(i -> i.getArguments()[0]);

        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "clement,cardot,cardotcl,clement.cardot@reseau.eseo.fr,LD\nalexis,bonamy,bonamyal,alexis.bonamy@reseau.eseo.fr,LD\njean,michel,michelje,jean.michel@reseau.eseo.fr,CSS".getBytes());

        // Call the method to test
        List<UserDTO> result = studentService.uploadStudents(file);

        // Assertions
        assertEquals(3, result.size());
        assertEquals("clement", result.get(0).getFirstname());
        assertEquals("alexis", result.get(1).getFirstname());
        assertEquals("jean", result.get(2).getFirstname());
    }

    @Test
    void uploadStudentsTest_UserNotPlanningAssistant() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole();
        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT)).when(userServiceRules).checkCurrentUserRole(anyString());

        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "clement,cardot,cardotcl,clement.cardot@reseau.eseo.fr,LD".getBytes());

        // Call the method to test
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            studentService.uploadStudents(file);
        });

        // Assertions
        assertEquals(CustomRuntimeException.USER_IS_NOT_A_PLANNING_ASSISTANT, exception.getMessage());
    }

    @Test
    void uploadStudentsTest_FileEmpty() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole();
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        // Mock fileRules.checkFileIsNotEmpty();
        doThrow(new CustomRuntimeException(CustomRuntimeException.FILE_IS_EMPTY)).when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "clement,cardot,cardotcl,clement.cardot@reseau.eseo.fr,LD".getBytes());

        // Call the method to test
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            studentService.uploadStudents(file);
        });

        // Assertions
        assertEquals(CustomRuntimeException.FILE_IS_EMPTY, exception.getMessage());
    }

    @Test
    void uploadStudentsTest_FileNotCSV() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole();
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        // Mock fileRules.checkFileIsNotEmpty();
        doNothing().when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock fileRules.checkFileIsCSV();
        doThrow(new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT)).when(fileRules).checkFileIsCSV(any(MultipartFile.class));

        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "clement,cardot,cardotcl,clement.cardot@reseau.eseo.fr,LD".getBytes());

        // Call the method to test
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            studentService.uploadStudents(file);
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }

    @Test
    void uploadStudentsTest_ReadingError() throws CustomRuntimeException {
        // Mock userServiceRules.checkCurrentUserRole();
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        // Mock fileRules.checkFileIsNotEmpty();
        doNothing().when(fileRules).checkFileIsNotEmpty(any(MultipartFile.class));

        // Mock fileRules.checkFileIsCSV();
        doNothing().when(fileRules).checkFileIsCSV(any(MultipartFile.class));

        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "bad input".getBytes());

        // Call the method to test
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            studentService.uploadStudents(file);
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }

    @Test
    void readCsvFileTest_Nominal() throws CustomRuntimeException {
        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "clement,cardot,cardotcl,clement.cardot@reseau.eseo.fr,LD".getBytes());

        // Call the method to test
        List<UserDTO> result = studentService.readCsvFile(file);

        // Assertions
        assertEquals(1, result.size());
        assertEquals("clement", result.get(0).getFirstname());
        assertEquals("cardot", result.get(0).getLastname());
        assertEquals("cardotcl", result.get(0).getLogin());
        assertEquals("clement.cardot@reseau.eseo.fr", result.get(0).getEmail());
        assertEquals("LD", result.get(0).getSpeciality());
        assertNull(result.get(0).getPassword());
    }

    @Test
    void readCsvFileTest_WrongFormat() {
        // Mock File
        MultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "bad input".getBytes());

        // Call the method to test
        CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
            studentService.readCsvFile(file);
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }
}
