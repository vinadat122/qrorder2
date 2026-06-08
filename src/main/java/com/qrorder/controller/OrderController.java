package com.qrorder.controller;

import com.qrorder.dto.order.request.CreateOrderRequest;

import com.qrorder.dto.order.response.OrderResponse;

import com.qrorder.entity.enums.OrderItemStatus;

import com.qrorder.service.OrderService;

import jakarta.validation.Valid;
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

            @Valid
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

    @PutMapping("/items/{itemId}/preparing")
    public String preparing(

            @PathVariable
            Long itemId
    ) {

        orderService.preparing(
                itemId
        );

        return "Preparing success";
    }

    @PutMapping("/items/{itemId}/done")
    public String done(

            @PathVariable
            Long itemId
    ) {

        orderService.done(
                itemId
        );

        return "Done success";
    }

    @PutMapping("/items/{itemId}/served")
    public String served(

            @PathVariable
            Long itemId
    ) {

        orderService.served(
                itemId
        );

        return "Served success";
    }

    @PutMapping("/items/{itemId}/cancel")
    public String cancel(

            @PathVariable
            Long itemId
    ) {

        orderService.cancel(
                itemId
        );

        return "Cancel success";
    }

    @PutMapping("/items/{itemId}/wasted")
    public String wasted(

            @PathVariable
            Long itemId
    ) {

        orderService.wasted(
                itemId
        );

        return "Wasted success";
    }

}
