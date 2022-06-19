package com.example.loginApplication.Controllers;

import com.example.loginApplication.Models.DTO.UserResponseDTO;
import com.example.loginApplication.Services.UserService;
import com.example.loginApplication.Utils.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserControllerTest {

    @Autowired
    @MockBean
    UserService userService;

    UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(userService);
    }

    @Test
    void contextLoads(){
        assertNotNull(userController);
    }

    @Test
    @DisplayName("GET /users gets all users from the user service")
    void getAllUsers(){
        List<UserResponseDTO> users = new ArrayList();
        users.add(new UserResponseDTO("khaled","lmao", State.ACTIVE));
        users.add(new UserResponseDTO("abdo","lmao", State.ACTIVE));
        users.add(new UserResponseDTO("mohamed","lmao", State.INACTIVE));
        when(userService.findAllUsers()).thenReturn(users);
        //Test

    }

}