package com.qrorder.service;

import com.qrorder.dto.reservation.ReservationResponse;
import com.qrorder.dto.table.request.ReserveTableRequest;

import java.util.List;

public interface ReservationService {

    void createReservation(
            ReserveTableRequest request
    );

    void checkIn(
            Long reservationId
    );

    List<ReservationResponse>
    getReservations();

    void promoteWaitlist();

    void expireReservations();
}