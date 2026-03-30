package com.clothing.store.mapper;

import com.clothing.store.dto.CategoryResponse;
import com.clothing.store.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponse toResponse(Category category);
}
