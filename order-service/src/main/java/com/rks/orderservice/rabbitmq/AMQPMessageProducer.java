package com.rks.orderservice.rabbitmq;

import com.rks.orderservice.configuration.RabbitMQProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AMQPMessageProducer {

    private static final Logger log = LoggerFactory.getLogger(AMQPMessageProducer.class);

    private RabbitTemplate rabbitTemplate;
    private RabbitMQProperties rabbitMQProperties;

    public AMQPMessageProducer(RabbitTemplate rabbitTemplate, RabbitMQProperties rabbitMQProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitMQProperties = rabbitMQProperties;
    }

    public void sendMessage(OrderMessage message) {
        log.info("Sending message to kafka thru {}", this.getClass().getName());
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getRoutingKey(),
                message);
    }
}
