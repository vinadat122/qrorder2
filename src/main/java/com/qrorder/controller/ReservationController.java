package com.qrorder.controller;

import com.qrorder.dto.reservation.ReservationResponse;
import com.qrorder.dto.table.request.ReserveTableRequest;
import com.qrorder.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService
            reservationService;

    @PostMapping
    public Map<String, String>
    createReservation(

            @Valid
            @RequestBody
            ReserveTableRequest request
    ) {

        reservationService
                .createReservation(
                        request
                );

        return Map.of(
                "message",
                "Reservation created"
        );
    }

    @GetMapping
    public List<ReservationResponse>
    getReservations() {

        return reservationService
                .getReservations();
    }

    @PostMapping("/{id}/checkin")
    public Map<String, String>
    checkIn(

            @PathVariable
            Long id
    ) {

        reservationService
                .checkIn(
                        id
                );

        return Map.of(
                "message",
                "Check in success"
        );
    }
}
