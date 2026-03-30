package com.clothing.store.dto.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(Long id,
                            String orderNumber,
                            String status,
                            Long addressId,
                            BigDecimal subtotal,
                            BigDecimal shippingCost,
                            BigDecimal totalAmount,
                            List<OrderItemResponse> items) {
}
