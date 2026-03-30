package com.clothing.store.dto.admin;

import com.clothing.store.entity.StatusEnums;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record VariantUpsertRequest(
        @NotBlank @Size(max = 100) String sku,
        @NotBlank @Size(max = 30) String size,
        @NotBlank @Size(max = 60) String color,
        @NotNull @DecimalMin(value = "0.01") BigDecimal price,
        @NotNull StatusEnums.ProductStatus status,
        @NotNull @PositiveOrZero Integer quantity
) {
}
