package com.example.loginApplication.Models.DTO;

import com.example.loginApplication.Utils.State;

public class UserResponseDTO {
    private String username;
    private String email;
    private State state;

    public UserResponseDTO() {
    }

    public UserResponseDTO(String username, String email, State state) {
        this.username = username;
        this.email = email;
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
