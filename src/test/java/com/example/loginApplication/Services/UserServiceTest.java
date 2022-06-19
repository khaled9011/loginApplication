package com.example.loginApplication.Services;
import com.example.loginApplication.Exceptions.UserNotFoundException;
import com.example.loginApplication.Models.DTO.UserDTO;
import com.example.loginApplication.Models.DTO.UserResponseDTO;
import com.example.loginApplication.Models.Entities.User;
import com.example.loginApplication.Repositories.UserRepository;
import com.example.loginApplication.Utils.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class UserServiceTest {
    UserService userService;

    @Autowired
    @MockBean
    UserRepository userRepo;

    @Autowired
    SaltService saltService;

    @BeforeEach
    void setUp() {
        userService = new UserService(userRepo,saltService);
    }


    @Test
    @DisplayName("findAll calls repo.findAll and returns List<UserResponseDTO>")
    void findAll(){
        User test1 = new User("khaled","lmao123", "khaled@mail.com");
        User test2 = new User("ahmed","lmao123", "ahmed@mail.com");
        User test3 = new User("mohamed","lmao123", "mohamed@mail.com");

        List<User> expected = new ArrayList<User>();
        expected.add(test1);
        expected.add(test2);
        expected.add(test3);

        when(userRepo.findAll()).thenReturn(expected);
        //Test
        List<UserResponseDTO> actual = userService.findAllUsers();
        assertEquals(3,actual.size());
        verify(userRepo,times(1)).findAll();
    }

    @Test
    @DisplayName("Get user by username")
    void getUserByUsername() throws Exception {
        when(userRepo.findByUsername("khaled")).thenReturn(new User("khaled","gK-\u00ADµ\u0003O€Öx}¾‚÷êD","khaled@mail.com",State.ACTIVE));
        //Test
        User actual = userService.findByUsername("khaled");
        assertEquals("khaled",actual.getUsername());
        assertEquals("khaled@mail.com",actual.getEmail());
        assertEquals(State.ACTIVE,actual.getState());
    }

    @Test
    @DisplayName("User registers successfully")
    void register() throws Exception {
        userService.register(new UserDTO("khaled","lmao","khaled@mail"));

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepo).save(argumentCaptor.capture());
        User actual = argumentCaptor.getValue();

        assertEquals("khaled",actual.getUsername());
        assertEquals("khaled@mail",actual.getEmail());
        assertNotEquals("lmao",actual.getPassword());
    }

    @Test
    @DisplayName("Can change user state: Activate")
    void activateUser() throws Exception{
        UUID testid = new UUID(2,4);
        User userForMock = new User("khaled", "lmao", "khaled@mail", State.INACTIVE);
        when(userRepo.findById(testid)).thenReturn(Optional.of(userForMock));
        when(userRepo.save(any(User.class))).thenReturn(userForMock);
        //Test
        User testUser = userService.changeUserStates(testid,State.ACTIVE);
        assertNotNull(testUser, "user was not found");
        assertEquals(State.ACTIVE,testUser.getState());
    }

    @Test
    @DisplayName("Can change user state: Deactivate")
    void deactivateUser() throws Exception{
        UUID testid = new UUID(2,4);
        User userForMock = new User("khaled", "lmao", "khaled@mail", State.ACTIVE);
        when(userRepo.findById(testid)).thenReturn(Optional.of(userForMock));
        when(userRepo.save(any(User.class))).thenReturn(userForMock);
        //Test
        User testUser = userService.changeUserStates(testid,State.INACTIVE);
        assertNotNull(testUser, "user was not found");
        assertEquals(State.INACTIVE,testUser.getState());
    }

    @Test
    @DisplayName("Throws UserNotFoundException when user not found")
    void userNotFound() throws Exception{
        when(userRepo.findById(any(UUID.class))).thenReturn(Optional.empty());
        //Test
        assertThatThrownBy(() -> userService.findById(new UUID(1,2)))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User with this Id does not exist");
    }
}