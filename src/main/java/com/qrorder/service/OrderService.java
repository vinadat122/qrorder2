package com.qrorder.service;

import com.qrorder.dto.order.CreateOrderRequest;

public interface OrderService {

    void createOrder(CreateOrderRequest request);
}