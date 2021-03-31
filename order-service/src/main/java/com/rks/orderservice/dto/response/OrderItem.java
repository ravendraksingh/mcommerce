package com.rks.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

import static com.rks.orderservice.constants.OrderServiceConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @JsonProperty(ITEM_ID)
    private Long id;

    @JsonProperty(ITEM_NAME)
    private String name;

    @JsonProperty(ITEM_QUANTITY)
    private int quantity;

    @Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", message = "Amount must be a positive number with maximal 2 decimal places")
    @JsonProperty(value = ITEM_PRICE, required = true)
    private BigDecimal price;
}
