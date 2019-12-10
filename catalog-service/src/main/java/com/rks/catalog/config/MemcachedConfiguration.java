package com.rks.catalog.config;

import com.rks.catalog.caching.CustomKeyGenerator;
import com.rks.catalog.caching.Memcached;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.*;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@EnableCaching
@Configuration
@ConditionalOnProperty(
        value = "memcached.isEnabled",
        havingValue = "true",
        matchIfMissing = false)
public class MemcachedConfiguration implements CachingConfigurer {

    @Value("${memcached.addresses}")
    private String memcachedAddresses;

    @Value("${memcached.expiration.sec}")
    private int expirationSec;

    @Override
    @Bean(name = "catalogCM")
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        try {
            cacheManager.setCaches(internalCaches());
            return cacheManager;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<Memcached> internalCaches() throws IOException {
        final Collection<Memcached> caches = new ArrayList<>();
        caches.add(new Memcached("catalogCache", memcachedAddresses, expirationSec));
        return caches;
    }
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver();
    }
    /*@Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }*/
    @Override
    @Bean(name = "customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }
    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }
}
