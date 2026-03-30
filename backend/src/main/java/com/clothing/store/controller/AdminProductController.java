package com.clothing.store.controller;

import com.clothing.store.dto.ProductDetailsResponse;
import com.clothing.store.dto.admin.ProductStatusRequest;
import com.clothing.store.dto.admin.ProductUpsertRequest;
import com.clothing.store.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {
    private final AdminProductService adminProductService;

    @PostMapping
    public ProductDetailsResponse create(@Valid @RequestBody ProductUpsertRequest request) {
        return adminProductService.create(request);
    }

    @PutMapping("/{id}")
    public ProductDetailsResponse update(@PathVariable Long id, @Valid @RequestBody ProductUpsertRequest request) {
        return adminProductService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public ProductDetailsResponse updateStatus(@PathVariable Long id, @Valid @RequestBody ProductStatusRequest request) {
        return adminProductService.updateStatus(id, request);
    }
}
