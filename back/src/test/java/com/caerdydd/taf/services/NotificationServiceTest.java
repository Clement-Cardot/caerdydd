package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.notification.NotificationDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.notification.NotificationEntity;
import com.caerdydd.taf.repositories.NotificationRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.NotificationServiceRules;
import com.caerdydd.taf.services.rules.UserServiceRules;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    
    @InjectMocks
    private NotificationService notificationService;

    @Spy
    private ModelMapper modelMapper;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationServiceRules notificationServiceRules;

    @Mock
    private UserServiceRules userServiceRules;

    @Test
    void testCreateNotification_Nominal() throws CustomRuntimeException {
        // Préparation de l'entrée et du retour simulé
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setUser(new UserDTO(1, null, null, null, null, null, null));
        NotificationEntity notificationEntity = new NotificationEntity();

        // Simulation des méthodes
        doNothing().when(userServiceRules).checkUserExists(any(Integer.class));
        when(modelMapper.map(notificationDTO, NotificationEntity.class)).thenReturn(notificationEntity);
        when(notificationRepository.save(any(NotificationEntity.class))).thenReturn(notificationEntity);
        
        // Appeler la méthode à tester
        NotificationDTO result = notificationService.createNotification(notificationDTO);

        // Vérifier les résultats
        verify(userServiceRules, times(1)).checkUserExists(any(Integer.class));
        verify(notificationRepository, times(1)).save(any(NotificationEntity.class));
        assertNotNull(result);
    }

    @Test
    void testChangeStatusRead_Nominal() throws CustomRuntimeException {
        // Préparation de l'entrée et du retour simulé
        Integer id = 1;
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setIsRead(false);

        // Simulation des méthodes
        doNothing().when(notificationServiceRules).checkNotificationExists(any(Integer.class));
        when(notificationRepository.findById(id)).thenReturn(Optional.of(notificationEntity));
        when(notificationRepository.save(notificationEntity)).thenReturn(notificationEntity);

        // Appeler la méthode à tester
        NotificationDTO result = notificationService.changeStatusRead(id);

        // Vérifier les résultats
        verify(notificationServiceRules, times(1)).checkNotificationExists(id);
        verify(notificationRepository, times(1)).findById(id);
        verify(notificationRepository, times(1)).save(notificationEntity);
        assertTrue(result.getIsRead());
    }

    @Test
void testGetNotificationsByUserId_Nominal() throws CustomRuntimeException {
    // Mock userServiceRules.checkUserExists() method
    doNothing().when(userServiceRules).checkUserExists(any(Integer.class));

    // Mock notificationRepository.findByUserId() method
    List<NotificationEntity> mockedAnswer = new ArrayList<NotificationEntity>();
    mockedAnswer.add(new NotificationEntity(1, null, null, null, null));
    mockedAnswer.add(new NotificationEntity(2, null, null, null, null));
    when(notificationRepository.findByUserId(any(Integer.class))).thenReturn(mockedAnswer);

    // Define the expected answer
    List<NotificationDTO> expectedAnswer = new ArrayList<NotificationDTO>();
    expectedAnswer.add(new NotificationDTO(1, null, null, null, null));
    expectedAnswer.add(new NotificationDTO(2, null, null, null, null));

    // Call the method to test
    List<NotificationDTO> result = notificationService.getNotificationsByUserId(1);

    // Verify the result
    verify(userServiceRules, times(1)).checkUserExists(any(Integer.class));
    verify(notificationRepository, times(1)).findByUserId(any(Integer.class));
    assertEquals(expectedAnswer.toString(), result.toString());
}

@Test
void testGetAllNotifications_Nominal() {
    // Mock notificationRepository.findAll() method
    List<NotificationEntity> mockedAnswer = new ArrayList<NotificationEntity>();
    mockedAnswer.add(new NotificationEntity(1, null, null, null, null));
    mockedAnswer.add(new NotificationEntity(2, null, null, null, null));
    when(notificationRepository.findAll()).thenReturn(mockedAnswer);

    // Define the expected answer
    List<NotificationDTO> expectedAnswer = new ArrayList<NotificationDTO>();
    expectedAnswer.add(new NotificationDTO(1, null, null, null, null));
    expectedAnswer.add(new NotificationDTO(2, null, null, null, null));

    // Call the method to test
    List<NotificationDTO> result = notificationService.getAllNotifications();

    // Verify the result
    verify(notificationRepository, times(1)).findAll();
    assertEquals(expectedAnswer.toString(), result.toString());
}


}

