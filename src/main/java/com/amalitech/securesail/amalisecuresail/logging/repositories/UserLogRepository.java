package com.amalitech.securesail.amalisecuresail.logging.repositories;

import com.amalitech.securesail.amalisecuresail.logging.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, UUID> {
    List<UserLog> findAllByDateOccurred(LocalDateTime dateOccurred);
    void deleteAllByDateOccurred(LocalDateTime localDateTime);

}
