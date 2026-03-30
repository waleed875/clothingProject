package com.clothing.store.controller;

import com.clothing.store.dto.admin.*;
import com.clothing.store.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping("/api/v1/admin/products/{id}/images")
    public ImageResponse addProductImage(@PathVariable Long id, @Valid @RequestBody ImageCreateRequest request) {
        return productImageService.addProductImage(id, request);
    }

    @PostMapping("/api/v1/admin/variants/{variantId}/images")
    public ImageResponse addVariantImage(@PathVariable Long variantId, @Valid @RequestBody ImageCreateRequest request) {
        return productImageService.addVariantImage(variantId, request);
    }

    @GetMapping("/api/v1/products/{id}/images")
    public List<ImageResponse> getProductImages(@PathVariable Long id) {
        return productImageService.getProductImages(id);
    }

    @DeleteMapping("/api/v1/admin/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        productImageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/api/v1/admin/images/{imageId}/primary")
    public ImageResponse updatePrimary(@PathVariable Long imageId, @Valid @RequestBody ImagePrimaryRequest request) {
        return productImageService.updatePrimary(imageId, request);
    }

    @PatchMapping("/api/v1/admin/images/{imageId}/sort-order")
    public ImageResponse updateSortOrder(@PathVariable Long imageId, @Valid @RequestBody ImageSortOrderRequest request) {
        return productImageService.updateSortOrder(imageId, request);
    }
}
