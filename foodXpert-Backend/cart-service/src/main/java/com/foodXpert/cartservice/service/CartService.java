package com.foodXpert.cartservice.service;

import com.foodXpert.cartservice.client.ProductClient;
import com.foodXpert.cartservice.dto.AddCartItemRequestDTO;
import com.foodXpert.cartservice.dto.CreateCartRequestDTO;
import com.foodXpert.cartservice.dto.ProductAvailabilityResponseDTO;
import com.foodXpert.cartservice.model.Cart;
import com.foodXpert.cartservice.model.CartItem;
import com.foodXpert.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductClient productClient;

    public Cart createCart(CreateCartRequestDTO createCartRequestDTO) {
        Cart cart = Cart.builder()
                .userId(createCartRequestDTO.getUserId())
                .cartName(createCartRequestDTO.getCartName())
                .items(new ArrayList<>())
                .checkedOut(false)
                .build();
        return cartRepository.save(cart);
    }

    public Cart addItemToCart(AddCartItemRequestDTO addCartItemRequestDTO) {
        // Check product availability
        ProductAvailabilityResponseDTO productAvailability = productClient.checkProductAvailability(addCartItemRequestDTO.getProductId());

        if (productAvailability == null || !productAvailability.isAvailable() || productAvailability.getAvailableQuantity() < addCartItemRequestDTO.getQuantity()) {
            throw new IllegalArgumentException("Product not available or insufficient quantity");
        }

        // Add item to cart
        Cart cart = cartRepository.findById(addCartItemRequestDTO.getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + addCartItemRequestDTO.getCartId()));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(addCartItemRequestDTO.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + addCartItemRequestDTO.getQuantity());
        } else {
            CartItem cartItem = CartItem.builder()
                    .productId(addCartItemRequestDTO.getProductId())
                    .quantity(addCartItemRequestDTO.getQuantity())
                    .build();
            cart.getItems().add(cartItem);
        }

        return cartRepository.save(cart);
    }

    public Cart getCartById(String id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + id));
    }

    public List<Cart> getCartsByUserId(Long userId) {
        return cartRepository.findByUserIdAndCheckedOutFalse(userId);
    }
}
