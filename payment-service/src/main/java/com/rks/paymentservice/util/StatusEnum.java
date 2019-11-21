package com.rks.paymentservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEnum {

    SUCCESS("success"),
    FAILURE("failure"),
    PENDING("pending"),
    INVALID("invalid"),
    ORDER_CREATED("created"),
    ORDER_PAID("paid"),
    ORDER_DELIVERED("delivered"),
    ORDER_RETURNED("returned"),
    ORDER_REFUNDED("refunded");

    private String status;

    public static StatusEnum getMappedStatusEnum(String status) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.getStatus().equalsIgnoreCase(status)) {
                return statusEnum;
            }
        }
        return FAILURE;
    }
}
