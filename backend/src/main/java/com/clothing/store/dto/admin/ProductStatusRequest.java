package com.clothing.store.dto.admin;

import com.clothing.store.entity.StatusEnums;
import jakarta.validation.constraints.NotNull;

public record ProductStatusRequest(@NotNull StatusEnums.ProductStatus status) {
}
