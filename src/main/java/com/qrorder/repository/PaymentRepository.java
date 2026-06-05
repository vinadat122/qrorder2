package com.qrorder.repository;

import com.qrorder.entity.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    boolean existsBySessionId(
            Long sessionId
    );

    List<Payment> findAllByOrderByPaidAtDesc();
}
