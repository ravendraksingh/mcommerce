package com.rks.orderservice.rabbitmq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    private static final Logger log = LoggerFactory.getLogger(RabbitListeners.class);

    @RabbitListener(queues="${rabbitmq.queueName}")
    public void listen(byte[] message) {
    //public void listen(OrderMessage message) {
        String msg = new String(message);
        log.info("New message {}", msg);
        //Notification not = new Gson().fromJson(msg, Notification.class);
        //System.out.println("Received a new notification...");
        //System.out.println(not.toString());
        //Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        Gson gson = new GsonBuilder().create();
        OrderMessage orderMessage = gson.fromJson(msg, OrderMessage.class);
        log.info("Received a new message using {}. Message: {}", this.getClass().getName(), orderMessage);
    }
}
