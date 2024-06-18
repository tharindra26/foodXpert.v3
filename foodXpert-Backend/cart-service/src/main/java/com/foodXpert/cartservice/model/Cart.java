package com.foodXpert.cartservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "f_carts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    @Id
    private String id;
    private Long userId;
    private String cartName;
    private List<CartItem> items;
    private boolean checkedOut;
    private boolean deleted = false;
}
