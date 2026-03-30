package com.clothing.store.service;

import com.clothing.store.dto.ProductDetailsResponse;
import com.clothing.store.dto.ProductListItemResponse;
import com.clothing.store.entity.StatusEnums;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.mapper.ProductMapper;
import com.clothing.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductListItemResponse> getProducts(String categorySlug) {
        var products = (categorySlug == null || categorySlug.isBlank())
                ? productRepository.findByStatus(StatusEnums.ProductStatus.ACTIVE)
                : productRepository.findByCategorySlugAndStatus(categorySlug, StatusEnums.ProductStatus.ACTIVE);

        return products.stream().map(productMapper::toListItem).toList();
    }

    public ProductDetailsResponse getProductBySlug(String slug) {
        return productRepository.findBySlugAndStatus(slug, StatusEnums.ProductStatus.ACTIVE)
                .map(productMapper::toDetails)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + slug));
    }
}
