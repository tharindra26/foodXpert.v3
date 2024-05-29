package com.foodXpert.productservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(value = "f_products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    private String id;
    private String productName;
    private String productDescription;
    @DBRef
    private List<Category> productCategories;
    private String productUnit;
    private BigDecimal productPricePerUnit;
    private int quantity;
    private boolean deleted;
}
