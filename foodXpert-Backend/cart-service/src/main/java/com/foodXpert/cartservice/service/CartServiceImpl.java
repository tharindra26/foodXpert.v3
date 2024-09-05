package com.foodXpert.cartservice.service;

import com.foodXpert.cartservice.dto.request.AddCartItemRequestDTO;
import com.foodXpert.cartservice.dto.request.CreateCartRequestDTO;
import com.foodXpert.cartservice.dto.response.CartItemDTO;
import com.foodXpert.cartservice.dto.response.CartResponseDTO;
import com.foodXpert.cartservice.dto.response.GenericAddOrUpdateResponse;
import com.foodXpert.cartservice.model.Cart;
import com.foodXpert.cartservice.model.CartItem;
import com.foodXpert.cartservice.repository.CartItemRepository;
import com.foodXpert.cartservice.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartResponseDTO createCart(CreateCartRequestDTO createCartRequestDTO) {
        Optional<Cart> existingCart = cartRepository.findByCartNameAndUserEmail(createCartRequestDTO.getCartName(), createCartRequestDTO.getUserEmail());

        if (existingCart.isPresent()) {
            throw new IllegalArgumentException("A cart with this name already exists for the user.");
        }

        Cart cart = Cart.builder()
                .userEmail(createCartRequestDTO.getUserEmail())
                .cartName(createCartRequestDTO.getCartName())
                .items(new ArrayList<>())
                .checkedOut(false)
                .build();

        Cart savedCart = cartRepository.save(cart);

        CartResponseDTO response = CartResponseDTO.builder()
                .id(savedCart.getId())
                .userEmail(savedCart.getUserEmail())
                .cartName(savedCart.getCartName())
                .items(savedCart.getItems().stream().map(cartItem -> CartItemDTO.builder()
                        .id(cartItem.getId())
                        .productId(cartItem.getProductId())
                        .quantity(cartItem.getQuantity())
                        .build()).toList())
                .checkedOut(savedCart.isCheckedOut())
                .build();

        return response;
    }

    @Override
    public GenericAddOrUpdateResponse addItemToCart(AddCartItemRequestDTO addCartItemRequestDTO) {
        Cart cart = cartRepository.findById(addCartItemRequestDTO.getCartId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found with ID: " + addCartItemRequestDTO.getCartId()));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(addCartItemRequestDTO.getProductId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(addCartItemRequestDTO.getQuantity());
        } else {
            CartItem cartItem = CartItem.builder()
                    .productId(addCartItemRequestDTO.getProductId())
                    .quantity(addCartItemRequestDTO.getQuantity())
                    .build();
            cart.getItems().add(cartItem);
        }

        cartRepository.save(cart);
        GenericAddOrUpdateResponse response = new GenericAddOrUpdateResponse();
        response.setMessage("Item added to cart successfully.");
        response.setSuccess(true);
        response.setId(cart.getId());
        return  response;
    }

    @Override
    public List<Cart> getCartsByUserEmail(String userEmail) {
        return cartRepository.findByUserEmailAndCheckedOutFalse(userEmail);
    }

    @Override
    public CartResponseDTO getCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            CartResponseDTO response = CartResponseDTO.builder()
                    .id(cart.getId())
                    .userEmail(cart.getUserEmail())
                    .cartName(cart.getCartName())
                    .items(cart.getItems().stream().map(cartItem -> CartItemDTO.builder()
                            .id(cartItem.getId())
                            .productId(cartItem.getProductId())
                            .quantity(cartItem.getQuantity())
                            .build()).toList())
                    .checkedOut(cart.isCheckedOut())
                    .build();
            return response;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteCartItem(Long itemId) {
        // Find the cart item
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found with ID: " + itemId));

        // Find the cart that contains this item
        Optional<Cart> cartOptional = cartRepository.findAll().stream()
                .filter(cart -> cart.getItems().contains(cartItem))
                .findFirst();

        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();

            // Remove the item from the cart
            cart.getItems().remove(cartItem);

            // Save the cart to update the relationship in the database
            cartRepository.save(cart);

            return true;
        } else {
            throw new IllegalArgumentException("No cart found for the cart item with ID: " + itemId);
        }
    }



}
