package com.rks.catalog.service.impl;

import com.rks.catalog.dto.category.CategoryResponse;
import com.rks.catalog.mapper.CategoryMapper;
import com.rks.catalog.models.category.Category;
import com.rks.catalog.repositories.CategoryRepository;
import com.rks.catalog.service.ICategoryService;
import com.rks.mcommon.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.rks.catalog.constants.Constants.CATEGORY_NOT_FOUND_ERR_MSG;
import static com.rks.mcommon.constants.CommonConstants.FAILED;
import static com.rks.mcommon.constants.CommonErrorCodeConstants.NOT_FOUND_ERROR_CODE;

@CacheConfig(cacheManager = "catalogCM", cacheNames = "catalogCache")
@Service
public class CategoryServiceImpl implements ICategoryService {

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    //private final CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Value("${memcached.isEnabled}")
    private String cachingEnabled;

    @Override
    public CategoryResponse getById(String categoryId) {
        log.info("Going to fetch name for name id: {}", categoryId);

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            throw new NotFoundException(FAILED, NOT_FOUND_ERROR_CODE, CATEGORY_NOT_FOUND_ERR_MSG);
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
            throw new NotFoundException(FAILED, NOT_FOUND_ERROR_CODE, "Category not found for: " + searchString);
        }
        log.info("Category fetched. Updating mostViewCategories data in cache");
        return categoryList;
    }

    @Cacheable(key="#name")
    @Override
    public Category getByName(String name) {
        log.info("Going to search categories for: {}", name);
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            throw new NotFoundException(FAILED, NOT_FOUND_ERROR_CODE, CATEGORY_NOT_FOUND_ERR_MSG);
        }
        return category;
    }
}
