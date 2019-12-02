package com.rks.catalog.service.impl;

import com.rks.catalog.dao.ProductSearchDao;
import com.rks.catalog.models.product.Product;
import com.rks.catalog.service.IProductSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductSearchImpl implements IProductSearch {
    private static final Logger log = LoggerFactory.getLogger(ProductSearchImpl.class);

    @Autowired
    private ProductSearchDao dao;

    @Override
    public List<Product> searchProducts(Map<String, String[]> searchCriteriaMap) {
        return dao.searchProducts(searchCriteriaMap);
    }
}
