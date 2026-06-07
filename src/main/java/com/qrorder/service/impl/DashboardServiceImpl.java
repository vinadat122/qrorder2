package com.qrorder.service.impl;

import com.qrorder.dto.dashboard.DashboardResponse;
import com.qrorder.entity.Payment;
import com.qrorder.entity.enums.OrderItemStatus;
import com.qrorder.entity.enums.TableStatus;
import com.qrorder.repository.OrderItemRepository;
import com.qrorder.repository.PaymentRepository;
import com.qrorder.repository.RestaurantTableRepository;
import com.qrorder.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final PaymentRepository
            paymentRepository;

    private final RestaurantTableRepository
            tableRepository;

    private final OrderItemRepository
            orderItemRepository;

    @Override
    public DashboardResponse getDashboard() {

        LocalDate today =
                LocalDate.now();

        LocalDateTime startToday =
                today.atStartOfDay();

        LocalDateTime endToday =
                today.plusDays(1)
                        .atStartOfDay();

        double revenueToday =

                paymentRepository

                        .findByPaidAtBetween(
                                startToday,
                                endToday
                        )

                        .stream()

                        .mapToDouble(
                                Payment::getAmount
                        )

                        .sum();

        LocalDate firstDayOfMonth =

                today.withDayOfMonth(
                        1
                );

        double revenueMonth =

                paymentRepository

                        .findByPaidAtBetween(

                                firstDayOfMonth
                                        .atStartOfDay(),

                                endToday
                        )

                        .stream()

                        .mapToDouble(
                                Payment::getAmount
                        )

                        .sum();

        long emptyTables =

                tableRepository
                        .countByStatus(
                                TableStatus.EMPTY
                        );

        long reservedTables =

                tableRepository
                        .countByStatus(
                                TableStatus.RESERVED
                        );

        long occupiedTables =

                tableRepository
                        .countByStatus(
                                TableStatus.OCCUPIED
                        );

        long wastedItems =

                orderItemRepository
                        .countByStatus(
                                OrderItemStatus.WASTED
                        );

        long totalPayments =

                paymentRepository
                        .count();

        return DashboardResponse
                .builder()

                .revenueToday(
                        revenueToday
                )

                .revenueMonth(
                        revenueMonth
                )

                .emptyTables(
                        emptyTables
                )

                .reservedTables(
                        reservedTables
                )

                .occupiedTables(
                        occupiedTables
                )

                .wastedItems(
                        wastedItems
                )

                .totalPayments(
                        totalPayments
                )

                .build();
    }
}