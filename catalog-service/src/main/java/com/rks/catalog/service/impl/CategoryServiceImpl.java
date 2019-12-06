package com.rks.catalog.service.impl;

import com.rks.catalog.dto.category.CategoryResponse;
import com.rks.catalog.exceptions.NotFoundException;
import com.rks.catalog.mapper.CategoryMapper;
import com.rks.catalog.models.category.Category;
import com.rks.catalog.repositories.CategoryRepository;
import com.rks.catalog.service.ICategoryService;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheManager = "catalogCM", cacheNames = "catalogCache")
@Service
public class CategoryServiceImpl implements ICategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

    //private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryResponse getById(String categoryId) {
        log.info("Going to fetch name for name id: {}", categoryId);

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            throw new NotFoundException("Category not found for name id: " + categoryId);
        }
        Category category = optionalCategory.get();
        log.info("Category fetched");
        log.info("name --> {}", category.toString());
        CategoryResponse response = categoryMapper.categoryToCategoryResponse(category);
        log.info(response.toString());
        return response;
    }

    @Cacheable(keyGenerator = "categoryKeyGenerator")
    @Override
    public List<Category> getCategoriesByNameRegex(String searchString) {
        log.info("Going to search categories for: {}", searchString);

        List<Category> categoryList = categoryRepository.findCategoriesByNameRegex(searchString);
        if (categoryList == null) {
            throw new NotFoundException("Category not found for: " + searchString);
        }
        log.info("Category fetched");
        return categoryList;
    }
}
