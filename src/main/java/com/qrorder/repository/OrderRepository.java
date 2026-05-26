package com.qrorder.repository;

import com.qrorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository
        extends JpaRepository<Order, Long> {
}