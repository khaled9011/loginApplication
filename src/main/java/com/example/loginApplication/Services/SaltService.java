package com.example.loginApplication.Services;

import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Service
public class SaltService {
    Cipher cipher;
    String key = "Bar12345Bar12345";
    Key aesKey;

    public SaltService() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance("AES");
        aesKey = new SecretKeySpec(key.getBytes(), "AES");
    }

    public String encrypt(String secret) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            return new String(cipher.doFinal(secret.getBytes()));
    }

    public String decrypt(String encrypted) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            return new String(cipher.doFinal(encrypted.getBytes()));
    }
}
