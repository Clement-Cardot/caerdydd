package com.caerdydd.taf.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.security.CustomRuntimeException;

@Service
@Transactional
public class FileService {

    private static final Logger logger = LogManager.getLogger(FileService.class);

    @Autowired
    private TeamService teamService;

    public void saveFile(MultipartFile file, int id, String type) throws CustomRuntimeException {
        try {
            final String relativePath = "/upload/equipe";
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + relativePath + id));
            this.checkFileIsPDF(file);
            file.transferTo(new File(System.getProperty("user.dir") + relativePath + id + "/" + type + ".pdf"));
            TeamDTO team = teamService.getTeamById(id);
            if (type.equals("teamScopeStatement")) {
                team.setFilePathScopeStatement(relativePath + id + "/" + type + ".pdf");
            } else if (type.equals("analysis")) {
                team.setFilePathScopeStatementAnalysis(relativePath + id + "/" + type + ".pdf");
            } else if (type.equals("finalTeamScopeStatement")) {
                team.setFilePathFinalScopeStatement(relativePath + id + "/" + type + ".pdf");
            }
            teamService.saveTeam(team);
            logger.info("File saved at this location : {}", (relativePath + id + "/" + type + ".pdf"));
        } catch (NullPointerException e) {
            logger.warn("The team you are trying to add a file is not existing: {}", e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        } catch (IOException e) {
            logger.warn("Could not store the file. Error: {}", e.getMessage());
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