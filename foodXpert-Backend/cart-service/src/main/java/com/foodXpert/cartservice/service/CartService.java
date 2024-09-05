package com.foodXpert.cartservice.service;

import com.foodXpert.cartservice.dto.request.AddCartItemRequestDTO;
import com.foodXpert.cartservice.dto.request.CreateCartRequestDTO;
import com.foodXpert.cartservice.dto.response.CartResponseDTO;
import com.foodXpert.cartservice.dto.response.GenericAddOrUpdateResponse;
import com.foodXpert.cartservice.model.Cart;

import java.util.List;

public interface CartService {
    CartResponseDTO createCart(CreateCartRequestDTO createCartRequestDTO);
    GenericAddOrUpdateResponse addItemToCart(AddCartItemRequestDTO addCartItemRequestDTO);
    List<Cart> getCartsByUserEmail(String userEmail);
    CartResponseDTO getCartById(Long cartId);
    boolean deleteCartItem(Long itemId);

}
