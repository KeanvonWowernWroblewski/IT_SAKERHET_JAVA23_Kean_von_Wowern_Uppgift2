package com.example.keanuppgift2v4.controller;

import com.example.keanuppgift2v4.manager.CapsuleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/messages")
public class TimecapsuleAPI {

    @Autowired
    private CapsuleManager capsuleManager;

    @PostMapping("/message")
    public ResponseEntity<?> capsule(@RequestBody Map<String, String> jsonData,
                                     @RequestHeader("Authorization") String authHeader) {
        try {
            // expects jsonData to have messages
            capsuleManager.createCapsule(jsonData, authHeader);
            return ResponseEntity.ok("Your message was encrypted and saved");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("couldn't save the message.");
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Map<String, String>>> fetchAll(@RequestHeader("authorization") String authHeader) {
        try {
            List<Map<String, String>> data = capsuleManager.readAllCapsules(authHeader);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
