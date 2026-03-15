package com.nurse.booking.repository;

import com.nurse.booking.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NurseRepository extends JpaRepository<Nurse, Long> {
    Optional<Nurse> findByUsername(String username);
}
