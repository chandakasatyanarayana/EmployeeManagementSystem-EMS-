package com.example.employee_backend.controller;


import com.example.employee_backend.entity.User;
import com.example.employee_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        if (userRepository.existsByEmail(user.getEmail())) {
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        userRepository.save(user);
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginRequest) {
        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            response.put("message", "Login successful");
            response.put("userId", user.getId().toString());
            response.put("name", user.getName());
            return ResponseEntity.ok(response);
        }

        response.put("message", "Invalid credentials");
        return ResponseEntity.badRequest().body(response);
    }
}
