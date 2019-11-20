package com.rks.orderservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@PropertySource("classpath:application.properties")
public class OrderCreatedRabbitMQProperties {

    @Value("${order-created.exchange.name}")
    private String orderCreatedExchangeName;

    @Value("${order-created.queue.name}")
    private String orderCreatedQueueName;

    @Value("${order-created.routing.key}")
    private String orderCreatedRoutingKey;


}
