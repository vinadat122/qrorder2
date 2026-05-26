package com.qrorder.repository;

import com.qrorder.entity.TableSession;
import com.qrorder.entity.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableSessionRepository
        extends JpaRepository<TableSession, Long> {

    boolean existsByTableIdAndStatus(
            Long tableId,
            SessionStatus status
    );
}