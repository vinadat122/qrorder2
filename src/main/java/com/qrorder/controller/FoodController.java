package com.qrorder.controller;

import com.qrorder.dto.food.UpdateFoodRequest;
import com.qrorder.dto.food.request.CreateFoodRequest;
import com.qrorder.dto.food.FoodResponse;

import com.qrorder.service.FoodService;

import jakarta.validation.Valid;
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

            @Valid
            @RequestBody
            CreateFoodRequest request
    ) {

        foodService.createFood(request);

        return Map.of(
                "message",
                "Create food success"
        );
    }

    @GetMapping
    public List<FoodResponse> getFoods() {

        return foodService.getFoods();
    }

    @GetMapping("/{id}")
    public FoodResponse getFoodById(
            @PathVariable Long id
    ) {

        return foodService.getFoodById(id);
    }

    @PutMapping("/{id}")
    public Map<String, String> updateFood(

            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateFoodRequest request
    ) {

        foodService.updateFood(
                id,
                request
        );

        return Map.of(
                "message",
                "Update food success"
        );
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteFood(
            @PathVariable Long id
    ) {

        foodService.deleteFood(id);

        return Map.of(
                "message",
                "Delete food success"
        );
    }
}
