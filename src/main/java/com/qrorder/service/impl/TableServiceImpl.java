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
    public void reserveTable(

            Long tableId,

            ReserveTableRequest request
    ) {

        RestaurantTable table =

                tableRepository
                        .findById(tableId)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Table not found"
                                )
                        );

        if(table.getStatus()
                != TableStatus.EMPTY) {

            throw new RuntimeException(
                    "Table unavailable"
            );
        }

        table.setStatus(
                TableStatus.RESERVED
        );

        tableRepository.save(
                table
        );

        Reservation reservation =

                Reservation.builder()

                        .customerName(
                                request.getCustomerName()
                        )

                        .phone(
                                request.getPhone()
                        )

                        .guestCount(
                                request.getGuestCount()
                        )

                        .reservationTime(
                                request.getReservationTime()
                        )

                        .note(
                                request.getNote()
                        )

                        .status(
                                ReservationStatus.PENDING
                        )

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .table(table)

                        .build();

        reservationRepository.save(
                reservation
        );
    }

    @Override
    @Transactional
    public Long checkIn(
            Long tableId
    ) {

        RestaurantTable table =

                tableRepository
                        .findById(tableId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Table not found"
                                )
                        );

        if (table.getStatus()
                == TableStatus.OCCUPIED) {

            throw new RuntimeException(
                    "Table already occupied"
            );
        }

        Optional<TableSession> existingSession =

                tableSessionRepository
                        .findByTableIdAndStatus(

                                tableId,

                                SessionStatus.OPEN
                        );

        if (existingSession.isPresent()) {

            return existingSession
                    .get()
                    .getId();
        }

        List<Reservation> reservations =

                reservationRepository
                        .findByTableId(
                                tableId
                        );

        Reservation activeReservation =

                reservations
                        .stream()
                        .filter(reservation ->

                                reservation.getStatus()
                                        == ReservationStatus.PENDING

                                        ||

                                        reservation.getStatus()
                                                == ReservationStatus.CONFIRMED
                        )
                        .findFirst()
                        .orElse(null);

        reservations.forEach(reservation -> {

            if (reservation.getStatus()
                    == ReservationStatus.PENDING

                    ||

                    reservation.getStatus()
                            == ReservationStatus.CONFIRMED) {

                reservation.setStatus(
                        ReservationStatus.ARRIVED
                );
            }
        });

        reservationRepository.saveAll(
                reservations
        );

        table.setStatus(
                TableStatus.OCCUPIED
        );

        tableRepository.save(
                table
        );

        TableSession session =

                TableSession.builder()

                        .table(table)

                        .sessionToken(
                                UUID.randomUUID()
                                        .toString()
                        )

                        .status(
                                SessionStatus.OPEN
                        )

                        .startTime(
                                LocalDateTime.now()
                        )

                        .customerName(

                                activeReservation != null
                                        ? activeReservation.getCustomerName()
                                        : null
                        )

                        .customerPhone(

                                activeReservation != null
                                        ? activeReservation.getPhone()
                                        : null
                        )

                        .note(

                                activeReservation != null
                                        ? activeReservation.getNote()
                                        : null
                        )
                        .build();


        TableSession savedSession =

                tableSessionRepository
                        .save(session);

        return savedSession.getId();
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
