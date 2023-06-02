package com.caerdydd.taf.services.rules;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.security.CustomRuntimeException;

@Component
public class FileRules {

    public void checkFileIsNotEmpty(MultipartFile file) throws CustomRuntimeException {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new CustomRuntimeException(CustomRuntimeException.FILE_IS_EMPTY);
        }
    }

    public void checkFileIsCSV(MultipartFile file) throws CustomRuntimeException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        try {
            if (!fileName.endsWith(".csv")){
                throw new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT);
            }
        } catch (NullPointerException e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        
    }

    public void checkFileIsPDF(MultipartFile file) throws CustomRuntimeException {
        String fileName = file.getOriginalFilename();
        if (fileName == null) throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        try {
            if (!fileName.endsWith(".pdf")){
                throw new CustomRuntimeException(CustomRuntimeException.INCORRECT_FILE_FORMAT);
            }
        } catch (NullPointerException e) {
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        }
        
    }
    
}
