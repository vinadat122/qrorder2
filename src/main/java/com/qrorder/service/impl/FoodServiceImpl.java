package com.qrorder.service.impl;

import com.qrorder.dto.food.CreateFoodRequest;
import com.qrorder.entity.Category;
import com.qrorder.entity.Food;
import com.qrorder.repository.CategoryRepository;
import com.qrorder.repository.FoodRepository;
import com.qrorder.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl
        implements FoodService {

    private final FoodRepository foodRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public void createFood(CreateFoodRequest request) {

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        Food food = Food.builder()
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .image(request.getImage())
                .available(true)
                .category(category)
                .build();

        foodRepository.save(food);
    }
}