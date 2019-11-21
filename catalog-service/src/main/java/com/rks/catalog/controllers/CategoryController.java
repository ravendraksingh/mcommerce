package com.rks.catalog.controllers;

import com.rks.catalog.exceptions.NotFoundException;
import com.rks.catalog.models.Category;
import com.rks.catalog.repositories.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/catalog/ext/v1")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(path = "/categories/all")
    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryRepository.findAll().forEach(c -> categoryList.add(c));
        return categoryList;
    }

    @GetMapping(path = "/categories/{categoryId}")
    public Category getCategoryById(@PathVariable("categoryId") Long categoryId) {
        try {
            log.info("Finding category by category id {}.", categoryId);
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            Category category = categoryOptional.get();
            return category;
        } catch (NoSuchElementException e) {
            log.error("Error in finding category for category id {}.", categoryId);
            throw new NotFoundException("Category not found with category id = "+categoryId);
        }
    }

}
