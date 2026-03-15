package com.nurse.booking.controller;

import com.nurse.booking.model.NurseBooking;
import com.nurse.booking.repository.NurseBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class NurseBookingController {

    @Autowired
    private NurseBookingRepository repository;

    // ✅ Create a new booking (defaults to PENDING)
    @PostMapping
    public NurseBooking createBooking(@RequestBody NurseBooking booking) {
        booking.setStatus("PENDING");
        booking.setRevisiting(false); // default revisiting status
        return repository.save(booking);
    }

    // ✅ Fetch all bookings (admin purpose)
    @GetMapping
    public List<NurseBooking> getAllBookings() {
        return repository.findAll();
    }

    // ✅ Fetch all PENDING bookings (for nurses to accept)
    @GetMapping("/pending")
    public List<NurseBooking> getPendingBookings() {
        return repository.findByStatus("PENDING");
    }

    // ✅ Accept a booking by a nurse (only if still pending)
    @PutMapping("/{id}/accept")
    public ResponseEntity<NurseBooking> acceptBooking(@PathVariable int id, @RequestParam Long nurseId) {
        Optional<NurseBooking> bookingOpt = repository.findById(id);
        if (bookingOpt.isPresent()) {
            NurseBooking booking = bookingOpt.get();

            if (!"PENDING".equalsIgnoreCase(booking.getStatus())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // already accepted
            }

            booking.setStatus("ACCEPTED");
            booking.setNurseId(nurseId);
            repository.save(booking);
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Fetch all bookings by patient name
    @GetMapping("/patient-bookings")
    public ResponseEntity<List<NurseBooking>> getBookingsByPatientName(@RequestParam String patientName) {
        List<NurseBooking> bookings = repository.findByPatientName(patientName);
        return ResponseEntity.ok(bookings);
    }

    // ✅ Mark a booking as "revisiting"
    @PutMapping("/{id}/revisit")
    public ResponseEntity<NurseBooking> markRevisiting(@PathVariable int id) {
        Optional<NurseBooking> bookingOpt = repository.findById(id);
        if (bookingOpt.isPresent()) {
            NurseBooking booking = bookingOpt.get();
            booking.setRevisiting(true);
            repository.save(booking);
            return ResponseEntity.ok(booking);
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Get all revisiting bookings for a nurse
    @GetMapping("/revisiting")
    public List<NurseBooking> getRevisitingBookings(@RequestParam Long nurseId) {
        return repository.findByNurseIdAndRevisitingTrue(nurseId);
    }

    // ✅ NEW: Get all accepted bookings for a nurse (used in MyPatients)
    @GetMapping("/nurse/{nurseId}")
    public List<NurseBooking> getAcceptedBookingsForNurse(@PathVariable Long nurseId) {
        return repository.findByNurseIdAndStatus(nurseId, "ACCEPTED");
    }
}
