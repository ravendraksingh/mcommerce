package com.rks.orderservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.rks.orderservice.constants.OrderServiceConstants.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse extends BaseResponse {

    @NotNull
    @JsonProperty(ORDER_ID)
    private Long orderId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty(ORDER_DATE)
    private Date orderDate;

    @JsonProperty(ORDER_STATUS)
    private String orderStatus;

    @JsonProperty(PAYMENT_STATUS)
    private String paymentStatus;

    @Pattern(regexp = "^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$", message = "Amount must be a positive number with maximal 2 decimal places")
    @JsonProperty(value = ORDER_AMOUNT, required = true)
    private BigDecimal orderAmount;

    @JsonProperty(ITEMS_IN_ORDER)
    private List<OrderItem> items = new ArrayList<>();

    public void addItem(Long id , String name, int quantity, BigDecimal price) {
        OrderItem newItem = new OrderItem();
        newItem.setId(id);
        newItem.setName(name);
        newItem.setQuantity(quantity);
        newItem.setPrice(price);

        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(newItem);
    }

    public void updateOrderAmount(BigDecimal amount) {
        if (orderAmount == null) {
            orderAmount = BigDecimal.ZERO;
        }
        orderAmount = orderAmount.add(amount);
    }
}
