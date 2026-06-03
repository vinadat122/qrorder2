package com.qrorder.repository;

import com.qrorder.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    List<Order> findBySessionId(Long sessionId);
}