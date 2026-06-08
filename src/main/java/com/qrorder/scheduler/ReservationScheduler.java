package com.qrorder.scheduler;

import com.qrorder.entity.Reservation;
import com.qrorder.entity.RestaurantTable;

import com.qrorder.entity.enums.ReservationStatus;
import com.qrorder.entity.enums.TableStatus;

import com.qrorder.repository.ReservationRepository;
import com.qrorder.repository.RestaurantTableRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor

public class ReservationScheduler {

    private final ReservationRepository
            reservationRepository;

    private final RestaurantTableRepository
            tableRepository;

    @Scheduled(fixedRate = 60000)
    public void autoCancelReservation() {

        List<Reservation> reservations =

                reservationRepository
                        .findByStatus(
                                ReservationStatus.PENDING
                        );

        for (Reservation reservation
                : reservations) {

            LocalDateTime expiredTime =

                    reservation
                            .getReservationTime()
                            .plusMinutes(10);

            if (

                    LocalDateTime.now()
                            .isAfter(expiredTime)

            ) {

                reservation.setStatus(
                        ReservationStatus.CANCELLED
                );

                RestaurantTable table =
                        reservation.getTable();

                if (table.getStatus()
                        == TableStatus.RESERVED) {

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

                System.out.println(

                        "Auto cancelled reservation for table: "

                                +

                                table.getTableNumber()
                );
            }
        }
    }
}