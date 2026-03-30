package com.clothing.store.dto.admin;

public record InventoryResponse(Long variantId, String sku, Long productId, Integer quantity, Integer reservedQuantity) {
}
