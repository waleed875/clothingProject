package com.clothing.store.controller;

import com.clothing.store.dto.ProductDetailsResponse;
import com.clothing.store.dto.ProductListItemResponse;
import com.clothing.store.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/public/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductListItemResponse> getProducts(@RequestParam(required = false) String category) {
        return productService.getProducts(category);
    }

    @GetMapping("/{slug}")
    public ProductDetailsResponse getProductDetails(@PathVariable String slug) {
        return productService.getProductBySlug(slug);
    }
}
