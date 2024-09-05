package com.foodXpert.productservice.dto.responses.products;

import com.foodXpert.productservice.dto.responses.categories.CategoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String productName;
    private String productDescription;
    private CategoryResponseDTO productCategory;;
    private String productUnit;
    private BigDecimal productPricePerUnit;
    private int quantity;
    private String productImagePath;
    private boolean deleted;
}
