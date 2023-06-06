import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.caerdydd.taf.controllers.PresentationController;
import com.caerdydd.taf.models.dto.project.PresentationDTO;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.PresentationService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PresentationControllerTest {
    @Autowired
    private PresentationService presentationService;

    @Test
    void testUpdateNote_nominal() throws CustomRuntimeException {
    }
}
