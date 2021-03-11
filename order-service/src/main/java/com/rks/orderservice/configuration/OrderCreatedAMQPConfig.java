package com.rks.orderservice.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(
        value = "rabbitmq.enabled",
        havingValue = "true",
        matchIfMissing = false
)
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

   /* @Bean(name = "orderCreatedContainer")
    @Qualifier("orderCreatedContainer")
    SimpleMessageListenerContainer container
            (ConnectionFactory connectionFactory1,
             @Qualifier("orderCreatedListenerAdapter") MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer orderCreatedContainer = new SimpleMessageListenerContainer();
        orderCreatedContainer.setConnectionFactory(connectionFactory1);
        orderCreatedContainer.setQueueNames(properties.getOrderCreatedQueueName());
        orderCreatedContainer.setMessageListener(listenerAdapter);
        return orderCreatedContainer;
    }*/

    /*@Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }*/

    @Bean(name = "orderCreatedAMQPTemplate")
    @Qualifier("orderCreatedAMQPTemplate")
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory1) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory1);
        rabbitTemplate.setMessageConverter(messageConverter2());
        return rabbitTemplate;
    }

    @Bean(name = "messageConverter2")
    @Qualifier("messageConverter2")
    public Jackson2JsonMessageConverter messageConverter2() {
        return new Jackson2JsonMessageConverter();
    }

   /* @Bean(name = "orderCreatedListenerAdapter")
    @Qualifier("orderCreatedListenerAdapter")
    MessageListenerAdapter listenerAdapter(OrderCreatedListener listener) {
        return new MessageListenerAdapter(listener, "listenToOrderCreated");
    }*/
}
