package com.rks.catalog.service;

import com.rks.catalog.dto.category.CategoryResponse;
import com.rks.catalog.models.category.Category;

import java.util.List;

public interface ICategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getById(String categoryId);
    List<Category> getCategoriesByNameRegex(String searchString);
    Category getByName(String name);
}
