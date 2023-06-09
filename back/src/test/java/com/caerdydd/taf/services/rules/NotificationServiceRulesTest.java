package com.caerdydd.taf.services.rules;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.caerdydd.taf.repositories.NotificationRepository;
import com.caerdydd.taf.security.CustomRuntimeException;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceRulesTest {
    

    @InjectMocks
    private NotificationServiceRules notificationServiceRules;

    @Mock
    private NotificationRepository notificationRepository;

    @Test
    void testCheckNotificationExists_True() {
        // Prepare Input
        Integer idNotification = 1;
        when(notificationRepository.existsById(idNotification)).thenReturn(true);

        // Call method to test
        try {
            notificationServiceRules.checkNotificationExists(idNotification);
        } catch (CustomRuntimeException e) {
            Assertions.fail(e);
        }
    }

    @Test
    void testCheckNotificationExists_False() {
        // Prepare Input
        Integer idNotification = 1;
        when(notificationRepository.existsById(idNotification)).thenReturn(false);

        // Call method to test
        CustomRuntimeException exception = Assertions.assertThrows(CustomRuntimeException.class, () -> {
            notificationServiceRules.checkNotificationExists(idNotification);
        });

        // Verify the result
        Assertions.assertEquals(CustomRuntimeException.NOTIFICATION_NOT_FOUND, exception.getMessage());
    }
}
