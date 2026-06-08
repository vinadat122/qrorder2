package com.qrorder.controller;

import com.qrorder.dto.customer.CustomerOrderRequest;
import com.qrorder.dto.customer.CustomerTableResponse;
import com.qrorder.dto.food.FoodResponse;
import com.qrorder.dto.order.response.OrderResponse;
import com.qrorder.service.CustomerService;
import com.qrorder.service.FoodService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor

public class CustomerController {

    private final CustomerService customerService;

    private final FoodService foodService;

    @GetMapping("/table/{qrToken}")
    public CustomerTableResponse getTableByQrToken(

            @PathVariable
            String qrToken
    ) {

        return customerService
                .getTableByQrToken(
                        qrToken
                );
    }

    @GetMapping("/menu")
    public List<FoodResponse> getMenu() {

        return foodService.getFoods();
    }

    @PostMapping("/orders")
    public String createCustomerOrder(

            @Valid
            @RequestBody
            CustomerOrderRequest request
    ) {

        customerService
                .createCustomerOrder(
                        request
                );

        return "Create order success";
    }

    @GetMapping("/orders/{sessionToken}")
    public List<OrderResponse>
    getCustomerOrders(

            @PathVariable
            String sessionToken
    ) {

        return customerService
                .getCustomerOrders(
                        sessionToken
                );
    }
}

