package com.clothing.store.dto.admin;

import jakarta.validation.constraints.NotNull;

public record ImageSortOrderRequest(@NotNull Integer sortOrder) {
}
