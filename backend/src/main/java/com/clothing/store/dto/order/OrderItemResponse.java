package com.clothing.store.dto.order;

import java.math.BigDecimal;

public record OrderItemResponse(Long id,
                                Long variantId,
                                String productName,
                                String sku,
                                String size,
                                String color,
                                BigDecimal unitPrice,
                                Integer quantity,
                                BigDecimal totalPrice) {
}
