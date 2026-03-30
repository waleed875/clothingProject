package com.clothing.store.dto.admin;

public record ImageResponse(Long id,
                            Long productId,
                            Long variantId,
                            String imageUrl,
                            String storageKey,
                            String altText,
                            Integer sortOrder,
                            boolean isPrimary) {
}
