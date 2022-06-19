package com.example.loginApplication.Services;

import com.example.loginApplication.Exceptions.UserNotFoundException;
import com.example.loginApplication.Models.DTO.UserDTO;
import com.example.loginApplication.Models.DTO.UserResponseDTO;
import com.example.loginApplication.Models.Entities.User;
import com.example.loginApplication.Repositories.UserRepository;
import com.example.loginApplication.Utils.State;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    SaltService saltService;
    UserRepository userRepo;

    public UserService(UserRepository userRepo, SaltService saltService) {
        this.userRepo = userRepo;
        this.saltService = saltService;
    }

    public void register(UserDTO userDTO) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String modifiedPassword = saltService.encrypt(userDTO.getPassword());
        userRepo.save(new User(userDTO.getUsername(),modifiedPassword,userDTO.getEmail()));
    }

    public List<UserResponseDTO> findAllUsers(){
        List<UserResponseDTO> users = new ArrayList<>();
        for (User user: userRepo.findAll()) {
            users.add(new UserResponseDTO(user.getUsername(),user.getEmail(),user.getState()));
        }
        return users;
    }

    public User findByUsername(String username) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        User target = userRepo.findByUsername(username);
        target.setPassword(saltService.decrypt(target.getPassword()));
        return target;
    }

    public User changeUserStates(UUID id , State state){
        return switch (state) {
            case ACTIVE -> changeUserState(id, State.ACTIVE);
            case INACTIVE -> changeUserState(id, State.INACTIVE);
        };
    }

    private User changeUserState(UUID id, State state) throws UserNotFoundException {
       return userRepo.findById(id)
               .map(user -> {
                   user.setState(state);
                   return userRepo.save(user);
               })
               .orElseThrow(() -> new UserNotFoundException("User with this Id does not exist"));
    }

    public User findById(UUID id) throws UserNotFoundException{
        return userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User with this Id does not exist"));
    }
}
