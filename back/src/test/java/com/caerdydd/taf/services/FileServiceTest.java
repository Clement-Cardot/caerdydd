package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.File;
import java.io.FileWriter;

import org.springframework.core.io.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.dto.user.TeachingStaffDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.FileRules;


@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private TeamService teamService;

    @Mock
    private Environment env;

    @Spy
    private ModelMapper modelMapper;

    @Mock 
    private FileRules fileRules;

    @Mock
    private ProjectService projectService;

    @Mock
    private JuryService juryService;

    @Mock
    private NotificationService notificationService;



    @Test
    void testSaveFile_Nominal() throws IOException, CustomRuntimeException {
        // Mock ProjectDTO
        ProjectDTO mockedProject = new ProjectDTO();
        mockedProject.setIdProject(1);
        JuryDTO mockedJury = new JuryDTO(
            new TeachingStaffDTO(new UserDTO(1, null, null, null, null, null, null)),
            new TeachingStaffDTO(new UserDTO(2, null, null, null, null, null, null))
            );
        mockedJury.setIdJury(1);
        mockedProject.setJury(mockedJury);

        // Mock TeamDTO
        TeamDTO mockedAnswer = new TeamDTO(1, "testName", mockedProject, null);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        // Mock file
        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));

        // Mock environment
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        // Test saveFile method
        assertDoesNotThrow(() -> fileService.saveFile(file, 1, "teamScopeStatement"));
    }

    @Test
    void testSaveFile_AndCreateNotification() throws IOException, CustomRuntimeException {
        // Mock ProjectDTO
        ProjectDTO mockedProject = new ProjectDTO();
        mockedProject.setIdProject(1);
        JuryDTO mockedJury = new JuryDTO(
            new TeachingStaffDTO(new UserDTO(1, null, null, null, null, null, null)),
            new TeachingStaffDTO(new UserDTO(2, null, null, null, null, null, null))
            );
        mockedJury.setIdJury(1);
        mockedProject.setJury(mockedJury);

        // Mock TeamDTO
        TeamDTO mockedTeam = new TeamDTO(1, "testName", mockedProject, null);
        when(teamService.getTeamById(1)).thenReturn(mockedTeam);

        // Mock file
        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));

        // Mock environment
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        // Lors de la création d'une notification, retournez la même notification
        when(notificationService.createNotification(any())).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> fileService.saveFile(file, 1, "teamScopeStatement"));

        // Vérifiez que les méthodes de création de notifications ont été appelées
        verify(notificationService, times(2)).createNotification(any());
    }

    @Test
    void testSaveFile_TheFileIsAnnotedReport() throws IOException, CustomRuntimeException {
        TeamDTO mockedAnswer = new TeamDTO(1, null, new ProjectDTO(), null);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));
        
        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        try {
            fileService.saveFile(file, 1, "annotedReport");
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testSaveFile_TeamIsNotPresent() throws IOException, CustomRuntimeException {
        when(teamService.getTeamById(1)).thenThrow(new NullPointerException());

        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        // Create a empty file
        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileService.saveFile(file, 1, "test");
        });

        // Assertions
        assertEquals(CustomRuntimeException.TEAM_NOT_FOUND, exception.getMessage());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testSaveFile_CantSave() throws IOException, CustomRuntimeException {
        MultipartFile file = mock(MultipartFile.class);
        doThrow(new IOException()).when(file).transferTo(any(File.class));
        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileService.saveFile(file, 1, "test");
        });

        // Assertions
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testSaveFile_FileIsNotPdf() throws IOException, CustomRuntimeException {
        TeamDTO mockedAnswer = new TeamDTO(1, null, null, null);
        when(teamService.getTeamById(1)).thenReturn(mockedAnswer);

        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        // Mock fileRules.checkFileIsPDF()
        doThrow(new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT)).when(fileRules).checkFileIsPDF(any(MultipartFile.class));

        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some xml".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileService.saveFile(file, 1, "test");
        });

        // Assertions
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testLoadFileAsResource_Nominal() throws CustomRuntimeException, IOException {
        // Mock env
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        // Mock TeamDTO
        TeamDTO mockedTeam = new TeamDTO();
        mockedTeam.setIdTeam(1);
        when(teamService.getTeamById(1)).thenReturn(mockedTeam);

        // Create a temporary file
        File tempFile = File.createTempFile("test", ".pdf");

        // Write some content to it
        FileWriter writer = new FileWriter(tempFile);
        writer.write("some content");
        writer.close();

        // Now we pretend that this is the file that was saved
        String path = tempFile.getAbsolutePath();
        mockedTeam.setFilePathScopeStatement(path);

        // Attempt to load the file as a resource
        try {
            Resource resource = fileService.loadFileAsResource(1, "test");
            assertNotNull(resource);
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        } finally {
            // Delete the temporary file
            tempFile.delete();
        }
    }


    @Test
    void testLoadFileAsResource_TheFileIsNotPresent() throws CustomRuntimeException {
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileService.loadFileAsResource(1, "notExistingFile");
        });

        // Assertions
        assertEquals(CustomRuntimeException.FILE_NOT_FOUND, exception.getMessage());
    }


    @Test
    void testSaveFile_TheFileIsAnalysis() throws IOException, CustomRuntimeException {
        testSaveFile_GivenFileType("analysis");
    }

    @Test
    void testSaveFile_TheFileIsFinalScopeStatement() throws IOException, CustomRuntimeException {
        testSaveFile_GivenFileType("finalTeamScopeStatement");
    }

    @Test
    void testSaveFile_TheFileIsReport() throws IOException, CustomRuntimeException {
        testSaveFile_GivenFileType("report");
    }

    void testSaveFile_GivenFileType(String fileType) throws IOException, CustomRuntimeException {
        // Mock ProjectDTO
        ProjectDTO mockedProject = new ProjectDTO();
        mockedProject.setIdProject(1);

        // Mock TeamDTO
        TeamDTO mockedTeam = new TeamDTO(1, "testName", mockedProject, null);
        when(teamService.getTeamById(1)).thenReturn(mockedTeam);

        // Mock file
        MultipartFile file = mock(MultipartFile.class);
        doNothing().when(file).transferTo(any(File.class));

        // Mock environment
        when(env.getProperty("file.upload-dir")).thenReturn("test/");

        assertDoesNotThrow(() -> fileService.saveFile(file, 1, fileType));
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testGetNotificationFileType() {
        FileService fileService = new FileService();

        // Test pour "analysis"
        String result = fileService.getNotificationFileType("analysis");
        assertEquals("Analyse du cahier des charges", result);

        // Test pour "finalTeamScopeStatement"
        result = fileService.getNotificationFileType("finalTeamScopeStatement");
        assertEquals("Cahier des charges final", result);

        // Test pour "report"
        result = fileService.getNotificationFileType("report");
        assertEquals("Rapport", result);

        // Test pour un type inconnu
        result = fileService.getNotificationFileType("unknownType");
        assertEquals("unknownType", result);
    }


}
