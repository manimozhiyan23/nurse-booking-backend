package com.nurse.booking.repository;

import com.nurse.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndRole(String username, String role);
}
