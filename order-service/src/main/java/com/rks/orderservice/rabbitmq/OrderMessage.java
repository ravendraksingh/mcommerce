package com.rks.orderservice.rabbitmq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrderMessage {
    private Long orderId;
    //private Date orderDate;
    private String orderStatus;
}
