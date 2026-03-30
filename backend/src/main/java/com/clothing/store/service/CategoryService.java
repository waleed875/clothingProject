package com.clothing.store.service;

import com.clothing.store.dto.CategoryResponse;
import com.clothing.store.mapper.CategoryMapper;
import com.clothing.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getActiveCategories() {
        return categoryRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }
}
