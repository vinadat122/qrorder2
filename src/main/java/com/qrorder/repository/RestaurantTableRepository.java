package com.qrorder.repository;

import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.enums.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantTableRepository
        extends JpaRepository<RestaurantTable, Long> {

    List<RestaurantTable>
    findByStatus(
            TableStatus status
    );

    boolean existsByTableNumber(Integer tableNumber);

}