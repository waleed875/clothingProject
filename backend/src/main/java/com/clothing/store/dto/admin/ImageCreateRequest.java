package com.clothing.store.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.validator.constraints.URL;

public record ImageCreateRequest(
        @NotBlank @URL String imageUrl,
        @NotBlank String storageKey,
        String altText,
        @NotNull @PositiveOrZero Integer sortOrder,
        boolean isPrimary
) {
}
