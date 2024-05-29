package com.foodXpert.cartservice.controller;

import com.foodXpert.cartservice.dto.AddCartItemRequestDTO;
import com.foodXpert.cartservice.dto.CreateCartRequestDTO;
import com.foodXpert.cartservice.model.Cart;
import com.foodXpert.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cart createCart(@RequestBody CreateCartRequestDTO createCartRequestDTO) {
        return cartService.createCart(createCartRequestDTO);
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public Cart addItemToCart(@RequestBody AddCartItemRequestDTO addCartItemRequestDTO) {
        return cartService.addItemToCart(addCartItemRequestDTO);
    }

    @GetMapping("/{id}")
    public Cart getCartById(@PathVariable String id) {
        return cartService.getCartById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Cart> getCartsByUserId(@PathVariable Long userId) {
        return cartService.getCartsByUserId(userId);
    }

}
