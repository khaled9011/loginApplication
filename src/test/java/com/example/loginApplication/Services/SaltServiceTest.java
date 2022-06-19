package com.example.loginApplication.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaltServiceTest {

    SaltService saltService;

    @BeforeEach
    void setup() throws Exception {
        saltService = new SaltService();
    }


    @Test
    @DisplayName("Encrypting string")
    void encrypt() throws Exception {
        String output = saltService.encrypt("khaled");
        assertEquals("gK-\u00ADµ\u0003O€Öx}¾‚÷êD",output);
    }

    @Test
    @DisplayName("Decrypting string")
    void decrypt() throws Exception {
        String output = saltService.decrypt("gK-\u00ADµ\u0003O€Öx}¾‚÷êD");
        assertEquals("khaled",output);
    }

}