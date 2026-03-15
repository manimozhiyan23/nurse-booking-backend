package com.nurse.booking.repository;

import com.nurse.booking.model.NurseBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NurseBookingRepository extends JpaRepository<NurseBooking, Integer> {
    List<NurseBooking> findByStatus(String status);
    List<NurseBooking> findByNurseIdAndStatus(Long nurseId, String status);

    // ✅ NEW: Find bookings by patient name
    List<NurseBooking> findByPatientName(String patientName);
    List<NurseBooking> findByNurseIdAndRevisitingTrue(Long nurseId);


}
