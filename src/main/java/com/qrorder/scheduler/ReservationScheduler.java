package com.qrorder.scheduler;

import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.enums.TableStatus;
import com.qrorder.repository.
        RestaurantTableRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.
        Scheduled;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationScheduler {

    private final RestaurantTableRepository
            tableRepository;

    @Scheduled(fixedRate = 60000)
    public void autoCancelReservation() {

        List<RestaurantTable> tables =
                tableRepository.findAll();

        for(RestaurantTable table : tables) {

            if(table.getStatus()
                    == TableStatus.RESERVED) {

                LocalDateTime expiredTime =
                        table.getReservedAt()
                                .plusSeconds(30);

                if(LocalDateTime.now()
                        .isAfter(expiredTime)) {

                    table.setStatus(
                            TableStatus.EMPTY
                    );

                    table.setReservedAt(null);

                    table.setReservationName(
                            null
                    );

                    table.setReservationPhone(
                            null
                    );

                    tableRepository.save(table);

                    System.out.println(

                            "Auto cancelled table: "

                                    + table.getTableNumber()
                    );
                }
            }
        }
    }
}