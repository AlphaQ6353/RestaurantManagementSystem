// src/main/java/com/restaurant/management/controller/AuthController.java
package com.restaurant.management.controller;

import com.restaurant.management.dto.JwtResponse;
import com.restaurant.management.dto.LoginRequest;
import com.restaurant.management.entity.User;
import com.restaurant.management.service.UserService;
import com.restaurant.management.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            String jwt = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail()));
        } else {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already taken!");
        }

        User result = userService.createUser(user);
        return ResponseEntity.ok(result);
    }
}