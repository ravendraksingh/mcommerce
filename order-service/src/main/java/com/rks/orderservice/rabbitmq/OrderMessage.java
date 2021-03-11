package com.rks.orderservice.rabbitmq;

import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderMessage implements Serializable {
    private Long orderId;
    //private Date orderDate;
    private String orderStatus;

    public OrderMessage(Long orderId) {
        this.orderId = orderId;
    }
}
