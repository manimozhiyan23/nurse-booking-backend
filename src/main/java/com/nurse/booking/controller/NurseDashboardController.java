package com.nurse.booking.controller;

import com.nurse.booking.model.Patient;
import com.nurse.booking.model.NurseBooking;
import com.nurse.booking.repository.PatientRepository;
import com.nurse.booking.repository.NurseBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nurse")
@CrossOrigin("*")
public class NurseDashboardController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private NurseBookingRepository nurseBookingRepository;

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/myscheduling/{nurseId}")
    public List<NurseBooking> getAcceptedBookings(@PathVariable Long nurseId) {
        return nurseBookingRepository.findByNurseIdAndStatus(nurseId, "ACCEPTED");
    }
}
