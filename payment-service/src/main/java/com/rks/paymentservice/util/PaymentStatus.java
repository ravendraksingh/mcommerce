package com.rks.paymentservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {

    SUCCESS("success"),
    FAILURE("failure"),
    PENDING("pending"),
    INVALID("invalid"),
    PAYMENT_COMPLETED("paid"),
    PAYMENT_FAILED("failed");

    private String status;

    public static PaymentStatus getMappedStatusEnum(String status) {
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            if (paymentStatus.getStatus().equalsIgnoreCase(status)) {
                return paymentStatus;
            }
        }
        return FAILURE;
    }
}
