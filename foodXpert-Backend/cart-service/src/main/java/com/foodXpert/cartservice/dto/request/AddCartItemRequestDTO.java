package com.foodXpert.cartservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemRequestDTO {
    private Long cartId;
    private String productId;
    private int quantity;
}
