package com.clothing.store.dto.admin;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record InventoryUpdateRequest(@NotNull @PositiveOrZero Integer quantity) {
}
