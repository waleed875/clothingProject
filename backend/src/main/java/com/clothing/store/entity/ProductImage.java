package com.clothing.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "product_images")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "storage_key", nullable = false)
    private String storageKey;

    @Column(name = "alt_text")
    private String altText;

    @Column(name = "is_primary", nullable = false)
    private Boolean primary;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;
}
