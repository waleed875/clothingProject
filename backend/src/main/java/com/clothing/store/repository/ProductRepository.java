package com.clothing.store.repository;

import com.clothing.store.entity.Product;
import com.clothing.store.entity.StatusEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(StatusEnums.ProductStatus status);
    List<Product> findByCategorySlugAndStatus(String categorySlug, StatusEnums.ProductStatus status);
    Optional<Product> findBySlugAndStatus(String slug, StatusEnums.ProductStatus status);
    boolean existsBySlugIgnoreCase(String slug);
    boolean existsBySlugIgnoreCaseAndIdNot(String slug, Long id);
}
