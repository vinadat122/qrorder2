package com.qrorder.service.impl;

import com.qrorder.dto.order.CreateOrderItemRequest;
import com.qrorder.dto.order.CreateOrderRequest;

import com.qrorder.dto.order.response.OrderItemResponse;
import com.qrorder.dto.order.response.OrderResponse;

import com.qrorder.entity.Food;
import com.qrorder.entity.Order;
import com.qrorder.entity.OrderItem;
import com.qrorder.entity.TableSession;

import com.qrorder.entity.enums.FoodType;
import com.qrorder.entity.enums.OrderItemStatus;
import com.qrorder.entity.enums.SessionStatus;

import com.qrorder.repository.FoodRepository;
import com.qrorder.repository.OrderItemRepository;
import com.qrorder.repository.OrderRepository;
import com.qrorder.repository.TableSessionRepository;

import com.qrorder.service.OrderService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

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

        TableSession session =

                sessionRepository
                        .findByIdAndStatus(

                                request.getSessionId(),

                                SessionStatus.OPEN
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Session not found or closed"
                                )
                        );

        Order order =

                Order.builder()

                        .createdAt(
                                LocalDateTime.now()
                        )

                        .session(session)

                        .build();

        Order savedOrder =

                orderRepository.save(
                        order
                );

        List<OrderItem> orderItems =
                new ArrayList<>();

        for (CreateOrderItemRequest itemRequest
                : request.getItems()) {

            if (itemRequest.getQuantity() <= 0) {

                throw new RuntimeException(
                        "Quantity must be greater than 0"
                );
            }

            Food food =

                    foodRepository
                            .findByIdAndAvailable(

                                    itemRequest.getFoodId(),

                                    true
                            )
                            .orElseThrow(() ->

                                    new RuntimeException(
                                            "Food not available"
                                    )
                            );

            OrderItemStatus initialStatus;

            if (food.getType()
                    == FoodType.INSTANT) {

                initialStatus =
                        OrderItemStatus.DONE;

            } else {

                initialStatus =
                        OrderItemStatus.PENDING;
            }

            for (int i = 0;
                 i < itemRequest.getQuantity();
                 i++) {

                OrderItem orderItem =

                        OrderItem.builder()

                                .food(food)

                                .quantity(1)

                                .note(
                                        itemRequest.getNote()
                                )

                                .status(
                                        initialStatus
                                )

                                .order(
                                        savedOrder
                                )

                                .build();

                orderItems.add(
                        orderItem
                );
            }
        }

        orderItemRepository.saveAll(
                orderItems
        );
    }

    @Override
    public List<OrderResponse>
    getOrdersBySession(
            Long sessionId
    ) {

        List<Order> orders =

                orderRepository
                        .findBySessionId(
                                sessionId
                        );

        return orders.stream().map(order ->

                OrderResponse.builder()

                        .orderId(
                                order.getId()
                        )

                        .createdAt(
                                order.getCreatedAt()
                        )

                        .items(

                                order.getItems()

                                        .stream()

                                        .map(item ->

                                                OrderItemResponse
                                                        .builder()

                                                        .itemId(
                                                                item.getId()
                                                        )

                                                        .foodId(
                                                                item.getFood().getId()
                                                        )

                                                        .foodName(
                                                                item.getFood().getName()
                                                        )

                                                        .type(
                                                                item.getFood()
                                                                        .getType()
                                                                        .name()
                                                        )

                                                        .quantity(
                                                                item.getQuantity()
                                                        )

                                                        .note(
                                                                item.getNote()
                                                        )

                                                        .status(
                                                                item.getStatus()
                                                                        .name()
                                                        )

                                                        .build()

                                        )

                                        .toList()
                        )

                        .build()

        ).toList();
    }

    @Override
    @Transactional
    public void updateOrderItemStatus(

            Long itemId,

            OrderItemStatus status
    ) {

        OrderItem item =

                orderItemRepository
                        .findById(itemId)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Item not found"
                                )
                        );

        OrderItemStatus currentStatus =
                item.getStatus();

        if(currentStatus
                == OrderItemStatus.PENDING

                &&

                status
                        != OrderItemStatus.PREPARING) {

            throw new RuntimeException(
                    "Invalid status transition"
            );
        }

        if(currentStatus
                == OrderItemStatus.PREPARING

                &&

                status
                        != OrderItemStatus.DONE) {

            throw new RuntimeException(
                    "Invalid status transition"
            );
        }

        if(currentStatus
                == OrderItemStatus.DONE

                &&

                status
                        != OrderItemStatus.SERVED) {

            throw new RuntimeException(
                    "Invalid status transition"
            );
        }

        item.setStatus(
                status
        );

        orderItemRepository.save(
                item
        );
    }
}
