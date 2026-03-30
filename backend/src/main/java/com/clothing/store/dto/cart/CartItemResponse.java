package com.clothing.store.dto.cart;

import java.math.BigDecimal;

public record CartItemResponse(Long itemId,
                               Long variantId,
                               String productName,
                               String size,
                               String color,
                               String image,
                               BigDecimal unitPrice,
                               Integer quantity,
                               BigDecimal lineTotal) {
}
