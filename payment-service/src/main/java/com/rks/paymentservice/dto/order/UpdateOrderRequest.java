package com.rks.paymentservice.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateOrderRequest {

    @JsonProperty("order_status")
    private String orderStatus;
    @JsonProperty("payment_status")
    private String paymentStatus;
}