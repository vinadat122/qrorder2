package com.qrorder.service.impl;

import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.TableSession;
import com.qrorder.entity.enums.SessionStatus;
import com.qrorder.entity.enums.TableStatus;
import com.qrorder.repository.RestaurantTableRepository;
import com.qrorder.repository.TableSessionRepository;
import com.qrorder.service.TableSessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TableSessionServiceImpl
        implements TableSessionService {

    private final TableSessionRepository sessionRepository;

    private final RestaurantTableRepository tableRepository;

    @Override
    public TableSession openSession(Long tableId) {

        boolean exists = sessionRepository
                .existsByTableIdAndStatus(
                        tableId,
                        SessionStatus.OPEN
                );

        if(exists) {
            throw new RuntimeException(
                    "Table already has open session"
            );
        }

        RestaurantTable table = tableRepository
                .findById(tableId)
                .orElseThrow(() ->
                        new RuntimeException("Table not found"));

        table.setStatus(TableStatus.OCCUPIED);

        TableSession session = TableSession.builder()
                .table(table)
                .startTime(LocalDateTime.now())
                .status(SessionStatus.OPEN)
                .build();

        return sessionRepository.save(session);

    }

    @Override
    @Transactional
    public Long getActiveSessionId(
            Long tableId
    ) {

        TableSession session =
                sessionRepository
                        .findByTableIdAndStatus(

                                tableId,

                                SessionStatus.OPEN
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "No active session"
                                )
                        );

        return session.getId();
    }
}