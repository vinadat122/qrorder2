package com.qrorder.service.impl;

import com.qrorder.dto.food.UpdateFoodRequest;
import com.qrorder.dto.food.request.CreateFoodRequest;
import com.qrorder.dto.food.FoodResponse;

import com.qrorder.entity.Category;
import com.qrorder.entity.Food;

import com.qrorder.repository.CategoryRepository;
import com.qrorder.repository.FoodRepository;

import com.qrorder.service.FoodService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class FoodServiceImpl
        implements FoodService {

    private final FoodRepository foodRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public void createFood(

            CreateFoodRequest request
    ) {

        String foodName =

                request.getName()
                        .trim();

        if (foodRepository.existsByNameIgnoreCase(
                foodName
        )) {

            throw new RuntimeException(
                    "Food already exists"
            );
        }

        Category category =

                categoryRepository
                        .findById(
                                request.getCategoryId()
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "Category not found"
                                )
                        );

        Food food =

                Food.builder()

                        .name(
                                foodName
                        )

                        .type(
                                request.getType()
                        )

                        .price(
                                request.getPrice()
                        )

                        .description(
                                request.getDescription()
                        )

                        .image(
                                request.getImage()
                        )

                        .available(
                                true
                        )

                        .category(
                                category
                        )

                        .build();

        foodRepository.save(
                food
        );
    }

    @Override
    public List<FoodResponse> getFoods() {

        List<Food> foods =

                foodRepository
                        .findByAvailable(
                                true
                        );

        return foods.stream().map(food ->

                FoodResponse.builder()

                        .id(
                                food.getId()
                        )

                        .name(
                                food.getName()
                        )

                        .type(
                                food.getType()
                        )

                        .price(
                                food.getPrice()
                        )

                        .description(
                                food.getDescription()
                        )

                        .image(
                                food.getImage()
                        )

                        .available(
                                food.getAvailable()
                        )

                        .categoryId(
                                food.getCategory().getId()
                        )

                        .categoryName(
                                food.getCategory()
                                        .getName()
                        )

                        .build()

        ).toList();
    }

    @Override
    public void updateFood(
            Long id,
            UpdateFoodRequest request
    ) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Food not found"));

        Category category = categoryRepository.findById(
                request.getCategoryId()
        ).orElseThrow(() ->
                new RuntimeException("Category not found"));

        food.setName(
                request.getName()
        );

        food.setPrice(
                request.getPrice()
        );

        food.setDescription(
                request.getDescription()
        );

        food.setImage(
                request.getImage()
        );

        food.setType(
                request.getType()
        );

        food.setCategory(
                category
        );

        foodRepository.save(food);
    }

    @Override
    public FoodResponse getFoodById(Long id) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Food not found"));

        return FoodResponse.builder()
                .id(food.getId())
                .name(food.getName())
                .type(food.getType())
                .price(food.getPrice())
                .description(food.getDescription())
                .image(food.getImage())
                .available(food.getAvailable())
                .categoryId(food.getCategory().getId())
                .categoryName(food.getCategory().getName())
                .build();
    }

    @Override
    public void deleteFood(Long id) {

        Food food = foodRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Food not found"));

        food.setAvailable(false);

        foodRepository.save(food);
    }
}
