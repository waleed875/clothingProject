package com.clothing.store.dto;

public record ProductDetailsResponse(Long id,
                                     String name,
                                     String slug,
                                     String description,
                                     String categoryName,
                                     String categorySlug,
                                     String status) {
}
