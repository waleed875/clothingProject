package com.clothing.store.controller;

import com.clothing.store.dto.cart.AddCartItemRequest;
import com.clothing.store.dto.cart.CartResponse;
import com.clothing.store.dto.cart.UpdateCartItemRequest;
import com.clothing.store.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public CartResponse getCart() {
        return cartService.getMyCart();
    }

    @PostMapping("/items")
    public CartResponse addItem(@Valid @RequestBody AddCartItemRequest request) {
        return cartService.addItem(request);
    }

    @PutMapping("/items/{itemId}")
    public CartResponse updateItem(@PathVariable Long itemId, @Valid @RequestBody UpdateCartItemRequest request) {
        return cartService.updateItem(itemId, request);
    }

    @DeleteMapping("/items/{itemId}")
    public CartResponse deleteItem(@PathVariable Long itemId) {
        return cartService.removeItem(itemId);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clear() {
        cartService.clear();
        return ResponseEntity.noContent().build();
    }
}
