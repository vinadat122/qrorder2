package com.qrorder.service;

import com.qrorder.dto.order.CreateOrderRequest;
import com.qrorder.dto.order.response.OrderResponse;
import com.qrorder.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {

    void createOrder(CreateOrderRequest request);

    List<OrderResponse>
    getOrdersBySession(Long sessionId);

    List<OrderResponse>
    getOrdersByStatus(OrderStatus status);

    void updateOrderStatus(
            Long orderId,
            OrderStatus status
    );
}