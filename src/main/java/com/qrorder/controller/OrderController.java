package com.qrorder.controller;

import com.qrorder.dto.order.CreateOrderRequest;

import com.qrorder.dto.order.response.OrderResponse;

import com.qrorder.entity.enums.OrderItemStatus;

import com.qrorder.service.OrderService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")

@RequiredArgsConstructor

public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public String createOrder(

            @RequestBody
            CreateOrderRequest request
    ) {

        orderService.createOrder(
                request
        );

        return "Create order success";
    }

    @GetMapping("/session/{sessionId}")
    public List<OrderResponse>
    getOrdersBySession(

            @PathVariable
            Long sessionId
    ) {

        return orderService
                .getOrdersBySession(
                        sessionId
                );
    }

    @PutMapping(
            "/items/{itemId}/status"
    )

    public String updateOrderItemStatus(

            @PathVariable
            Long itemId,

            @RequestParam
            OrderItemStatus status
    ) {

        orderService.updateOrderItemStatus(

                itemId,

                status
        );
        return "Update success";
    }
}
