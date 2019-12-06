package com.rks.catalog.controllers;

import com.rks.catalog.cachedData.CategoryTemp;
import com.rks.catalog.cachedData.MostViewedCategories;
import com.rks.catalog.dto.category.CategoryResponse;
import com.rks.catalog.models.category.Category;
import com.rks.catalog.repositories.CategoryRepository;
import com.rks.catalog.service.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/catalog/ext")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private MostViewedCategories mostViewedCategories;

    @PostMapping("/v1/category")
    @ResponseStatus(HttpStatus.CREATED)
    public Category add(@RequestBody Category c) {
        return categoryRepository.save(c);
    }

    @GetMapping("/v1/category/{categoryId}")
    public CategoryResponse getCategoryById(@PathVariable("categoryId") String categoryId) {
        return categoryService.getById(categoryId);
    }

    @GetMapping("/v1/category/search/nameRegex/{searchString}")
    public List<Category> getCategoriesByNameRegex(@PathVariable("searchString") String searchString) {
        List categoryList = categoryService.getCategoriesByNameRegex(searchString);
        log.info("Fetched category list for search string: {}", searchString);
        categoryList.forEach(category -> mostViewedCategories.addViewCount(((Category)category).getName(),1));
        log.info("Updating mostViewedCategories data in cache. mostViewedCategories: {}", mostViewedCategories.toString());
        return categoryList;
    }

    @GetMapping("/v1/category/search/name/{name}")
    public Category getCategoriesByName(@PathVariable("name") String name) {
        Category category = categoryService.getByName(name);
        log.info("Updating mostViewedCategories data in cache");
        mostViewedCategories.addViewCount(category.getName(),1);
        log.info("mostViewedCategories: {}", mostViewedCategories);
        return category;
    }

}
