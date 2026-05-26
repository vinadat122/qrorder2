package com.qrorder.controller;

import com.qrorder.dto.order.CreateOrderRequest;
import com.qrorder.entity.enums.OrderStatus;
import com.qrorder.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.qrorder.dto.order.response.OrderResponse;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String createOrder(
            @RequestBody CreateOrderRequest request
    ) {

        orderService.createOrder(request);

        return "Create order success";
    }

    @GetMapping("/session/{sessionId}")
    public List<OrderResponse>
    getOrdersBySession(
            @PathVariable Long sessionId
    ) {

        return orderService
                .getOrdersBySession(sessionId);
    }

    @GetMapping("/status/{status}")
    public List<OrderResponse>
    getOrdersByStatus(
            @PathVariable OrderStatus status
    ) {

        return orderService
                .getOrdersByStatus(status);
    }

    @PutMapping("/{orderId}/status")
    public String updateOrderStatus(

            @PathVariable Long orderId,

            @RequestParam OrderStatus status
    ) {

        orderService.updateOrderStatus(
                orderId,
                status
        );

        return "Update order status success";
    }
}