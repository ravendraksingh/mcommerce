package com.rks.catalog.service;

import com.rks.catalog.models.product.Product;

import java.util.List;
import java.util.Map;

public interface IProductService {
    void add(Product product);
    void delete(String id);
    Product get(String id);
    List<Product> searchProducts(Map<String, String[]> searchCriteriaMap) throws Exception;
    List<Product> getProductsByCategoryName(String categoryName);
}
