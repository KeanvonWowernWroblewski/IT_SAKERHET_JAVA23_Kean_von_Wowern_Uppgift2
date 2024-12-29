package com.example.keanuppgift2v4.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class PasswordUtility {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String hashPassword(String raw) {
        return encoder.encode(raw);
    }

    public boolean checkMatch(String raw, String storedHash) {
        return encoder.matches(raw, storedHash);
    }
}
