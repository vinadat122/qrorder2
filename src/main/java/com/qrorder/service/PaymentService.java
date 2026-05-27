package com.qrorder.service;

import com.qrorder.dto.payment.BillResponse;

public interface PaymentService {

    BillResponse calculateBill(Long sessionId);

    void payment(Long sessionId);
}