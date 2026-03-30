package com.clothing.store.service;

import com.clothing.store.dto.cart.*;
import com.clothing.store.entity.Cart;
import com.clothing.store.entity.CartItem;
import com.clothing.store.entity.StatusEnums;
import com.clothing.store.exception.ConflictException;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CurrentUserService currentUserService;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductVariantRepository variantRepository;
    private final InventoryRepository inventoryRepository;
    private final ProductImageRepository productImageRepository;

    @Transactional
    public CartResponse getMyCart() {
        var cart = getOrCreateCart();
        return toResponse(cart);
    }

    @Transactional
    public CartResponse addItem(AddCartItemRequest request) {
        var cart = getOrCreateCart();
        var variant = variantRepository.findById(request.variantId())
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));

        validateVariantAvailable(variant, request.quantity());

        var existing = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variant.getId());
        if (existing.isPresent()) {
            var item = existing.get();
            int newQty = item.getQuantity() + request.quantity();
            validateVariantAvailable(variant, newQty);
            item.setQuantity(newQty);
            cartItemRepository.save(item);
        } else {
            var item = new CartItem();
            item.setCart(cart);
            item.setVariant(variant);
            item.setQuantity(request.quantity());
            cartItemRepository.save(item);
        }

        return toResponse(cart);
    }

    @Transactional
    public CartResponse updateItem(Long itemId, UpdateCartItemRequest request) {
        var cart = getOrCreateCart();
        var item = cartItemRepository.findByIdAndCartId(itemId, cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        validateVariantAvailable(item.getVariant(), request.quantity());
        item.setQuantity(request.quantity());
        cartItemRepository.save(item);
        return toResponse(cart);
    }

    @Transactional
    public CartResponse removeItem(Long itemId) {
        var cart = getOrCreateCart();
        var item = cartItemRepository.findByIdAndCartId(itemId, cart.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        cartItemRepository.delete(item);
        return toResponse(cart);
    }

    @Transactional
    public void clear() {
        var cart = getOrCreateCart();
        cartItemRepository.deleteByCartId(cart.getId());
    }

    public Cart getOrCreateCart() {
        var user = currentUserService.getCurrentUser();
        return cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            var cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    public CartResponse toResponse(Cart cart) {
        var items = cartItemRepository.findByCartId(cart.getId()).stream().map(item -> {
            var variant = item.getVariant();
            var product = variant.getProduct();
            var image = productImageRepository.findFirstByVariantIdOrderByPrimaryDescSortOrderAsc(variant.getId())
                    .or(() -> productImageRepository.findFirstByProductIdOrderByPrimaryDescSortOrderAsc(product.getId()))
                    .map(com.clothing.store.entity.ProductImage::getImageUrl)
                    .orElse(null);

            var unitPrice = variant.getPrice();
            var lineTotal = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));

            return new CartItemResponse(
                    item.getId(),
                    variant.getId(),
                    product.getName(),
                    variant.getSize(),
                    variant.getColor(),
                    image,
                    unitPrice,
                    item.getQuantity(),
                    lineTotal
            );
        }).toList();

        var total = items.stream().map(CartItemResponse::lineTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartResponse(cart.getId(), items, total);
    }

    private void validateVariantAvailable(com.clothing.store.entity.ProductVariant variant, int quantity) {
        if (variant.getStatus() != StatusEnums.ProductStatus.ACTIVE) {
            throw new ConflictException("Variant is not available");
        }
        var inventory = inventoryRepository.findByVariantId(variant.getId())
                .orElseThrow(() -> new ConflictException("Inventory not found for variant"));

        int available = inventory.getQuantity() - inventory.getReservedQuantity();
        if (available < quantity) {
            throw new ConflictException("Requested quantity exceeds available inventory");
        }
    }
}
