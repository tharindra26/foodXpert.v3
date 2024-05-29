package com.foodXpert.productservice.dto;

import com.foodXpert.productservice.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private String id;
    private String productName;
    private String productDescription;
    private List<Category> productCategories;
    private String productUnit;
    private BigDecimal productPricePerUnit;
    private boolean deleted;
}
