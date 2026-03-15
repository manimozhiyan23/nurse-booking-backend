package com.nurse.booking.controller;

import com.nurse.booking.model.User;
import com.nurse.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{role}/login")
    public ResponseEntity<String> loginUser(@PathVariable String role, @RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or password missing");
        }

        Optional<User> userOpt = userRepository.findByUsernameAndRole(username, role);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return ResponseEntity.ok(role.substring(0, 1).toUpperCase() + role.substring(1) + " login successful");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}
