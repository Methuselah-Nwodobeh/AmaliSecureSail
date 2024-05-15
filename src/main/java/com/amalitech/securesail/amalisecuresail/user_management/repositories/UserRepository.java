package com.amalitech.securesail.amalisecuresail.user_management.repositories;

import com.amalitech.securesail.amalisecuresail.user_management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByEmployeeEmail(String email);
    Optional<User> findUserByEmployeeId(String employeeId);
    Optional<User> findUserById(UUID id);
    boolean existsByEmployeeEmail(String email);
    boolean existsByPersonalEmail(String email);
}
