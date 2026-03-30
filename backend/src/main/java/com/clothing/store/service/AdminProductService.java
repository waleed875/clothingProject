package com.clothing.store.service;

import com.clothing.store.dto.ProductDetailsResponse;
import com.clothing.store.dto.admin.ProductStatusRequest;
import com.clothing.store.dto.admin.ProductUpsertRequest;
import com.clothing.store.exception.ConflictException;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.mapper.ProductMapper;
import com.clothing.store.repository.CategoryRepository;
import com.clothing.store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductDetailsResponse create(ProductUpsertRequest request) {
        if (productRepository.existsBySlugIgnoreCase(request.slug())) {
            throw new ConflictException("Product slug already exists");
        }

        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        var product = new com.clothing.store.entity.Product();
        product.setName(request.name());
        product.setSlug(request.slug());
        product.setDescription(request.description());
        product.setStatus(request.status());
        product.setCategory(category);
        return productMapper.toDetails(productRepository.save(product));
    }

    public ProductDetailsResponse update(Long id, ProductUpsertRequest request) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (productRepository.existsBySlugIgnoreCaseAndIdNot(request.slug(), id)) {
            throw new ConflictException("Product slug already exists");
        }

        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setName(request.name());
        product.setSlug(request.slug());
        product.setDescription(request.description());
        product.setStatus(request.status());
        product.setCategory(category);

        return productMapper.toDetails(productRepository.save(product));
    }

    public ProductDetailsResponse updateStatus(Long id, ProductStatusRequest request) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setStatus(request.status());
        return productMapper.toDetails(productRepository.save(product));
    }
}
