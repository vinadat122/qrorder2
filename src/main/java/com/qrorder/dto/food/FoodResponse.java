package com.qrorder.dto.food;

import com.qrorder.entity.enums.FoodType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class FoodResponse {

    private Long id;

    private String name;

    private FoodType type;

    private Double price;

    private String description;

    private String image;

    private Boolean available;

    private String categoryName;

    private Long categoryId;
}
