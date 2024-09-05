package com.foodXpert.productservice.service;

import com.foodXpert.productservice.dto.requests.CategoryRequestDTO;
import com.foodXpert.productservice.dto.responses.GenericAddOrUpdateResponse;
import com.foodXpert.productservice.model.Category;
import com.foodXpert.productservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j //coming from lombok. to add the logs
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public GenericAddOrUpdateResponse<CategoryRequestDTO> createCategory(CategoryRequestDTO categoryRequestDTO) {

        GenericAddOrUpdateResponse<CategoryRequestDTO> response = new GenericAddOrUpdateResponse<>();
        Category category = Category.builder()
                .categoryName(categoryRequestDTO.getCategoryName())
                .categoryDescription(categoryRequestDTO.getCategoryDescription())
                .build();
        categoryRepository.save(category);
        log.info("Category {} is saved!", category.getId());

        response.setId(category.getId());
        response.setMessage("Category created successfully");
        response.setSuccess(true);
        return response;
    }

    //get all categories function
    @Override
    public List<Category> getAllProductCategories() {
        return categoryRepository.findAll();
    }

}
