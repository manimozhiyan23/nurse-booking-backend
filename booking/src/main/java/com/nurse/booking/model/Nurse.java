package com.nurse.booking.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "nurses")
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String fullName;
    private String gender;
    private String dob;
    private String email;
    private String phone;
    private String address;
    private int experienceYears;
    private String specialization;
    private String qualification;
    private String licenseNo;
    private String availability;
    private String preferredLocations;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        PENDING, APPROVED
    }
}
