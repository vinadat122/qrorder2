package com.qrorder.repository;

import com.qrorder.entity.TableSession;
import com.qrorder.entity.enums.SessionStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

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


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<TableSession>
    findByTableIdAndStatus(

            Long tableId,

            SessionStatus status
    );


    Optional<TableSession>
    findBySessionToken(
            String sessionToken
    );

    long countByStatus(
            SessionStatus status
    );

}