package com.foodXpert.productservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category productCategory;

    @Column(nullable = false)
    private String productUnit;

    @Column(nullable = false)
    private BigDecimal productPricePerUnit;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private boolean deleted;
}
