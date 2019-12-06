package com.rks.catalog.repositories;

import com.rks.catalog.models.category.Category;
import com.rks.catalog.models.product.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String > {

    Category findByName(String name);

    @Query("{ 'name':{$regex:?0, $options:'i'} }")
    List<Category> findCategoriesByNameRegex(String searchString);
}
