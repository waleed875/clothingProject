package com.clothing.store.repository;

import com.clothing.store.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findFirstByVariantIdOrderByPrimaryDescSortOrderAsc(Long variantId);
    Optional<ProductImage> findFirstByProductIdOrderByPrimaryDescSortOrderAsc(Long productId);
    List<ProductImage> findByProductIdOrderBySortOrderAsc(Long productId);
    List<ProductImage> findByVariantIdOrderBySortOrderAsc(Long variantId);
}
