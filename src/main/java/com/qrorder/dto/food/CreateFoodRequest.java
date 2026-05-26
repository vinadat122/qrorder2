package com.qrorder.dto.food;

import lombok.Data;

@Data
public class CreateFoodRequest {

    private String name;

    private Double price;

    private String description;

    private String image;

    private Long categoryId;
}