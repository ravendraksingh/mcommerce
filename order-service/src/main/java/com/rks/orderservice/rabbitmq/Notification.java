package com.rks.orderservice.rabbitmq;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Notification implements Serializable {

    private String notificationType;
    private String msg;

    public Notification() {
    }

}
