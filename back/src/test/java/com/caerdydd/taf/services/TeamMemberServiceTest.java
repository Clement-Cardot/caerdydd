package com.caerdydd.taf.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.isNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.caerdydd.taf.models.dto.project.ProjectDTO;
import com.caerdydd.taf.models.dto.project.TeamDTO;
import com.caerdydd.taf.models.dto.user.RoleDTO;
import com.caerdydd.taf.models.dto.user.TeamMemberDTO;
import com.caerdydd.taf.models.dto.user.UserDTO;
import com.caerdydd.taf.models.entities.project.ProjectEntity;
import com.caerdydd.taf.models.entities.project.TeamEntity;
import com.caerdydd.taf.models.entities.user.TeamMemberEntity;
import com.caerdydd.taf.models.entities.user.UserEntity;
import com.caerdydd.taf.repositories.TeamMemberRepository;
import com.caerdydd.taf.security.CustomRuntimeException;
import com.caerdydd.taf.services.rules.TeamMemberServiceRules;
import com.caerdydd.taf.services.rules.UserServiceRules;

@ExtendWith(MockitoExtension.class)
public class TeamMemberServiceTest {

    @InjectMocks
    private TeamMemberService teamMemberService;

    @Mock
    private TeamMemberRepository teamMemberRepository;

    @Mock
    private TeamMemberServiceRules teamMemberServiceRules;

    @Mock
    private UserServiceRules userServiceRules;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void testListAllTeamMembers_Nominal() {
        // Mock teamMemberRepository.findAll() method
        List<TeamMemberEntity> mockedAnswer = new ArrayList<TeamMemberEntity>();
        TeamEntity team = new TeamEntity(
                                        1, 
                                        "Team A",
                                        new ProjectEntity("Project A", "Description 1"),
                                        new ProjectEntity("Project B", "Description 2")
                                    );
        UserEntity user1 = new UserEntity("firstname1", "lastname1", "login1", "password1", "email1", "LD");
        UserEntity user2 = new UserEntity("firstname2", "lastname2", "login2", "password2", "email2", "CSS");
        
        mockedAnswer.add(new TeamMemberEntity(user1,  team));
        mockedAnswer.add(new TeamMemberEntity(user2,  team));
        when(teamMemberRepository.findAll()).thenReturn(mockedAnswer);

        // Define the expected answer
        List<TeamMemberDTO> expectedAnswer = new ArrayList<TeamMemberDTO>();
        TeamDTO expectedTeam = new TeamDTO(
                                        1, 
                                        "Team A",
                                        new ProjectDTO("Project A", "Description 1"),
                                        new ProjectDTO("Project B", "Description 2")
                                    );
        UserDTO expectedUser1 = new UserDTO("firstname1", "lastname1", "login1", "password1", "email1", "LD");
        UserDTO expectedUser2 = new UserDTO("firstname2", "lastname2", "login2", "password2", "email2", "CSS");
        expectedAnswer.add(new TeamMemberDTO(expectedUser1, expectedTeam));
        expectedAnswer.add(new TeamMemberDTO(expectedUser2, expectedTeam));

        // Call the method to test
        List<TeamMemberDTO> result = new ArrayList<TeamMemberDTO>();
        try {
            result = teamMemberService.listAllTeamMembers();
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(teamMemberRepository, times(1)).findAll();
        assertEquals(2, result.size());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testListAllTeamMembers_Empty() {
          // Mock teamMemberRepository.findAll() method
          List<TeamMemberEntity> mockedAnswer = new ArrayList<TeamMemberEntity>();
          when(teamMemberRepository.findAll()).thenReturn(mockedAnswer);
  
          // Define the expected answer
          List<TeamMemberDTO> expectedAnswer = new ArrayList<TeamMemberDTO>();
  
          // Call the method to test
          List<TeamMemberDTO> result = new ArrayList<TeamMemberDTO>();
          try {
              result = teamMemberService.listAllTeamMembers();
          } catch (CustomRuntimeException e) {
              fail();
          }
  
          // Verify the result
          verify(teamMemberRepository, times(1)).findAll();
          assertEquals(0, result.size());
          assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testListAllTeamMembers_ServiceError() {
          // Mock teamMemberRepository.findAll() method
          when(teamMemberRepository.findAll()).thenThrow(new NoSuchElementException());
  
          // Call the method to test
          CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamMemberService.listAllTeamMembers();
        });
  
          // Verify the result
          verify(teamMemberRepository, times(1)).findAll();
          assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }
    
    @Test
    void testGetTeamMembersById_Nominal(){
        // Mock teamMemberRepository.findById() method
        TeamEntity team = new TeamEntity(
                                        1, 
                                        "Team A",
                                        new ProjectEntity("Project A", "Description 1"),
                                        new ProjectEntity("Project B", "Description 2")
                                    );
        UserEntity user = new UserEntity("firstname1", "lastname1", "login1", "password1", "email1", "LD");
        Optional<TeamMemberEntity> mockedAnswer = Optional.of(new TeamMemberEntity(user, team));
        when(teamMemberRepository.findById(1)).thenReturn(mockedAnswer);

        // Define the expected answer
        TeamDTO expectedTeam = new TeamDTO(
                                        1, 
                                        "Team A",
                                        new ProjectDTO("Project A", "Description 1"),
                                        new ProjectDTO("Project B", "Description 2")
                                    );
        UserDTO expectedUser = new UserDTO("firstname1", "lastname1", "login1", "password1", "email1", "LD");
        TeamMemberDTO expectedAnswer = new TeamMemberDTO(expectedUser, expectedTeam);

        // Call the method to test
        TeamMemberDTO result = new TeamMemberDTO();
        try {
            result = teamMemberService.getTeamMemberById(1);
        } catch (CustomRuntimeException e) {
            fail();
        }

        // Verify the result
        verify(teamMemberRepository, times(1)).findById(anyInt());
        assertEquals(expectedAnswer.toString(), result.toString());
    }

    @Test
    void testGetTeamMembersById_TeamMemberNotFound(){
        // Mock teamMemberRepository.findById() method
        Optional<TeamMemberEntity> mockedAnswer = Optional.empty();
        when(teamMemberRepository.findById(1)).thenReturn(mockedAnswer);

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamMemberService.getTeamMemberById(1);
        });

