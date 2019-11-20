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
public class RabbitMQProperties {

    @Value("${order-service.exchange.name}")
    private String exchangeName;

    @Value("${order-service.queue.name}")
    private String queueName;

    @Value("${order-service.routing.key}")
    private String routingKey;

}
