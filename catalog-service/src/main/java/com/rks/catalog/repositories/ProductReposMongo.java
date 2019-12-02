package com.rks.catalog.repositories;

import com.rks.catalog.models.product.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductReposMongo extends MongoRepository<Product, String > {

    //@Query("{type : ?0}")
    List<Product> findByType(String type);


}
