package com.rks.catalog;

import com.rks.catalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableDiscoveryClient
@EnableMongoRepositories(basePackageClasses = ProductRepository.class)
@SpringBootApplication
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }


}
