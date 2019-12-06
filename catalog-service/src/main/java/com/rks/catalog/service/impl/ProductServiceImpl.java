package com.rks.catalog.service.impl;

import com.rks.catalog.dao.ProductSearchDao;
import com.rks.catalog.exceptions.NotFoundException;
import com.rks.catalog.models.product.Product;
import com.rks.catalog.repositories.ProductRepository;
import com.rks.catalog.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductSearchDao productSearchDao;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void add(Product product) {
        productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new NotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }

    @Override
    public Product get(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new NotFoundException("Product not found");
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getProductsByCategoryName(String categoryName) {
        return productRepository.findProductsByCategoryName(categoryName);
    }

    @Override
    public List<Product> searchProducts(Map<String, String[]> searchCriteriaMap) {
        return productSearchDao.searchProducts(searchCriteriaMap);
    }

}
