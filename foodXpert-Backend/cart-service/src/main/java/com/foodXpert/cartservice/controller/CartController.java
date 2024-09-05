package com.foodXpert.cartservice.controller;

import com.foodXpert.cartservice.dto.request.AddCartItemRequestDTO;
import com.foodXpert.cartservice.dto.request.CreateCartRequestDTO;
import com.foodXpert.cartservice.dto.request.UserEmailRequestDTO;
import com.foodXpert.cartservice.dto.response.CartResponseDTO;
import com.foodXpert.cartservice.dto.response.GenericAddOrUpdateResponse;
import com.foodXpert.cartservice.model.Cart;
import com.foodXpert.cartservice.service.CartService;
import com.foodXpert.cartservice.service.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartResponseDTO> createCart(@RequestBody CreateCartRequestDTO createCartRequestDTO) {
        if (createCartRequestDTO.getUserEmail() == null) {
            return ResponseEntity.badRequest().body(null);
        }else {
            return ResponseEntity.ok(cartService.createCart(createCartRequestDTO));
        }
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenericAddOrUpdateResponse> addItemToCart(@RequestBody AddCartItemRequestDTO addCartItemRequestDTO) {
        if (addCartItemRequestDTO.getCartId() == null) {
            return ResponseEntity.badRequest().body(new GenericAddOrUpdateResponse(false, "Cart id is required"));
        }else {
            return ResponseEntity.ok(cartService.addItemToCart(addCartItemRequestDTO));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<List<Cart>> getCartsByUserEmail(
            @RequestParam(value = "userEmail", required = false) String queryUserEmail,
            @RequestBody(required = false) UserEmailRequestDTO userEmailRequest) {

        // Determine the user email source
        String userEmail = queryUserEmail;
        if (userEmail == null || userEmail.isEmpty()) {
            if (userEmailRequest != null) {
                userEmail = userEmailRequest.getUserEmail();
            }
        }

        if (userEmail == null || userEmail.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        } else {
            List<Cart> carts = cartService.getCartsByUserEmail(userEmail);
            return ResponseEntity.ok(carts);
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Long cartId) {
        CartResponseDTO cartResponseDTO = cartService.getCartById(cartId);
        if (cartResponseDTO != null) {
            return ResponseEntity.ok(cartResponseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<GenericAddOrUpdateResponse> deleteCartItem(@PathVariable Long itemId) {
        boolean isDeleted = cartService.deleteCartItem(itemId);
        if (isDeleted) {
            return ResponseEntity.ok(new GenericAddOrUpdateResponse(true, "Item deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GenericAddOrUpdateResponse(false, "Item not found"));
        }
    }

}
