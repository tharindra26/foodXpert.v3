package com.foodXpert.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "product_categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryName;
    private String categoryDescription;

    @OneToMany(mappedBy = "productCategory")
    private List<Product> products;
}
