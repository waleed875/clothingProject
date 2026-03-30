package com.clothing.store.service;

import com.clothing.store.dto.admin.VariantResponse;
import com.clothing.store.dto.admin.VariantStatusRequest;
import com.clothing.store.dto.admin.VariantUpsertRequest;
import com.clothing.store.entity.Inventory;
import com.clothing.store.exception.ConflictException;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.repository.InventoryRepository;
import com.clothing.store.repository.ProductRepository;
import com.clothing.store.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariantService {
    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public List<VariantResponse> getByProduct(Long productId) {
        return variantRepository.findByProductId(productId).stream().map(this::toResponse).toList();
    }

    @Transactional
    public VariantResponse create(Long productId, VariantUpsertRequest request) {
        var product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        validateVariant(request.sku(), request.size(), request.color(), productId, null);

        var variant = new com.clothing.store.entity.ProductVariant();
        variant.setProduct(product);
        variant.setSku(request.sku());
        variant.setSize(request.size());
        variant.setColor(request.color());
        variant.setPrice(request.price());
        variant.setStatus(request.status());
        var saved = variantRepository.save(variant);

        var inventory = new Inventory();
        inventory.setVariant(saved);
        inventory.setQuantity(request.quantity());
        inventory.setReservedQuantity(0);
        inventoryRepository.save(inventory);

        return toResponse(saved);
    }

    public VariantResponse update(Long variantId, VariantUpsertRequest request) {
        var variant = variantRepository.findById(variantId).orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
        validateVariant(request.sku(), request.size(), request.color(), variant.getProduct().getId(), variantId);

        variant.setSku(request.sku());
        variant.setSize(request.size());
        variant.setColor(request.color());
        variant.setPrice(request.price());
        variant.setStatus(request.status());

        var inventory = inventoryRepository.findByVariantId(variantId).orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        inventory.setQuantity(request.quantity());
        inventoryRepository.save(inventory);

        return toResponse(variantRepository.save(variant));
    }

    public VariantResponse updateStatus(Long variantId, VariantStatusRequest request) {
        var variant = variantRepository.findById(variantId).orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
        variant.setStatus(request.status());
        return toResponse(variantRepository.save(variant));
    }

    private void validateVariant(String sku, String size, String color, Long productId, Long variantId) {
        if (variantId == null) {
            if (variantRepository.existsBySkuIgnoreCase(sku)) {
                throw new ConflictException("SKU already exists");
            }
            if (variantRepository.existsByProductIdAndSizeIgnoreCaseAndColorIgnoreCase(productId, size, color)) {
                throw new ConflictException("Variant combination already exists for this product");
            }
        } else {
            if (variantRepository.existsBySkuIgnoreCaseAndIdNot(sku, variantId)) {
                throw new ConflictException("SKU already exists");
            }
            if (variantRepository.existsByProductIdAndSizeIgnoreCaseAndColorIgnoreCaseAndIdNot(productId, size, color, variantId)) {
                throw new ConflictException("Variant combination already exists for this product");
            }
        }
    }

    private VariantResponse toResponse(com.clothing.store.entity.ProductVariant variant) {
        var inventory = inventoryRepository.findByVariantId(variant.getId()).orElse(null);
        return new VariantResponse(
                variant.getId(),
                variant.getProduct().getId(),
                variant.getSku(),
                variant.getSize(),
                variant.getColor(),
                variant.getPrice(),
                variant.getStatus().name(),
                inventory != null ? inventory.getQuantity() : 0
        );
    }
}
