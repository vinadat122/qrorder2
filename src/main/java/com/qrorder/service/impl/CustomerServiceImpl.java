package com.qrorder.service.impl;

import com.qrorder.dto.customer.CustomerOrderRequest;
import com.qrorder.dto.customer.CustomerTableResponse;

import com.qrorder.dto.order.request.CreateOrderRequest;
import com.qrorder.dto.order.response.OrderResponse;
import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.TableSession;

import com.qrorder.entity.enums.SessionStatus;
import com.qrorder.entity.enums.TableStatus;

import com.qrorder.repository.RestaurantTableRepository;
import com.qrorder.repository.TableSessionRepository;

import com.qrorder.service.CustomerService;

import com.qrorder.service.OrderService;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class CustomerServiceImpl
        implements CustomerService {

    private final RestaurantTableRepository
            tableRepository;

    private final TableSessionRepository
            sessionRepository;
    private final OrderService orderService;

    @Override
    @Transactional
    public CustomerTableResponse getTableByQrToken(

            String qrToken
    ) {

        RestaurantTable table =

                tableRepository
                        .findByQrToken(qrToken)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Table not found"
                                )
                        );

        TableSession session =

                sessionRepository
                        .findByTableIdAndStatus(

                                table.getId(),

                                SessionStatus.OPEN
                        )
                        .orElse(null);

        if(session == null) {

            session =

                    TableSession.builder()

                            .table(table)

                            .sessionToken(
                                    UUID.randomUUID()
                                            .toString()
                            )

                            .status(
                                    SessionStatus.OPEN
                            )

                            .startTime(
                                    LocalDateTime.now()
                            )

                            .build();

            session = sessionRepository.save(
                    session
            );

            table.setStatus(
                    TableStatus.OCCUPIED
            );

            tableRepository.save(
                    table
            );
        }

        return CustomerTableResponse.builder()

                .tableId(
                        table.getId()
                )

                .tableNumber(
                        table.getTableNumber()
                )

                .sessionId(
                        session.getId()
                )

                .sessionToken(
                        session.getSessionToken()
                )

                .build();
    }

    @Override
    @Transactional
    public void createCustomerOrder(
            CustomerOrderRequest request
    ) {

        TableSession session =

                sessionRepository
                        .findBySessionToken(
                                request.getSessionToken()
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Session not found"
                                )
                        );

        CreateOrderRequest orderRequest =
                new CreateOrderRequest();

        orderRequest.setSessionId(
                session.getId()
        );

        orderRequest.setItems(
                request.getItems()
        );

        orderService.createOrder(
                orderRequest
        );
    }

    @Override
    public List<OrderResponse>
    getCustomerOrders(
            String sessionToken
    ) {

        TableSession session =

                sessionRepository
                        .findBySessionToken(
                                sessionToken
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Session not found"
                                )
                        );

        return orderService
                .getOrdersBySession(
                        session.getId()
                );
    }
}