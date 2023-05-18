package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.user.JuryDTO;
import com.caerdydd.taf.models.entities.user.JuryEntity;
import com.caerdydd.taf.repositories.JuryRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.JuryServiceRules;

@ExtendWith(MockitoExtension.class)
public class JuryServiceTest {

    @InjectMocks
    private JuryService juryService;

    @Mock
    private JuryRepository juryRepository;

    @Mock
    private JuryServiceRules JuryServiceRules;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void testUpdateTeamMember_Nominal() {
        JuryDTO input = new JuryDTO(1);
        JuryEntity juryEntity = new JuryEntity(1);

        when(juryRepository.save(any(JuryEntity.class))).thenReturn(juryEntity);

        JuryDTO result = new JuryDTO();

        try {
            result = juryService.updateJury(input);
        } catch (CustomRuntimeException e) {
            fail();
        }

        verify(juryRepository, times(1)).save(any(JuryEntity.class));
        assertEquals(input.toString(), result.toString());
    }

    @Test
    void testUpdateTeamMember_ServiceError() throws Exception {
        JuryDTO juryDTO = new JuryDTO();
        
        when(juryRepository.save(any(JuryEntity.class))).thenThrow(new RuntimeException());

        CustomRuntimeException thrownException = assertThrows(CustomRuntimeException.class, () -> {
            juryService.updateJury(juryDTO);
        });
        assertEquals(CustomRuntimeException.SERVICE_ERROR, thrownException.getMessage());
    }
}