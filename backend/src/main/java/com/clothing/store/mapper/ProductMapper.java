package com.clothing.store.mapper;

import com.clothing.store.dto.ProductDetailsResponse;
import com.clothing.store.dto.ProductListItemResponse;
import com.clothing.store.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "categorySlug", source = "category.slug")
    @Mapping(target = "status", expression = "java(product.getStatus().name())")
    ProductListItemResponse toListItem(Product product);

    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "categorySlug", source = "category.slug")
    @Mapping(target = "status", expression = "java(product.getStatus().name())")
    ProductDetailsResponse toDetails(Product product);
}
