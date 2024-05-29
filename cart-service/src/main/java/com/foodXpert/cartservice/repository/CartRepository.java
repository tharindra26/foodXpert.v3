package com.foodXpert.cartservice.repository;

import com.foodXpert.cartservice.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart, String> {
    List<Cart> findByUserIdAndCheckedOutFalse(Long userId);
}
