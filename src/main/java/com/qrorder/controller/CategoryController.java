package com.qrorder.controller;

import com.qrorder.dto.category.CreateCategoryRequest;

import com.qrorder.repository.CategoryRepository;
import com.qrorder.service.CategoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/categories")

@RequiredArgsConstructor

public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    @PostMapping
    public Map<String, String> createCategory(

            @RequestBody
            CreateCategoryRequest request
    ) {
        boolean exists =
                categoryRepository
                        .existsByName(
                                request.getName()
                        );

        if(exists) {

            throw new RuntimeException(
                    "Category already exists"
            );
        }

        categoryService.createCategory(
                request
        );

        return Map.of(
                "message",
                "Create category success"
        );
    }
}
