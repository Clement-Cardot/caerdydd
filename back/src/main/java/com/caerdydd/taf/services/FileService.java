package com.caerdydd.taf.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment env;

    public void saveFile(MultipartFile file, int id, String type) throws CustomRuntimeException {
        try {
            final String absolutPath = env.getProperty("file.upload-dir") + "/equipe/";
            Files.createDirectories(Paths.get(absolutPath + id));
            this.checkFileIsPDF(file);
            String fileName = type + ".pdf";
            String filePath = absolutPath + id;
            file.transferTo(new File(filePath, fileName));
            TeamDTO team = teamService.getTeamById(id);
            if (type.equals("teamScopeStatement")) {
                team.setFilePathScopeStatement(absolutPath + id + "/" + type + ".pdf");
            } else if (type.equals("analysis")) {
                team.setFilePathScopeStatementAnalysis(absolutPath + id + "/" + type + ".pdf");
            } else if (type.equals("finalTeamScopeStatement")) {
                team.setFilePathFinalScopeStatement(absolutPath + id + "/" + type + ".pdf");
            }
            teamService.saveTeam(team);
            logger.info("File saved at this location : {} {}", filePath, fileName);
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