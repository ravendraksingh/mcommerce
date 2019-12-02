package com.rks.catalog;

import com.rks.catalog.repositories.ProductReposMongo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableDiscoveryClient
@EnableMongoRepositories(basePackageClasses = ProductReposMongo.class)
@SpringBootApplication
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

}
