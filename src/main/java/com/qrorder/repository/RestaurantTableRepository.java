package com.qrorder.repository;

import com.qrorder.entity.RestaurantTable;
import com.qrorder.entity.enums.TableStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantTableRepository
        extends JpaRepository<RestaurantTable, Long> {

    List<RestaurantTable>
    findByStatus(
            TableStatus status
    );

    boolean existsByTableNumber(Integer tableNumber);

    long countByStatus(
            TableStatus status
    );

    Optional<RestaurantTable>
    findByQrToken(
            String qrToken
    );

}