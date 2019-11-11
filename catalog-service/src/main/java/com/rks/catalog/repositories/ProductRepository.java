package com.rks.catalog.repositories;

import com.rks.catalog.models.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
