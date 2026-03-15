package com.nurse.booking.repository;

import com.nurse.booking.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByNurseId(Long nurseId);
}
