package com.foodXpert.cartservice.repository;

import com.foodXpert.cartservice.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
