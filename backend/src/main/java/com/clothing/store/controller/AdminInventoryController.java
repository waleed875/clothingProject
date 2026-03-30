package com.clothing.store.controller;

import com.clothing.store.dto.admin.InventoryResponse;
import com.clothing.store.dto.admin.InventoryUpdateRequest;
import com.clothing.store.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminInventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/inventory")
    public List<InventoryResponse> listInventory() {
        return inventoryService.list();
    }

    @GetMapping("/variants/{variantId}/inventory")
    public InventoryResponse getVariantInventory(@PathVariable Long variantId) {
        return inventoryService.getByVariant(variantId);
    }

    @PutMapping("/variants/{variantId}/inventory")
    public InventoryResponse updateVariantInventory(@PathVariable Long variantId, @Valid @RequestBody InventoryUpdateRequest request) {
        return inventoryService.update(variantId, request);
    }
}
