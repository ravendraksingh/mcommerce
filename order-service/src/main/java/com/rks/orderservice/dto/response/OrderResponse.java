package com.rks.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rks.orderservice.domain.Item;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.rks.orderservice.constants.Constant.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse extends BaseResponse {

    @NotNull
    @JsonProperty(ORDER_ID)
    private Long orderId;

    @JsonProperty(ORDER_STATUS)
    private String orderStatus;

    @Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", message = "Amount must be a positive number with maximal 2 decimal places")
    @JsonProperty(value = ORDER_AMOUNT, required = true)
    private BigDecimal orderAmount;

    @JsonProperty(ITEMS_IN_ORDER)
    private List<OrderItem> itemList = new ArrayList<>();
}
