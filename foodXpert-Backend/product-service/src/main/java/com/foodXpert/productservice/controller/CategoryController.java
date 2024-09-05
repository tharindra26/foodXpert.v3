package com.foodXpert.productservice.controller;

import com.foodXpert.productservice.dto.requests.CategoryRequestDTO;
import com.foodXpert.productservice.dto.responses.GenericAddOrUpdateResponse;
import com.foodXpert.productservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public GenericAddOrUpdateResponse<CategoryRequestDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        return categoryService.createCategory(categoryRequestDTO);
    }
}
