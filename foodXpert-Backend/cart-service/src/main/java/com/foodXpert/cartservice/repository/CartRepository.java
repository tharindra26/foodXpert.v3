package com.foodXpert.cartservice.repository;

import com.foodXpert.cartservice.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCartNameAndUserEmail(String cartName, String userEmail);
    List<Cart> findByUserEmailAndCheckedOutFalse(String userEmail);
}
