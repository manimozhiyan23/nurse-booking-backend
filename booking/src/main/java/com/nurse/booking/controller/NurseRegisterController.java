package com.nurse.booking.controller;

import com.nurse.booking.model.Nurse;
import com.nurse.booking.repository.NurseRepository;
import com.nurse.booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nurse")
@CrossOrigin("*")
public class NurseRegisterController {

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerNurse(@RequestBody Nurse nurse) {
        if (nurse.getUsername() == null || nurse.getUsername().isEmpty()) {
            return ResponseEntity.badRequest().body("Username is required");
        }

        if (nurseRepository.findByUsername(nurse.getUsername()).isPresent() || 
            userRepository.findByUsernameAndRole(nurse.getUsername(), "nurse").isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken");
        }

        // Save to Nurse table for details
        nurse.setStatus(Nurse.Status.PENDING);
        nurseRepository.save(nurse);

        // Save to User table for login
        com.nurse.booking.model.User user = new com.nurse.booking.model.User();
        user.setUsername(nurse.getUsername());
        user.setPassword(nurse.getPassword());
        user.setRole("nurse");
        userRepository.save(user);

        return ResponseEntity.ok("Nurse registered successfully. Pending approval.");
    }



}
