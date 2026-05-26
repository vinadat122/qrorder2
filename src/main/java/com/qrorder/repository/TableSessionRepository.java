package com.qrorder.repository;

import com.qrorder.entity.TableSession;
import com.qrorder.entity.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableSessionRepository
        extends JpaRepository<TableSession, Long> {

    boolean existsByTableIdAndStatus(
            Long tableId,
            SessionStatus status
    );
    Optional<TableSession>
    findByIdAndStatus(
            Long id,
            SessionStatus status
    );
}