package com.clothing.store.dto.order;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(@NotNull Long addressId) {
}
