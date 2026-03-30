package com.clothing.store.service;

import com.clothing.store.dto.admin.InventoryResponse;
import com.clothing.store.dto.admin.InventoryUpdateRequest;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public List<InventoryResponse> list() {
        return inventoryRepository.findAll().stream().map(this::toResponse).toList();
    }

    public InventoryResponse getByVariant(Long variantId) {
        var inventory = inventoryRepository.findByVariantId(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        return toResponse(inventory);
    }

    public InventoryResponse update(Long variantId, InventoryUpdateRequest request) {
        var inventory = inventoryRepository.findByVariantId(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        inventory.setQuantity(request.quantity());
        return toResponse(inventoryRepository.save(inventory));
    }

    private InventoryResponse toResponse(com.clothing.store.entity.Inventory inventory) {
        return new InventoryResponse(
                inventory.getVariant().getId(),
                inventory.getVariant().getSku(),
                inventory.getVariant().getProduct().getId(),
                inventory.getQuantity(),
                inventory.getReservedQuantity()
        );
    }
}
