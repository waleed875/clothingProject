package com.clothing.store.controller;

import com.clothing.store.dto.CategoryResponse;
import com.clothing.store.dto.admin.CategoryStatusRequest;
import com.clothing.store.dto.admin.CategoryUpsertRequest;
import com.clothing.store.service.AdminCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/categories")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CategoryUpsertRequest request) {
        return adminCategoryService.create(request);
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryUpsertRequest request) {
        return adminCategoryService.update(id, request);
    }

    @PatchMapping("/{id}/status")
    public CategoryResponse updateStatus(@PathVariable Long id, @Valid @RequestBody CategoryStatusRequest request) {
        return adminCategoryService.updateStatus(id, request);
    }
}
