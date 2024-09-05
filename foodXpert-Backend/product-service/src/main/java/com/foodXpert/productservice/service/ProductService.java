package com.foodXpert.productservice.service;

import com.foodXpert.productservice.dto.responses.products.ProductAvailabilityResponseDTO;
import com.foodXpert.productservice.dto.requests.ProductRequestDTO;
import com.foodXpert.productservice.dto.responses.products.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    public Long createProduct(ProductRequestDTO productRequest);
    public List<ProductResponseDTO> getAllProducts();
    public ProductResponseDTO getProductById(Long id);
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequest);
    public void deleteProduct(Long id);
    public ProductAvailabilityResponseDTO checkProductAvailability(Long productId);
}
