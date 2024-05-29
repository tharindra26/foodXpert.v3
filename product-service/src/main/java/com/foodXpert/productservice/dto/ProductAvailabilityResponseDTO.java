package com.foodXpert.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductAvailabilityResponseDTO {
    private boolean available;
    private int availableQuantity;
}
