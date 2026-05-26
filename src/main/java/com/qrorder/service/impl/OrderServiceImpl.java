package com.qrorder.service.impl;

import com.qrorder.dto.order.CreateOrderItemRequest;
import com.qrorder.dto.order.CreateOrderRequest;
import com.qrorder.entity.*;
import com.qrorder.entity.enums.OrderStatus;
import com.qrorder.entity.enums.SessionStatus;
import com.qrorder.repository.*;
import com.qrorder.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.qrorder.dto.order.response.OrderItemResponse;
import com.qrorder.dto.order.response.OrderResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl
        implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final FoodRepository foodRepository;

    private final TableSessionRepository sessionRepository;

    @Override
    @Transactional
    public void createOrder(
            CreateOrderRequest request
    ) {

        TableSession session = sessionRepository
                .findByIdAndStatus(
                        request.getSessionId(),
                        SessionStatus.OPEN
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Session not found or closed"
                        ));

        Order order = Order.builder()
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .session(session)
                .build();

        orderRepository.save(order);

        List<OrderItem> orderItems =
                new ArrayList<>();

        for(CreateOrderItemRequest itemRequest
                : request.getItems()) {

            if(itemRequest.getQuantity() <= 0) {

                throw new RuntimeException(
                        "Quantity must be greater than 0"
                );
            }

            Food food = foodRepository
                    .findByIdAndAvailable(
                            itemRequest.getFoodId(),
                            true
                    )
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Food not available"
                            ));

            OrderItem orderItem = OrderItem.builder()
                    .food(food)
                    .quantity(itemRequest.getQuantity())
                    .note(itemRequest.getNote())
                    .order(order)
                    .build();

            orderItems.add(orderItem);
        }

        orderItemRepository.saveAll(orderItems);
    }
    @Override
    public List<OrderResponse>
    getOrdersBySession(Long sessionId) {

        List<Order> orders =
                orderRepository.findBySessionId(sessionId);

        return orders.stream().map(order ->

                OrderResponse.builder()
                        .orderId(order.getId())
                        .status(order.getStatus())
                        .createdAt(order.getCreatedAt())

                        .items(order.getItems()
                                .stream()
                                .map(item ->

                                        OrderItemResponse.builder()
                                                .foodId(
                                                        item.getFood().getId()
                                                )
                                                .foodName(
                                                        item.getFood().getName()
                                                )
                                                .quantity(
                                                        item.getQuantity()
                                                )
                                                .note(
                                                        item.getNote()
                                                )
                                                .build()

                                ).toList())

                        .build()

        ).toList();
    }
}