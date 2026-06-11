package com.qrorder.service.impl;

import com.qrorder.dto.table.request.CreateTableRequest;
import com.qrorder.dto.table.request.ReserveTableRequest;
import com.qrorder.dto.table.response.TableResponse;

import com.qrorder.entity.Reservation;
import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.TableSession;

import com.qrorder.entity.enums.ReservationStatus;
import com.qrorder.entity.enums.SessionStatus;
import com.qrorder.entity.enums.TableStatus;

import com.qrorder.repository.ReservationRepository;
import com.qrorder.repository.RestaurantTableRepository;
import com.qrorder.repository.TableSessionRepository;

import com.qrorder.service.TableService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class TableServiceImpl
        implements TableService {

    private final RestaurantTableRepository tableRepository;

    private final TableSessionRepository tableSessionRepository;

    private final ReservationRepository reservationRepository;

    private TableResponse mapToResponse(RestaurantTable table) {

        return new TableResponse(
                table.getId(),
                table.getTableNumber(),
                table.getCapacity(),
                table.getQrToken(),
                table.getStatus()
        );
    }

    @Override
    @Transactional
    public void createTable(
            CreateTableRequest request
    ) {

        if (request.getCapacity() <= 0) {

            throw new RuntimeException(
                    "Capacity must be greater than 0"
            );
        }

        boolean exists = tableRepository
                .existsByTableNumber(
                        request.getTableNumber()
                );

        if (exists) {

            throw new RuntimeException(
                    "Table number already exists"
            );
        }

        RestaurantTable table =

                RestaurantTable.builder()

                        .tableNumber(
                                request.getTableNumber()
                        )

                        .capacity(
                                request.getCapacity()
                        )

                        .qrToken(
                                UUID.randomUUID()
                                        .toString()
                        )

                        .status(
                                TableStatus.EMPTY
                        )

                        .build();

        tableRepository.save(
                table
        );
    }


    @Override
    public List<TableResponse> getTables() {

        return tableRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public void resetTable(Long tableId) {

        RestaurantTable table =

                tableRepository
                        .findById(tableId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Table not found"
                                )
                        );

        if (table.getStatus()
                == TableStatus.EMPTY) {

            throw new RuntimeException(
                    "Table already empty"
            );
        }

        TableSession session =

                tableSessionRepository
                        .findByTableIdAndStatus(
                                tableId,
                                SessionStatus.OPEN
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "No active session found"
                                )
                        );

        session.setStatus(
                SessionStatus.CLOSED
        );

        session.setEndTime(
                LocalDateTime.now()
        );

        tableSessionRepository.save(
                session
        );

        table.setStatus(
                TableStatus.EMPTY
        );

        tableRepository.save(
                table
        );
    }
}
