package com.rks.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rks.orderservice.dto.response.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rks.orderservice.constants.OrderServiceConstants.ITEMS_IN_ORDER;
import static com.rks.orderservice.constants.OrderServiceConstants.ORDER_DATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @JsonProperty(ORDER_DATE)
    private Date orderDate;

    @JsonProperty(ITEMS_IN_ORDER)
    private List<OrderItem> items = new ArrayList<>();

    @JsonIgnore
    private String orderStatus;
}
