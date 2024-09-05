package com.foodXpert.productservice.service;

import com.foodXpert.productservice.dto.requests.CategoryRequestDTO;
import com.foodXpert.productservice.dto.responses.GenericAddOrUpdateResponse;
import com.foodXpert.productservice.model.Category;

import java.util.List;

public interface CategoryService {
    public GenericAddOrUpdateResponse<CategoryRequestDTO> createCategory(CategoryRequestDTO categoryRequestDTO);
    public List<Category> getAllProductCategories();
}
