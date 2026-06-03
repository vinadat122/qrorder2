package com.qrorder.controller;

import com.qrorder.dto.food.CreateFoodRequest;
import com.qrorder.dto.food.FoodResponse;

import com.qrorder.service.FoodService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/foods")

@RequiredArgsConstructor

public class FoodController {

    private final FoodService foodService;

    @PostMapping
    public Map<String, String> createFood(

            @RequestBody
            CreateFoodRequest request
    ) {

        foodService.createFood(
                request
        );

        return Map.of(
                "message",
                "Create food success"
        );
    }

    @GetMapping
    public List<FoodResponse> getFoods() {

        return foodService
                .getFoods();
    }
}
