package com.qrorder.service.impl;

import com.qrorder.dto.category.CreateCategoryRequest;
import com.qrorder.entity.Category;
import com.qrorder.repository.CategoryRepository;
import com.qrorder.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(
            CreateCategoryRequest request
    ) {

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        categoryRepository.save(category);
    }
}