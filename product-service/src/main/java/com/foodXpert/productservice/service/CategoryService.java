package com.foodXpert.productservice.service;

import com.foodXpert.productservice.dto.CategoryRequestDTO;
import com.foodXpert.productservice.model.Category;
import com.foodXpert.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j //coming from lombok. to add the logs
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public String createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = Category.builder()
                .CategoryName(categoryRequestDTO.getCategoryName())
                .CategoryDescription(categoryRequestDTO.getCategoryDescription())
                .build();
        categoryRepository.save(category);
        log.info("Category {} is saved!", category.getId());
        return category.getId();
    }

    //get all categories function
    public List<Category> getAllProductCategories() {
        return categoryRepository.findAll();
    }

}
