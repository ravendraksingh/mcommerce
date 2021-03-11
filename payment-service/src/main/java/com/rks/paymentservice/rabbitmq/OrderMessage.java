package com.rks.paymentservice.rabbitmq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrderMessage implements Serializable {
    private Long orderId;
    //private Date orderDate;
    private String orderStatus;
}
