package com.caerdydd.taf.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.caerdydd.taf.models.dto.notification.NotificationDTO;
import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.FileRules;

@Service
@Transactional
public class FileService {

    private static final Logger logger = LogManager.getLogger(FileService.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private FileRules fileRules;

    @Autowired
    private Environment env;

    @Autowired
    private JuryService juryService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    
    public void saveFile(MultipartFile multipartFile, int id, String type) throws CustomRuntimeException {
    try {
        fileRules.checkFileIsPDF(multipartFile);
        
        String path = env.getProperty("file.upload-dir") + String.format("/equipe%d/", id);
        String fileName = type + ".pdf";

        Files.createDirectories(Paths.get(path));
        multipartFile.transferTo(new File(path, fileName));

        TeamDTO team = teamService.getTeamById(id);
        ProjectDTO project = projectService.getProjectId(team.getProjectDev().getIdProject());

        if (type.equals("teamScopeStatement")) {
            team.setFilePathScopeStatement(path + fileName);
        } else if (type.equals("analysis")) {
            team.setFilePathScopeStatementAnalysis(path + fileName);
        } else if (type.equals("finalTeamScopeStatement")) {
            team.setFilePathFinalScopeStatement(path + fileName);
        } else if (type.equals("report")) {
            team.setFilePathReport(path + fileName);
        }

        // Si le projet a un jury attribué, créez une notification
        if(project.getJury() != null) {
            createAndSendNotification(team, project, type);
        }
        
        teamService.saveTeam(team);
        logger.info("File saved at this location : {}{}", path, fileName);

        } catch (NullPointerException e) {
            logger.error("The team you are trying to add a file is not existing: {}", e.getMessage());
            throw new CustomRuntimeException(CustomRuntimeException.TEAM_NOT_FOUND);
        } catch (IOException e) {

            logger.error("Could not store the file. Error: {}", e.getMessage());
            logger.error(e);
            throw new CustomRuntimeException(CustomRuntimeException.SERVICE_ERROR);
        } 
    }


    public Resource loadFileAsResource(int idTeam, String type) throws CustomRuntimeException {
        try {
            String path = env.getProperty("file.upload-dir") + String.format("/equipe%d/%s.pdf", idTeam, type);
            Path filePath = Paths.get(path).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                logger.warn("File not found for path {}", filePath);
                throw new CustomRuntimeException(CustomRuntimeException.FILE_NOT_FOUND);
            }
        } catch (MalformedURLException ex) {
            logger.warn("File not found {}, {}", type, ex);
            throw new CustomRuntimeException(CustomRuntimeException.FILE_NOT_FOUND);
        }
    }

    public void createAndSendNotification(TeamDTO team, ProjectDTO project, String type) throws CustomRuntimeException {
    // Create notifications for jury members
    JuryDTO jury = juryService.getJury(project.getJury().getIdJury());
    UserDTO juryMember1 = userService.getUserById(jury.getTs1().getIdUser());
    UserDTO juryMember2 = userService.getUserById(jury.getTs2().getIdUser());

    String notificationMessage = String.format("%s a déposé un nouveau fichier de type : %s", team.getName(), getNotificationFileType(type));
    
    NotificationDTO notification1 = new NotificationDTO(notificationMessage, "notificationLink", juryMember1, false);
    NotificationDTO notification2 = new NotificationDTO(notificationMessage, "notificationLink", juryMember2, false);

    notificationService.createNotification(notification1);
    notificationService.createNotification(notification2);
    }

    public String getNotificationFileType(String fileType) {
    switch(fileType) {
        case "teamScopeStatement":
            return "Cahier des charges";
        case "analysis":
            return "Analyse du cahier des charges";
        case "finalTeamScopeStatement":
            return "Cahier des charges final";
        case "report":
            return "Rapport";
        default:
            return fileType;
    }
}


}