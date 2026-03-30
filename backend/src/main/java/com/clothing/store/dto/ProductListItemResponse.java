package com.clothing.store.dto;

public record ProductListItemResponse(Long id, String name, String slug, String categorySlug, String status) {
}
