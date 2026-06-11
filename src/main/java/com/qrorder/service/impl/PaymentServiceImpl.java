package com.qrorder.service.impl;

import com.qrorder.dto.payment.PaymentHistoryResponse;
import com.qrorder.dto.payment.PaymentItemResponse;
import com.qrorder.dto.payment.PaymentResponse;
import com.qrorder.entity.*;

import com.qrorder.entity.enums.OrderItemStatus;
import com.qrorder.entity.enums.ReservationStatus;
import com.qrorder.entity.enums.SessionStatus;
import com.qrorder.entity.enums.TableStatus;

import com.qrorder.repository.*;

import com.qrorder.service.PaymentService;

import com.qrorder.service.ReservationService;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class PaymentServiceImpl
        implements PaymentService {

    private final OrderRepository orderRepository;

    private final TableSessionRepository sessionRepository;

    private final RestaurantTableRepository tableRepository;

    private final ReservationRepository reservationRepository;

    private final PaymentRepository paymentRepository;
    private final ReservationService reservationService;

    @Override
    @Transactional
    public PaymentResponse getBill(
            Long sessionId
    ) {

        TableSession session =

                sessionRepository
                        .findById(sessionId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Session not found"
                                )
                        );

        List<Order> orders =

                orderRepository
                        .findBySessionId(
                                sessionId
                        );

        double totalAmount =

                calculateTotalAmount(
                        orders
                );

        Map<Long, PaymentItemResponse> groupedItems =
                new LinkedHashMap<>();

        for (Order order : orders) {

            for (OrderItem orderItem
                    : order.getItems()) {

                if (orderItem.getStatus()
                        != OrderItemStatus.SERVED

                        &&

                        orderItem.getStatus()
                                != OrderItemStatus.CANCELLED

                        &&

                        orderItem.getStatus()
                                != OrderItemStatus.WASTED) {

                    throw new RuntimeException(
                            "Cannot generate bill while kitchen is processing orders"
                    );
                }

                if(orderItem.getStatus()
                        == OrderItemStatus.CANCELLED

                        ||

                        orderItem.getStatus()
                                == OrderItemStatus.WASTED) {

                    continue;
                }

                Long foodId =
                        orderItem.getFood().getId();

                double unitPrice =
                        orderItem.getFood().getPrice();

                PaymentItemResponse existing =
                        groupedItems.get(foodId);

                if (existing == null) {

                    groupedItems.put(

                            foodId,

                            PaymentItemResponse.builder()

                                    .foodName(
                                            orderItem.getFood().getName()
                                    )

                                    .quantity(1)

                                    .unitPrice(
                                            unitPrice
                                    )

                                    .subtotal(
                                            unitPrice
                                                    *
                                                    orderItem.getQuantity()
                                    )

                                    .build()
                    );

                } else {

                    existing.setQuantity(
                            existing.getQuantity() + 1
                    );

                    existing.setSubtotal(
                            existing.getSubtotal()

                                    +

                                    (
                                            unitPrice
                                                    * orderItem.getQuantity()
                                    )
                    );
                }
            }
        }

        return PaymentResponse.builder()

                .sessionId(
                        sessionId
                )

                .items(
                        new ArrayList<>(
                                groupedItems.values()
                        )
                )

                .totalAmount(
                        totalAmount
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
                                        "Session not found or already closed"
                                )
                        );

        if (paymentRepository.existsBySessionId(
                sessionId
        )) {

            throw new RuntimeException(
                    "Session already paid"
            );
        }

        List<Order> orders =

                orderRepository
                        .findBySessionId(
                                sessionId
                        );

        if (orders.isEmpty()) {

            throw new RuntimeException(
                    "No orders found"
            );
        }

        for (Order order : orders) {

            for (OrderItem item
                    : order.getItems()) {

                if (item.getStatus()
                        != OrderItemStatus.SERVED

                        &&

                        item.getStatus()
                                != OrderItemStatus.CANCELLED

                        &&

                        item.getStatus()
                                != OrderItemStatus.WASTED) {

                    throw new RuntimeException(
                            "All items must be completed before payment"
                    );
                }
            }
        }

        double subtotal =

                calculateTotalAmount(
                        orders
                );

        double serviceCharge = 0;

        double taxAmount = 0;

        double discountAmount = 0;

        double finalAmount =

                subtotal
                        + serviceCharge
                        + taxAmount
                        - discountAmount;

        Payment payment =

                Payment.builder()

                        .session(
                                session
                        )

                        .amount(
                                finalAmount
                        )

                        .paidAt(
                                LocalDateTime.now()
                        )

                        .build();

        paymentRepository.save(
                payment
        );

        session.setSubtotal(
                subtotal
        );

        session.setServiceCharge(
                serviceCharge
        );

        session.setTaxAmount(
                taxAmount
        );

        session.setDiscountAmount(
                discountAmount
        );

        session.setFinalAmount(
                finalAmount
        );

        session.setStatus(
                SessionStatus.CLOSED
        );

        session.setEndTime(
                LocalDateTime.now()
        );

        RestaurantTable table =
                session.getTable();

        table.setStatus(
                TableStatus.EMPTY
        );

        Reservation reservation =
                session.getReservation();

        if(reservation != null) {

            reservation.setStatus(
                    ReservationStatus.COMPLETED
            );

            reservationRepository.save(
                    reservation
            );
        }

        sessionRepository.save(
                session
        );

        tableRepository.save(
                table
        );

        reservationService
                .promoteWaitlist();
    }

    private double calculateTotalAmount(

            List<Order> orders
    ) {

        double totalAmount = 0;

        for (Order order : orders) {

            for (OrderItem item
                    : order.getItems()) {

                if (item.getStatus()
                        == OrderItemStatus.CANCELLED

                        ||

                        item.getStatus()
                                == OrderItemStatus.WASTED) {

                    continue;
                }

                totalAmount +=

                        item.getFood()
                                .getPrice()

                                *

                                item.getQuantity();
            }
        }

        return totalAmount;
    }

    @Override
    public List<PaymentHistoryResponse>
    getPaymentHistory() {

        return paymentRepository

                .findAllByOrderByPaidAtDesc()

                .stream()

                .map(payment ->

                        PaymentHistoryResponse
                                .builder()

                                .paymentId(
                                        payment.getId()
                                )

                                .sessionId(
                                        payment
                                                .getSession()
                                                .getId()
                                )

                                .tableId(
                                        payment
                                                .getSession()
                                                .getTable()
                                                .getId()
                                )

                                .tableNumber(
                                        payment
                                                .getSession()
                                                .getTable()
                                                .getTableNumber()
                                )

                                .amount(
                                        payment.getAmount()
                                )

                                .paidAt(
                                        payment.getPaidAt()
                                )

                                .build()
                )

                .toList();
    }

    @Override
    public PaymentHistoryResponse
    getPaymentDetail(

            Long paymentId
    ) {

        Payment payment =

                paymentRepository
                        .findById(
                                paymentId
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Payment not found"
                                )
                        );

        return PaymentHistoryResponse
                .builder()

                .paymentId(
                        payment.getId()
                )

                .sessionId(
                        payment
                                .getSession()
                                .getId()
                )

                .tableId(
                        payment
                                .getSession()
                                .getTable()
                                .getId()
                )

                .tableNumber(
                        payment
                                .getSession()
                                .getTable()
                                .getTableNumber()
                )

                .amount(
                        payment.getAmount()
                )

                .paidAt(
                        payment.getPaidAt()
                )

                .build();
    }

}
