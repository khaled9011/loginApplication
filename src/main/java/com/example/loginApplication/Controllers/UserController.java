package com.example.loginApplication.Controllers;


import com.example.loginApplication.Models.DTO.UserDTO;
import com.example.loginApplication.Models.DTO.UserResponseDTO;
import com.example.loginApplication.Services.UserService;
import com.example.loginApplication.Utils.State;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers(){
        return userService.findAllUsers();
    }

    @PostMapping("register")
    public void registerUser(@RequestBody UserDTO userDTO) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        this.userService.register(userDTO);
    }

    //TODO
    @PostMapping("signIn/{id}")
    public void signInUser(){

    }

    //TODO
    @PostMapping("changeState/{id}")
    public void changeUserState(@PathVariable UUID id, @Param("state")State s){
        userService.changeUserStates(id,s);
    }
}
