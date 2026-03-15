package com.nurse.booking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "nurse_bookings")
public class NurseBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String patientName;
    private String patientContact;
    private String address;
    private String requiredService;
    private String preferredDate;
    private String preferredTime;
    private String nurseGenderPreference;
    private String additionalNotes;
    private boolean revisiting; // Add this

    private Long nurseId;
    private String status;
}
