package com.nurse.booking.controller;

import com.nurse.booking.model.User;
import com.nurse.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null || user.getRole() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }

        Optional<User> existingUser = userRepository.findByUsernameAndRole(user.getUsername(), user.getRole());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists for this role");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
