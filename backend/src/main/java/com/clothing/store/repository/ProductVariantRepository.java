package com.clothing.store.repository;

import com.clothing.store.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProductId(Long productId);
    boolean existsBySkuIgnoreCase(String sku);
    boolean existsBySkuIgnoreCaseAndIdNot(String sku, Long id);
    boolean existsByProductIdAndSizeIgnoreCaseAndColorIgnoreCase(Long productId, String size, String color);
    boolean existsByProductIdAndSizeIgnoreCaseAndColorIgnoreCaseAndIdNot(Long productId, String size, String color, Long id);
    Optional<ProductVariant> findByIdAndProductId(Long id, Long productId);
}
