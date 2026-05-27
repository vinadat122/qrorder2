package com.qrorder.service;

import com.qrorder.dto.food.CreateFoodRequest;
import com.qrorder.entity.Food;

import java.util.List;

public interface FoodService {

    void createFood(CreateFoodRequest request);

    List<Food> getFoods();
}