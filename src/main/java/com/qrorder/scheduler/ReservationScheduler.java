package com.qrorder.scheduler;

import com.qrorder.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationService
            reservationService;

    @Scheduled(
            fixedRate = 60000
    )
    public void processReservations() {

        log.info(
                "Running reservation scheduler..."
        );

        System.out.println(
                "Scheduler running..."
        );
        reservationService
                .expireReservations();
    }
}
