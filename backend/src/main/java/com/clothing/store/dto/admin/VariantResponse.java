package com.clothing.store.dto.admin;

import java.math.BigDecimal;

public record VariantResponse(Long id,
                              Long productId,
                              String sku,
                              String size,
                              String color,
                              BigDecimal price,
                              String status,
                              Integer quantity) {
}
