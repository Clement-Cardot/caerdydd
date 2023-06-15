package com.caerdydd.taf.services.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class FileRulesTest {

    @InjectMocks
    private FileRules fileRules;

    @Test
    void checkFileIsNotEmpty_FileIsEmpty() {
        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", new byte[0]);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileRules.checkFileIsNotEmpty(file);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.FILE_IS_EMPTY, exception.getMessage());
    }

    @Test
    void checkFileIsNotEmpty_FileIsNotEmpty() {
        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "some xml".getBytes());

        // Call method to test
        try {
            fileRules.checkFileIsNotEmpty(file);
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void checkFileIsCSV_FileIsCSV() {
        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.csv", "text/plain", "some xml".getBytes());

        // Call method to test
        try {
            fileRules.checkFileIsCSV(file);
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void checkFileIsCSV_FileIsNotCSV() {
        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "some xml".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileRules.checkFileIsCSV(file);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }

    // @Test
    // void checkFileIsCSV_NullPointerException() {
    //     // Create a null file
    //     MultipartFile file = null;


    //     doThrow(new NullPointerException()).when(fileRules).checkFileIsCSV(file);
    //     // when(fileRules.checkFileIsCSV(file).thenThrow(new NullPointerException());
    //     // Call method to test
    //     CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
    //         fileRules.checkFileIsCSV(file);
    //     });

    //     // Verify the result
    //     assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    // }

    // @Test
    // void checkFileIsCSV_NullPointerException() throws CustomRuntimeException {
    //     // Create a null file
    //     MultipartFile file = null;

    //     // Mock la méthode checkFileIsCSV pour lancer une NullPointerException
    //     Mockito.doThrow(new NullPointerException()).when(fileRules).checkFileIsCSV(Mockito.any(MultipartFile.class));

    //     // Call method to test
    //     NullPointerException exception = Assertions.assertThrows(NullPointerException.class, () -> {
    //         fileRules.checkFileIsCSV(file);
    //     });

    //     // Verify the result
    //     assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    // }

    @Test
    void checkFileIsPDF_FileIsPDF() {
        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.pdf", "text/plain", "some test".getBytes());

        // Call method to test
        try {
            fileRules.checkFileIsPDF(file);
        } catch (CustomRuntimeException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void checkFileIsPDF_FileIsNotPDF() {
        // Create a empty file
        MultipartFile file = new MockMultipartFile("file", "file.txt", "text/plain", "some xml".getBytes());

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            fileRules.checkFileIsPDF(file);
        });
        
        // Verify the result
        assertEquals(CustomRuntimeException.INCORRECT_FILE_FORMAT, exception.getMessage());
    }
}
