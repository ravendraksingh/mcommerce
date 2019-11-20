package com.rks.orderservice.rabbitmq;

import com.rks.orderservice.configuration.RabbitMQProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AMQPProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    RabbitMQProperties rabbitMQProperties;

    public void sendMessage(Notification msg) {
        System.out.println("Send msg = " + msg.toString());
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getRoutingKey(), msg);
    }
}
