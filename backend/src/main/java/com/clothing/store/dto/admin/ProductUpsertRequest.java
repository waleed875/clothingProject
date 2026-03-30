package com.clothing.store.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductUpsertRequest(
        @NotBlank @Size(min = 2, max = 255) String name,
        @NotBlank @Size(min = 2, max = 300) String slug,
        @Size(max = 3000) String description,
        @NotNull Long categoryId,
        @NotNull com.clothing.store.entity.StatusEnums.ProductStatus status
) {
}
