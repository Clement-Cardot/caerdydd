package com.caerdydd.taf.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void uploadStudentsTest_Nominal() {
        // TODO
    }

    @Test
    public void uploadStudentsTest_UserNotPlanningAssistant() {
        // TODO
    }

    @Test
    public void uploadStudentsTest_FileEmpty() {
        // TODO
    }

    @Test
    public void uploadStudentsTest_FileNotCSV() {
        // TODO
    }

    @Test
    public void uploadStudentsTest_ReadingError() {
        // TODO
    }

    @Test
    public void readCsvFileTest_Nominal() {
        // TODO
    }

    @Test
    public void readCsvFileTest_WrongFormat() {
        // TODO
    }
}
