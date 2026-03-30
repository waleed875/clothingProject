package com.clothing.store.service;

import com.clothing.store.dto.admin.*;
import com.clothing.store.entity.ProductImage;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.repository.ProductImageRepository;
import com.clothing.store.repository.ProductRepository;
import com.clothing.store.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {
    private final ProductRepository productRepository;
    private final ProductVariantRepository variantRepository;
    private final ProductImageRepository imageRepository;

    @Transactional
    public ImageResponse addProductImage(Long productId, ImageCreateRequest request) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (request.isPrimary()) {
            clearPrimaryForProduct(productId);
        }

        var image = new ProductImage();
        image.setProduct(product);
        image.setImageUrl(request.imageUrl());
        image.setStorageKey(request.storageKey());
        image.setAltText(request.altText());
        image.setSortOrder(request.sortOrder());
        image.setPrimary(request.isPrimary());
        return toResponse(imageRepository.save(image));
    }

    @Transactional
    public ImageResponse addVariantImage(Long variantId, ImageCreateRequest request) {
        var variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found"));

        if (request.isPrimary()) {
            clearPrimaryForVariant(variantId);
        }

        var image = new ProductImage();
        image.setVariant(variant);
        image.setImageUrl(request.imageUrl());
        image.setStorageKey(request.storageKey());
        image.setAltText(request.altText());
        image.setSortOrder(request.sortOrder());
        image.setPrimary(request.isPrimary());
        return toResponse(imageRepository.save(image));
    }

    public List<ImageResponse> getProductImages(Long productId) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        var images = new ArrayList<>(imageRepository.findByProductIdOrderBySortOrderAsc(productId));
        variantRepository.findByProductId(product.getId())
                .forEach(v -> images.addAll(imageRepository.findByVariantIdOrderBySortOrderAsc(v.getId())));

        return images.stream().map(this::toResponse).toList();
    }

    public void deleteImage(Long imageId) {
        var image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
        imageRepository.delete(image);
    }

    @Transactional
    public ImageResponse updatePrimary(Long imageId, ImagePrimaryRequest request) {
        var image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));

        if (request.isPrimary()) {
            if (image.getProduct() != null) {
                clearPrimaryForProduct(image.getProduct().getId());
            }
            if (image.getVariant() != null) {
                clearPrimaryForVariant(image.getVariant().getId());
            }
        }

        image.setPrimary(request.isPrimary());
        return toResponse(imageRepository.save(image));
    }

    public ImageResponse updateSortOrder(Long imageId, ImageSortOrderRequest request) {
        var image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
        image.setSortOrder(request.sortOrder());
        return toResponse(imageRepository.save(image));
    }

    private void clearPrimaryForProduct(Long productId) {
        imageRepository.findByProductIdOrderBySortOrderAsc(productId).forEach(img -> {
            if (Boolean.TRUE.equals(img.getPrimary())) {
                img.setPrimary(false);
                imageRepository.save(img);
            }
        });
    }

    private void clearPrimaryForVariant(Long variantId) {
        imageRepository.findByVariantIdOrderBySortOrderAsc(variantId).forEach(img -> {
            if (Boolean.TRUE.equals(img.getPrimary())) {
                img.setPrimary(false);
                imageRepository.save(img);
            }
        });
    }

    private ImageResponse toResponse(ProductImage image) {
        return new ImageResponse(
                image.getId(),
                image.getProduct() != null ? image.getProduct().getId() : null,
                image.getVariant() != null ? image.getVariant().getId() : null,
                image.getImageUrl(),
                image.getStorageKey(),
                image.getAltText(),
                image.getSortOrder(),
                Boolean.TRUE.equals(image.getPrimary())
        );
    }
}
