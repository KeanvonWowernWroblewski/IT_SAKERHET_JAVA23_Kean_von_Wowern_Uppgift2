package com.example.keanuppgift2v4.controller;

import com.example.keanuppgift2v4.manager.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/auth")
public class AccessGate {

    @Autowired
    private AccountManager accountManager;

    @PostMapping("/register")
    public ResponseEntity<String> signUp(@RequestBody Map<String, Object> body) {
        // expects name, email, age, address, password
        accountManager.createAccount(body);
        return ResponseEntity.ok("User account has been registered.");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> signIn(@RequestBody Map<String, String> loginBody) {
        // expects email, password
        String tokenVal = accountManager.processLogin(loginBody);
        return ResponseEntity.ok(Map.of("token", tokenVal));
    }
}
