package com.foodXpert.productservice.service;

import com.foodXpert.productservice.dto.ProductAvailabilityResponseDTO;
import com.foodXpert.productservice.dto.ProductRequestDTO;
import com.foodXpert.productservice.dto.ProductResponseDTO;
import com.foodXpert.productservice.model.Category;
import com.foodXpert.productservice.model.Product;
import com.foodXpert.productservice.repository.CategoryRepository;
import com.foodXpert.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j //coming from lombok. to add the logs
public class ProductService {
    private final ProductRepository productRepository;
    private  final CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Long createProduct(ProductRequestDTO productRequest) {
        Category category = categoryRepository.findById(productRequest.getProductCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + productRequest.getProductCategoryId()));

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productCategory(category)
                .productUnit(productRequest.getProductUnit())
                .productPricePerUnit(productRequest.getProductPricePerUnit())
                .quantity(productRequest.getQuantity())
                .deleted(false)
                .build();

        product = productRepository.save(product);
        log.info("Product {} is saved!", product.getId());
        return product.getId();
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll().stream()
                .filter(product -> !product.isDeleted())
                .collect(Collectors.toList());

        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequest) {
        Product existingProduct = productRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        Category category = categoryRepository.findById(productRequest.getProductCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + productRequest.getProductCategoryId()));

        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setProductDescription(productRequest.getProductDescription());
        existingProduct.setProductCategory(category);
        existingProduct.setProductUnit(productRequest.getProductUnit());
        existingProduct.setProductPricePerUnit(productRequest.getProductPricePerUnit());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setDeleted(productRequest.isDeleted());

        productRepository.save(existingProduct);
        log.info("Product {} is updated!", existingProduct.getId());
        return modelMapper.map(existingProduct, ProductResponseDTO.class);
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        product.setDeleted(true);
        productRepository.save(product);
        log.info("Product {} is marked as deleted!", product.getId());
    }

    public ProductAvailabilityResponseDTO checkProductAvailability(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        boolean available = !product.isDeleted() && product.getQuantity() > 0;

        return ProductAvailabilityResponseDTO.builder()
                .available(available)
                .availableQuantity(product.getQuantity())
                .build();
    }
}
