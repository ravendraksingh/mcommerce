package com.rks.catalog.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class CatalogExecutorConfig {

    @Value("${healthcheck.core.pool.size}")
    private int corePoolSize;

    @Value("${healthcheck.max.pool.size}")
    private int maxPoolSize;

    @Value("${healthcheck.queue.size}")
    private int queueCapacity;

    @Value("${healthcheck.timeout}")
    private int threadTimeout;

    @Bean
    @Qualifier("healthcheckExecutor")
    public ThreadPoolTaskExecutor healthcheckExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        threadPoolTaskExecutor.setKeepAliveSeconds(threadTimeout);

        return threadPoolTaskExecutor;
    }
}
