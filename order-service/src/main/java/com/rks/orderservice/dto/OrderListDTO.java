package com.rks.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rks.orderservice.domain.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDTO {
    private Long orderId;
    private String orderStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date orderDate;

    private List<Item> orderItems;
}
