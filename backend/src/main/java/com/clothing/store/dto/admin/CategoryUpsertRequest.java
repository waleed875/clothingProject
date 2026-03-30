package com.clothing.store.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpsertRequest(
        @NotBlank @Size(min = 2, max = 150) String name,
        @NotBlank @Size(min = 2, max = 180) String slug
) {
}
