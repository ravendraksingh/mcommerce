package com.rks.catalog.controllers;

import com.rks.catalog.exceptions.NotFoundException;
import com.rks.catalog.models.product.Product;
import com.rks.catalog.repositories.ProductReposMongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalog/ext")
public class ProductControllerMongo {

    private static final Logger log = LoggerFactory.getLogger(ProductControllerMongo.class);

    @Autowired
    private ProductReposMongo productReposMongo;

    @GetMapping("/v1/products/{productId}")
    public Product getProductById(@PathVariable("productId") String id) {
        Optional<Product> optionalProduct = productReposMongo.findById(id);
        if (optionalProduct.isPresent())
            return optionalProduct.get();
        else
            throw new NotFoundException("Product not found for id " + id);
    }

}
