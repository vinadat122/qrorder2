package com.qrorder.service;

import com.qrorder.dto.order.request.CreateOrderRequest;
import com.qrorder.dto.order.response.OrderResponse;

import com.qrorder.entity.enums.OrderItemStatus;

import java.util.List;

public interface OrderService {

    void createOrder(
            CreateOrderRequest request
    );

    List<OrderResponse>
    getOrdersBySession(
            Long sessionId
    );

    void preparing(Long itemId);

    void done(Long itemId);

    void served(Long itemId);

    void cancel(Long itemId);

    void wasted(Long itemId);

}
