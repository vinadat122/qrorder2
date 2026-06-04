package com.qrorder.repository;

import com.qrorder.entity.OrderItem;
import com.qrorder.entity.enums.FoodType;
import com.qrorder.entity.enums.OrderItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {

    List<OrderItem>
    findByStatus(
            OrderItemStatus status
    );

    List<OrderItem>
    findByFood_TypeAndStatusNotIn(

            FoodType type,

            List<OrderItemStatus> statuses
    );

    long countByStatus(
            OrderItemStatus status
    );
}