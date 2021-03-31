package com.rks.orderservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.rks.orderservice.constants.OrderServiceConstants.ORDER_STATUS;
import static com.rks.orderservice.constants.OrderServiceConstants.PAYMENT_STATUS;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderRequest {

    @JsonProperty(ORDER_STATUS)
    private String orderStatus;
    @JsonProperty(PAYMENT_STATUS)
    private String paymentStatus;
}
