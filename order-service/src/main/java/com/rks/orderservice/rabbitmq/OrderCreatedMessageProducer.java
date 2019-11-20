package com.rks.orderservice.rabbitmq;

import com.rks.orderservice.configuration.OrderCreatedRabbitMQProperties;
import com.rks.orderservice.configuration.RabbitMQProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedMessageProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedMessageProducer.class);

    private RabbitTemplate template;
    private OrderCreatedRabbitMQProperties properties;

    public OrderCreatedMessageProducer(RabbitTemplate template, OrderCreatedRabbitMQProperties properties) {
        this.template = template;
        this.properties = properties;
    }

    public void sendMessage(OrderMessage message) {
        log.info("Sending message to kafka thru {}", this.getClass().getName());
        try {
            template.convertAndSend(properties.getOrderCreatedExchangeName(), properties.getOrderCreatedRoutingKey(),
                    message);
        } catch (Exception e) {
            log.error("Exception occurred. Message {}", e.getMessage());
        }

    }
}
