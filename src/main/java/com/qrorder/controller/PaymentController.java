package com.qrorder.controller;


import com.qrorder.dto.payment.PaymentResponse;
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
    public PaymentResponse getBill(

            @PathVariable Long sessionId
    ) {

        return paymentService.getBill(
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
