package com.qrorder.service.impl;

import com.qrorder.dto.reservation.ReservationResponse;
import com.qrorder.dto.table.request.ReserveTableRequest;
import com.qrorder.entity.Reservation;
import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.TableSession;
import com.qrorder.entity.enums.ReservationStatus;
import com.qrorder.entity.enums.SessionStatus;
import com.qrorder.entity.enums.TableStatus;
import com.qrorder.repository.ReservationRepository;
import com.qrorder.repository.RestaurantTableRepository;
import com.qrorder.repository.TableSessionRepository;
import com.qrorder.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl
        implements ReservationService {

    private final ReservationRepository
            reservationRepository;

    private final RestaurantTableRepository
            tableRepository;

    private final TableSessionRepository
            sessionRepository;

    @Override
    @Transactional
    public void createReservation(
            ReserveTableRequest request
    ) {

        RestaurantTable table =
                findAvailableTable(
                        request.getGuestCount()
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

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .status(
                                table != null
                                        ? ReservationStatus.CONFIRMED
                                        : ReservationStatus.WAITLIST
                        )

                        .table(
                                table
                        )

                        .build();

        reservationRepository.save(
                reservation
        );

        if(table != null) {

            table.setStatus(
                    TableStatus.RESERVED
            );

            tableRepository.save(
                    table
            );
        }
    }

    @Override
    @Transactional
    public void checkIn(
            Long reservationId
    ) {

        Reservation reservation =

                reservationRepository
                        .findByIdAndStatus(

                                reservationId,

                                ReservationStatus.CONFIRMED
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Reservation not found"
                                )
                        );

        if(
                reservation.getStatus()
                        != ReservationStatus.CONFIRMED
        ) {

            throw new RuntimeException(
                    "Reservation is not confirmed"
            );
        }

        RestaurantTable table =
                reservation.getTable();

        TableSession session =

                TableSession.builder()

                        .table(
                                table
                        )

                        .reservation(
                                reservation
                        )

                        .status(
                                SessionStatus.OPEN
                        )

                        .sessionToken(
                                UUID.randomUUID()
                                        .toString()
                        )

                        .startTime(
                                LocalDateTime.now()
                        )

                        .customerName(
                                reservation.getCustomerName()
                        )

                        .customerPhone(
                                reservation.getPhone()
                        )

                        .note(
                                reservation.getNote()
                        )

                        .build();

        sessionRepository.save(
                session
        );

        reservation.setStatus(
                ReservationStatus.CHECKED_IN
        );

        reservationRepository.save(
                reservation
        );

        table.setStatus(
                TableStatus.OCCUPIED
        );

        tableRepository.save(
                table
        );
    }

    @Override
    public List<ReservationResponse>
    getReservations() {

        return reservationRepository

                .findAll()

                .stream()

                .map(this::toResponse)

                .toList();
    }

    private ReservationResponse
    toResponse(
            Reservation reservation
    ) {

        return ReservationResponse
                .builder()

                .id(
                        reservation.getId()
                )

                .customerName(
                        reservation.getCustomerName()
                )

                .phone(
                        reservation.getPhone()
                )

                .guestCount(
                        reservation.getGuestCount()
                )

                .reservationTime(
                        reservation.getReservationTime()
                )

                .note(
                        reservation.getNote()
                )

                .status(
                        reservation.getStatus()
                )

                .tableId(
                        reservation.getTable() != null
                                ? reservation.getTable().getId()
                                : null
                )

                .tableNumber(
                        reservation.getTable() != null
                                ? reservation.getTable().getTableNumber()
                                : null
                )

                .build();
    }

    private RestaurantTable
    findAvailableTable(
            Integer guestCount
    ) {

        return tableRepository

                .findByStatus(
                        TableStatus.EMPTY
                )

                .stream()

                .filter(table ->

                        table.getCapacity()
                                >= guestCount
                )

                .sorted(

                        Comparator.comparing(
                                RestaurantTable::getCapacity
                        )
                )

                .findFirst()

                .orElse(null);
    }

    @Transactional
    public void promoteWaitlist() {

        List<Reservation> waitlist =

                reservationRepository

                        .findByStatusOrderByCreatedAtAsc(
                                ReservationStatus.WAITLIST
                        );

        for (Reservation reservation
                : waitlist) {

            RestaurantTable table =

                    findAvailableTable(
                            reservation.getGuestCount()
                    );

            if(table == null) {

                continue;
            }

            reservation.setTable(
                    table
            );

            reservation.setStatus(
                    ReservationStatus.CONFIRMED
            );

            table.setStatus(
                    TableStatus.RESERVED
            );

            tableRepository.save(
                    table
            );

            reservationRepository.save(
                    reservation
            );
        }
    }

    @Override
    @Transactional
    public void expireReservations() {

        List<Reservation> reservations =

                reservationRepository
                        .findByStatus(
                                ReservationStatus.CONFIRMED
                        );

        LocalDateTime now =
                LocalDateTime.now();

        for (Reservation reservation
                : reservations) {

            LocalDateTime expireTime =

                    reservation
                            .getReservationTime()
                            .plusMinutes(20);

            if(now.isAfter(expireTime)) {

                reservation.setStatus(
                        ReservationStatus.EXPIRED
                );

                RestaurantTable table =
                        reservation.getTable();

                if(table != null) {

                    table.setStatus(
                            TableStatus.EMPTY
                    );

                    tableRepository.save(
                            table
                    );
                }

                reservationRepository.save(
                        reservation
                );
            }
        }

        promoteWaitlist();
    }

}
