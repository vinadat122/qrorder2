package com.qrorder.controller;

import com.qrorder.dto.payment.BillResponse;

import com.qrorder.service.PaymentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")

@RequiredArgsConstructor

public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{sessionId}")
    public BillResponse calculateBill(

            @PathVariable
            Long sessionId
    ) {

        return paymentService
                .calculateBill(
                        sessionId
                );
    }

    @PostMapping("/{sessionId}")
    public Map<String, String> payment(

            @PathVariable
            Long sessionId
    ) {

        paymentService.payment(
                sessionId
        );

        return Map.of(
                "message",
                "Payment success"
        );
    }
}
