package com.rks.orderservice.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rks.orderservice.domain.Order;
import com.rks.orderservice.repository.OrderRepository;
import com.rks.orderservice.util.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(OrderCreatedListener.class);

    @Autowired
    private OrderRepository orderRepository;

    //@RabbitListener(queues="${rabbitmq.queueName}")
    public void listenToOrderCreated(byte[] message) {

        String msg = new String(message);
        //log.info("New message {}", msg);
        Gson gson = new GsonBuilder().create();
        OrderMessage orderMessage = gson.fromJson(msg, OrderMessage.class);
        log.info("Received a new message thru {}. Message: {}", this.getClass().getName(), orderMessage);

        log.info("Processing payment for the new order received");
        try {
            Thread.sleep(10000);
            processPaymentForNewOrder(orderMessage);
        } catch (Exception e) {
            log.error("Exception occurred. Message {}", e.getMessage());
        }

    }

    private void processPaymentForNewOrder(OrderMessage message) {
        Order newOrder = orderRepository.findById(message.getOrderId()).get();
        if (newOrder == null) {
            throw new RuntimeException("Order id is null in the new order");
        }
        newOrder.setOrderStatus(StatusEnum.ORDER_PAID.getStatus());
        orderRepository.save(newOrder);
    }
}
