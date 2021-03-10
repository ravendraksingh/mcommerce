package com.rks.catalog.controllers;

import com.rks.catalog.cachedData.CategoryTemp;
import com.rks.catalog.cachedData.MostViewedCategories;
import com.rks.catalog.dto.category.CategoryResponse;
import com.rks.catalog.exceptions.CategoryNotFoundException;
import com.rks.catalog.models.category.Category;
import com.rks.catalog.repositories.CategoryRepository;
import com.rks.catalog.service.ICategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

//import static com.rks.catalog.constants.Constants.CATEGORY_NOT_FOUND_ERR_CODE;

@RestController
@RequestMapping("/catalog-service")
public class CategoryController {
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private CategoryRepository categoryRepository;
    private ICategoryService categoryService;
    private MostViewedCategories mostViewedCategories;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository, ICategoryService categoryService,
                              MostViewedCategories mostViewedCategories) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
        this.mostViewedCategories = mostViewedCategories;
    }

    @GetMapping("/v1/categories")
    public List<CategoryResponse> getCategories(@RequestParam MultiValueMap<String, String> params) {
        log.debug("Fetching all categories");
        return categoryService.getAllCategories();
    }

    @PostMapping("/v1/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public Category add(@RequestBody Category c) {
        return categoryRepository.save(c);
    }

    @GetMapping("/v1/categories/{categoryId}")
    public CategoryResponse getCategoryById(@PathVariable("categoryId") String categoryId) {
        return categoryService.getById(categoryId);
    }

    @GetMapping("/v1/categories/search/nameRegex/{searchString}")
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
