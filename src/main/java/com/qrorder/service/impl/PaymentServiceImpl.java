package com.qrorder.service.impl;

import com.qrorder.dto.payment.BillResponse;

import com.qrorder.entity.Order;
import com.qrorder.entity.OrderItem;
import com.qrorder.entity.Reservation;
import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.TableSession;

import com.qrorder.entity.enums.OrderItemStatus;
import com.qrorder.entity.enums.ReservationStatus;
import com.qrorder.entity.enums.SessionStatus;
import com.qrorder.entity.enums.TableStatus;

import com.qrorder.repository.OrderRepository;
import com.qrorder.repository.ReservationRepository;
import com.qrorder.repository.RestaurantTableRepository;
import com.qrorder.repository.TableSessionRepository;

import com.qrorder.service.PaymentService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class PaymentServiceImpl
        implements PaymentService {

    private final OrderRepository orderRepository;

    private final TableSessionRepository sessionRepository;

    private final RestaurantTableRepository tableRepository;

    private final ReservationRepository reservationRepository;

    @Override
    public BillResponse calculateBill(

            Long sessionId
    ) {

        List<Order> orders =

                orderRepository
                        .findBySessionId(
                                sessionId
                        );

        double total = 0;

        for(Order order : orders) {

            for(OrderItem item
                    : order.getItems()) {

                total +=

                        item.getFood().getPrice()

                                *

                                item.getQuantity();
            }
        }

        return BillResponse.builder()

                .sessionId(
                        sessionId
                )

                .totalAmount(
                        total
                )

                .build();
    }

    @Override
    @Transactional
    public void payment(

            Long sessionId
    ) {

        TableSession session =

                sessionRepository
                        .findByIdAndStatus(

                                sessionId,

                                SessionStatus.OPEN
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Session not found or closed"
                                )
                        );

        List<Order> orders =

                orderRepository
                        .findBySessionId(
                                sessionId
                        );

        for(Order order : orders) {

            for(OrderItem item
                    : order.getItems()) {

                if(item.getStatus()
                        != OrderItemStatus.SERVED) {

                    throw new RuntimeException(
                            "All items must be served before payment"
                    );
                }
            }
        }

        RestaurantTable table =
                session.getTable();

        table.setStatus(
                TableStatus.EMPTY
        );

        session.setStatus(
                SessionStatus.CLOSED
        );

        session.setEndTime(
                LocalDateTime.now()
        );

        List<Reservation> reservations =

                reservationRepository
                        .findByTableId(
                                table.getId()
                        );

        reservations.forEach(reservation -> {

            if(reservation.getStatus()
                    == ReservationStatus.ARRIVED) {

                reservation.setStatus(
                        ReservationStatus.COMPLETED
                );
            }
        });

        reservationRepository.saveAll(
                reservations
        );

        sessionRepository.save(
                session
        );

        tableRepository.save(
                table
        );
    }
}
