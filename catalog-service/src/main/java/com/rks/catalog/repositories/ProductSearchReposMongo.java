package com.rks.catalog.repositories;

import com.rks.catalog.models.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductSearchReposMongo extends MongoRepository<Product, String > {

    List<Product> findByType(String type);

}
