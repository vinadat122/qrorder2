package com.qrorder.service;

import com.qrorder.dto.food.UpdateFoodRequest;
import com.qrorder.dto.food.request.CreateFoodRequest;
import com.qrorder.dto.food.FoodResponse;

import java.util.List;

public interface FoodService {

    void createFood(CreateFoodRequest request);

    List<FoodResponse> getFoods();

    FoodResponse getFoodById(
            Long id
    );

    void updateFood(
            Long id,
            UpdateFoodRequest request
    );

    void deleteFood(
            Long id
    );
}