        // Verify the result
        verify(teamMemberRepository, times(1)).findById(anyInt());
        assertEquals(CustomRuntimeException.TEAM_MEMBER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetTeamMembersById_ServiceError(){
        // Mock teamMemberRepository.findById() method
        when(teamMemberRepository.findById(1)).thenThrow(new NoSuchElementException());

        // Call the method to test
        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamMemberService.getTeamMemberById(1);
        });

        // Verify the result
        verify(teamMemberRepository, times(1)).findById(anyInt());
        assertEquals(CustomRuntimeException.SERVICE_ERROR, exception.getMessage());
    }

    @Test
    void testSaveTeamMember_Nominal(){
        // Mock teamMemberRepository.save() method
        TeamEntity team = new TeamEntity(
                                    1, 
                                    "Team A",
                                    new ProjectEntity("Project A", "Description 1"),
                                    new ProjectEntity("Project B", "Description 2")
                                );
        UserEntity user = new UserEntity("firstname1", "lastname1", "login1", "password1", "email1", "LD");
        TeamMemberEntity mockedAnswer = new TeamMemberEntity(user, team);
        when(teamMemberRepository.save(any(TeamMemberEntity.class))).thenReturn(mockedAnswer);

        // Prepare the input
        TeamDTO inputTeam = new TeamDTO(
                                    1, 
                                    "Team A",
                                    new ProjectDTO("Project A", "Description 1"),
                                    new ProjectDTO("Project B", "Description 2")
                                );
        UserDTO inputUser = new UserDTO("firstname1", "lastname1", "login1", "password1", "email1", "LD");
        TeamMemberDTO teamMemberToSave = new TeamMemberDTO(inputUser, inputTeam);
        // Call the method to test
        TeamMemberDTO response = teamMemberService.saveTeamMember(teamMemberToSave);

        // Verify the result
        verify(teamMemberRepository, times(1)).save(any(TeamMemberEntity.class));
        assertEquals(teamMemberToSave.toString(), response.toString());
    }

    @Test
    void testSetBonusTeamMember_Nominal() throws CustomRuntimeException {
        // Mock teamMemberRepository.save() method
        doNothing().when(userServiceRules).checkCurrentUserRoles(List.of("JURY_MEMBER_ROLE", "OPTION_LEADER_ROLE"));

        TeamEntity team = new TeamEntity();
        UserEntity user = new UserEntity(1, "firstname1", "lastname1", "login1", "password1", "email1", "LD");

        TeamMemberEntity teamMember = new TeamMemberEntity(user, team);

        int bonusToAdd = -2;
        teamMember.setBonusPenalty((bonusToAdd));

        ArgumentCaptor<TeamMemberEntity> captor = ArgumentCaptor.forClass(TeamMemberEntity.class);
        when(teamMemberRepository.save(captor.capture())).thenReturn(teamMember);

        Optional<TeamMemberEntity> mockedTeamMember = Optional.of(teamMember);
        when(teamMemberRepository.findById(mockedTeamMember.get().getIdUser())).thenReturn(mockedTeamMember);
        mockedTeamMember.get().setBonusPenalty(0); // set bonus penalty to 0 initially

        teamMemberService.setBonusPenaltyById(mockedTeamMember.get().getIdUser(), bonusToAdd);

        verify(teamMemberServiceRules, times(1)).checkTeamMemberBonusValue(bonusToAdd);
        assertEquals(bonusToAdd, captor.getValue().getBonusPenalty());
    }

    @Test
    void testSetBonusTeamMember_CurrentUserNotOptionLeaderOrJuryMember() throws CustomRuntimeException{
        doThrow(new CustomRuntimeException(CustomRuntimeException.USER_IS_NOT_AUTHORIZED)).when(userServiceRules).checkCurrentUserRoles(List.of("JURY_MEMBER_ROLE", "OPTION_LEADER_ROLE"));

        RoleDTO role = new RoleDTO();
        role.setRole(RoleDTO.TEAM_MEMBER_ROLE);

        UserDTO currentUser = new UserDTO(2, "firstname2", "lastname2", "login2", "password2", "email2", "LD");
        currentUser.setRoles(Collections.singletonList(role));

        CustomRuntimeException exception = Assertions.assertThrowsExactly(CustomRuntimeException.class, () -> {
            teamMemberService.setBonusPenaltyById(2, 1);
        });

        assertEquals(CustomRuntimeException.USER_IS_NOT_AUTHORIZED, exception.getMessage());
    }

    @Test
    void testUpdateTeamMember_Nominal() {
        // Arrange
        TeamDTO teamDTO = new TeamDTO();
        UserDTO userDTO = new UserDTO(1, "firstname3", "lastname3", "login3", "password3", "email3", "J2EE");
        TeamMemberDTO input = new TeamMemberDTO(userDTO, teamDTO);
    
        int teamMemberId = 1;
        TeamMemberEntity teamMemberEntity = new TeamMemberEntity(
            new UserEntity(teamMemberId, "firstname3", "lastname3", "login3", "password3", "email3", "J2EE"),
            new TeamEntity()
        );
        
        // Stubbing
        when(teamMemberRepository.findById(isNull())).thenReturn(Optional.of(teamMemberEntity));
        when(teamMemberRepository.save(any(TeamMemberEntity.class))).thenReturn(teamMemberEntity);
    
        // Act
        TeamMemberDTO result = null;
        try {
            result = teamMemberService.updateTeamMember(input);
        } catch (CustomRuntimeException e) {
            fail();
        }
    
        // Assert
        verify(teamMemberRepository, times(1)).findById(isNull());
        verify(teamMemberRepository, times(1)).save(any(TeamMemberEntity.class));
        assertEquals(input.toString(), result.toString());
    }

    @Test
    void testUpdateTeamMemberUserNotFound() {
    // Arrange
    TeamDTO teamDTO = new TeamDTO();
    UserDTO userDTO = new UserDTO(1, "firstname1", "lastname1", "login1", "password1", "email1", "LD");
    TeamMemberDTO teamMemberDTO = new TeamMemberDTO(userDTO, teamDTO);

    // Stubbing
    doReturn(Optional.empty()).when(teamMemberRepository).findById(ArgumentMatchers.isNull());

    // Act and Assert
    CustomRuntimeException exception = assertThrows(CustomRuntimeException.class, () -> {
        teamMemberService.updateTeamMember(teamMemberDTO);
    });
    assertEquals(CustomRuntimeException.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
void testUpdateTeamMember_ServiceError() {
    // Arrange
    TeamDTO teamDTO = new TeamDTO();
    UserDTO userDTO = new UserDTO(1, "firstname1", "lastname1", "login1", "password1", "email1", "LD");
    TeamMemberDTO teamMemberDTO = new TeamMemberDTO(userDTO, teamDTO);

    TeamEntity team = new TeamEntity();
    UserEntity user = new UserEntity(1, "firstname1", "lastname1", "login1", "password1", "email1", "LD");

    TeamMemberEntity teamMemberEntity = new TeamMemberEntity(user, team);

    when(teamMemberRepository.findById(any())).thenReturn(Optional.of(teamMemberEntity));

    when(teamMemberRepository.save(any(TeamMemberEntity.class))).thenThrow(new RuntimeException("Service Error"));

    // Act and Assert
    CustomRuntimeException thrownException = assertThrows(CustomRuntimeException.class, () -> {
        teamMemberService.updateTeamMember(teamMemberDTO);
    });
    assertEquals(CustomRuntimeException.SERVICE_ERROR, thrownException.getMessage());
}

    @Test
    void testSetIndividualMarkByIdSuccess() throws CustomRuntimeException {
        doNothing().when(userServiceRules).checkCurrentUserRole(anyString());

        TeamEntity team = new TeamEntity();
        UserEntity user = new UserEntity(1, "firstname1", "lastname1", "login1", "password1", "email1", "LD");

        TeamMemberEntity teamMember = new TeamMemberEntity(user, team);

        int mark = 2;
        teamMember.setIndividualMark(mark);

        ArgumentCaptor<TeamMemberEntity> captor = ArgumentCaptor.forClass(TeamMemberEntity.class);
        when(teamMemberRepository.save(captor.capture())).thenReturn(teamMember);

        Optional<TeamMemberEntity> mockedTeamMember = Optional.of(teamMember);
        when(teamMemberRepository.findById(mockedTeamMember.get().getIdUser())).thenReturn(mockedTeamMember);
        mockedTeamMember.get().setIndividualMark(0); // set bonus penalty to 0 initially

        teamMemberService.setIndividualMarkById(mockedTeamMember.get().getIdUser(), mark);

        assertEquals(mark, captor.getValue().getIndividualMark());
    }
    

    @Test
    void testSetIndividualMarkByIdUserNotAuthorized() throws CustomRuntimeException {
        // Mock des inputs
        Integer id = 1;
        Integer individualMark = 15;

        // Appel de la méthode à tester et vérification de l'exception levée
        assertThrows(CustomRuntimeException.class, () -> {
            teamMemberService.setIndividualMarkById(id, individualMark);
        });
    }
    
}