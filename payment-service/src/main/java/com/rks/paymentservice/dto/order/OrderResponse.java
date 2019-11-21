package com.rks.paymentservice.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponse implements Serializable {
    private Long order_id;
    private String order_status;
    private BigDecimal order_amount;
    private List<OrderItem> items = new ArrayList<>();
}
