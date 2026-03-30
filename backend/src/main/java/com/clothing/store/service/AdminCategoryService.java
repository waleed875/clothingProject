package com.clothing.store.service;

import com.clothing.store.dto.CategoryResponse;
import com.clothing.store.dto.admin.CategoryStatusRequest;
import com.clothing.store.dto.admin.CategoryUpsertRequest;
import com.clothing.store.exception.ConflictException;
import com.clothing.store.exception.ResourceNotFoundException;
import com.clothing.store.mapper.CategoryMapper;
import com.clothing.store.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse create(CategoryUpsertRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new ConflictException("Category name already exists");
        }

        var category = new com.clothing.store.entity.Category();
        category.setName(request.name());
        category.setSlug(request.slug());
        category.setActive(true);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    public CategoryResponse update(Long id, CategoryUpsertRequest request) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if (categoryRepository.existsByNameIgnoreCaseAndIdNot(request.name(), id)) {
            throw new ConflictException("Category name already exists");
        }
        category.setName(request.name());
        category.setSlug(request.slug());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    public CategoryResponse updateStatus(Long id, CategoryStatusRequest request) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        category.setActive(request.active());
        return categoryMapper.toResponse(categoryRepository.save(category));
    }
}
