package com.qrorder.repository;


import com.qrorder.entity.Reservation;
import com.qrorder.entity.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository
        extends JpaRepository<Reservation, Long> {

    List<Reservation>
    findByStatus(
            ReservationStatus status
    );

    List<Reservation>
    findByTableId(
            Long tableId
    );

    Optional<Reservation>
    findByIdAndStatus(

            Long id,

            ReservationStatus status
    );

    List<Reservation>
    findByStatusOrderByCreatedAtAsc(
            ReservationStatus status
    );
}

