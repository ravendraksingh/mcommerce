package com.rks.paymentservice.rabbitmq;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrderMessage {
    private Long orderId;
    //private Date orderDate;
    private String orderStatus;
}
