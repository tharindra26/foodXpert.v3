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
    private final ProductRepository repo;
    private  final CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public String createProduct(ProductRequestDTO productRequest) {
        List<Category> categories = productRequest.getProductCategories().stream()
                .map(category -> categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + category.getId())))
                .toList();

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productCategories(categories)
                .productUnit(productRequest.getProductUnit())
                .productPricePerUnit(productRequest.getProductPricePerUnit())
                .quantity(productRequest.getQuantity())
                .deleted(false)
                .build();

        repo.save(product);
        log.info("Product {} is saved!", product.getId());
        //return the created product id
        return product.getId();
    }

    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = repo.findAll().stream()
                .filter(product -> !product.isDeleted())
                .collect(Collectors.toList());

        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDTO.class))
                .toList();
    }

    public ProductResponseDTO getProductById(String id) {
        Product product = repo.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public ProductResponseDTO updateProduct(String id, ProductRequestDTO productRequest) {
        Product existingProduct = repo.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));

        List<Category> categories = productRequest.getProductCategories().stream()
                .map(category -> categoryRepository.findById(category.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + category.getId())))
                .toList();

        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setProductDescription(productRequest.getProductDescription());
        existingProduct.setProductCategories(categories);
        existingProduct.setProductUnit(productRequest.getProductUnit());
        existingProduct.setProductPricePerUnit(productRequest.getProductPricePerUnit());

        repo.save(existingProduct);
        log.info("Product {} is updated!", existingProduct.getId());
        return modelMapper.map(existingProduct, ProductResponseDTO.class);
    }

    public void deleteProduct(String id) {
        Product product = repo.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
        product.setDeleted(true);
        repo.save(product);
        log.info("Product {} is marked as deleted!", product.getId());
    }

    public ProductAvailabilityResponseDTO checkProductAvailability(String productId) {
        Product product = repo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        boolean available = !product.isDeleted() && product.getQuantity() > 0;

        return ProductAvailabilityResponseDTO.builder()
                .available(available)
                .availableQuantity(product.getQuantity())
                .build();
    }
}
