package com.qrorder.service;


import com.qrorder.dto.payment.PaymentResponse;

public interface PaymentService {

    PaymentResponse getBill(
            Long sessionId
    );

    void payment(Long sessionId);
}