package com.rks.catalog.service;

import com.rks.catalog.models.product.Product;

import java.util.List;
import java.util.Map;

public interface IProductSearch {
    List<Product> searchProducts(Map<String, String[]> searchCriteriaMap);
}
