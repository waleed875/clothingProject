package com.clothing.store.controller;

import com.clothing.store.dto.admin.VariantResponse;
import com.clothing.store.dto.admin.VariantStatusRequest;
import com.clothing.store.dto.admin.VariantUpsertRequest;
import com.clothing.store.service.VariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class VariantController {
    private final VariantService variantService;

    @GetMapping("/api/v1/products/{id}/variants")
    public List<VariantResponse> getProductVariants(@PathVariable Long id) {
        return variantService.getByProduct(id);
    }

    @PostMapping("/api/v1/admin/products/{id}/variants")
    public VariantResponse createVariant(@PathVariable Long id, @Valid @RequestBody VariantUpsertRequest request) {
        return variantService.create(id, request);
    }

    @PutMapping("/api/v1/admin/variants/{variantId}")
    public VariantResponse updateVariant(@PathVariable Long variantId, @Valid @RequestBody VariantUpsertRequest request) {
        return variantService.update(variantId, request);
    }

    @PatchMapping("/api/v1/admin/variants/{variantId}/status")
    public VariantResponse updateStatus(@PathVariable Long variantId, @Valid @RequestBody VariantStatusRequest request) {
        return variantService.updateStatus(variantId, request);
    }
}
