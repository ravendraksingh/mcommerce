package com.rks.paymentservice.configuration;

import com.rks.paymentservice.rabbitmq.OrderCreatedListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

@Configuration
public class OrderCreatedAMQPConfig {

    @Autowired
    private OrderCreatedRabbitMQProperties properties;

    @Bean(name = "orderCreatedQueue")
    @Qualifier("orderCreatedQueue")
    Queue queue() {
        return new Queue(properties.getOrderCreatedQueueName(), false);
    }

    @Bean(name = "orderCreatedExchange")
    @Qualifier("orderCreatedExchange")
    TopicExchange exchange() {
        return new TopicExchange(properties.getOrderCreatedExchangeName());
    }

    @Bean(name = "orderCreatedBinding")
    @Qualifier("orderCreatedBinding")
    Binding binding(@Qualifier("orderCreatedQueue") Queue queue,
                    @Qualifier("orderCreatedExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(properties.getOrderCreatedRoutingKey());
    }

    @Bean(name = "orderCreatedContainer")
    @Qualifier("orderCreatedContainer")
    SimpleMessageListenerContainer container
            (ConnectionFactory connectionFactory1,
             @Qualifier("orderCreatedListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer orderCreatedContainer = new SimpleMessageListenerContainer();
        orderCreatedContainer.setConnectionFactory(connectionFactory1);
        orderCreatedContainer.setQueueNames(properties.getOrderCreatedQueueName());
        orderCreatedContainer.setMessageListener(listenerAdapter);
        return orderCreatedContainer;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean(name = "orderCreatedAMQPTemplate")
    @Qualifier("orderCreatedAMQPTemplate")
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory1) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory1);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean(name = "messageConverter")
    @Qualifier("messageConverter")
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean(name = "orderCreatedListenerAdapter")
    @Qualifier("orderCreatedListenerAdapter")
    MessageListenerAdapter listenerAdapter(OrderCreatedListener listener) {
        return new MessageListenerAdapter(listener, "listenToOrderCreated");
    }
}
