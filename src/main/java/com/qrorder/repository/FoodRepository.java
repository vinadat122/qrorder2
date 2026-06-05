package com.qrorder.repository;

import com.qrorder.entity.Food;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository
        extends JpaRepository<Food, Long> {

    Optional<Food> findByIdAndAvailable(

            Long id,

            Boolean available
    );

    List<Food> findByAvailable(
            Boolean available
    );

    boolean existsByNameIgnoreCase(
            String name
    );
}
