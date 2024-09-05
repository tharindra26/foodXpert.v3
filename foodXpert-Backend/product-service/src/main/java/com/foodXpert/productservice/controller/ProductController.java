package com.foodXpert.productservice.controller;

import com.foodXpert.productservice.dto.responses.products.ProductAvailabilityResponseDTO;
import com.foodXpert.productservice.dto.requests.ProductRequestDTO;
import com.foodXpert.productservice.dto.responses.products.ProductResponseDTO;
import com.foodXpert.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@CrossOrigin
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long createProduct(@RequestBody ProductRequestDTO productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponseDTO updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/{productId}/availability")
    public ProductAvailabilityResponseDTO checkProductAvailability(@PathVariable Long productId) {
        return productService.checkProductAvailability(productId);
    }
}
