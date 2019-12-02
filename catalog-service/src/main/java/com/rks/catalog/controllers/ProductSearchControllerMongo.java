package com.rks.catalog.controllers;

import com.rks.catalog.models.product.Product;
import com.rks.catalog.service.IProductSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/catalog/ext")
public class ProductSearchControllerMongo {
    private static final Logger log = LoggerFactory.getLogger(ProductSearchControllerMongo.class);

    @Autowired
    private IProductSearch iProductSearch;

    @GetMapping("/v1/products/search")
    public List<Product> searchProducts(HttpServletRequest request) {
        return iProductSearch.searchProducts(request.getParameterMap());
    }
}
