package com.rks.catalog.controllers;

import com.rks.catalog.dto.response.HealthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/catalog/ext/v1")
public class UtilityController {

    private static final Logger log = LoggerFactory.getLogger(UtilityController.class);

    @Autowired
    private Environment environment;

    @RequestMapping(value = "/health", method = RequestMethod.GET, produces = "application/json")
    @Async("healthcheckExecutor")
    public CompletableFuture<HealthResponse> health() {
        Instant startTime = Instant.now();
        //String transactionId = TransactionIdGenerator.generateId();
        //ThreadContext.put(REQUEST_ID, transactionId);
        log.info("Request received for health check.");
        HealthResponse response = new HealthResponse();
        Instant endTime = Instant.now();
        response.setDuration(endTime.toEpochMilli() - startTime.toEpochMilli());
        response.setPort(environment.getProperty("local.server.port"));
        return CompletableFuture.completedFuture(response);
    }
}
