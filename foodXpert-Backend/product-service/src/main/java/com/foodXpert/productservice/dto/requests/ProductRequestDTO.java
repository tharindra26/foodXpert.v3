package com.foodXpert.productservice.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private String productName;
    private String productDescription;
    private Long productCategoryId;
    private String productUnit;
    private BigDecimal productPricePerUnit;
    private int quantity;
    private String productImagePath;
    private boolean deleted;

}
