package com.rks.paymentservice.constants;

public class Constant {

    public static final String AUTHORIZATION             = "authorization";
    /**
     * Response related constants
     */
    public static final String RESPONSE_CODE            = "response_code";
    /**
     * Order related constants
     */
    public static final String ORDER_DATE               = "order_date";
    public static final String ORDER_ID                 = "order_id";
    public static final String ORDER_STATUS             = "order_status";
    public static final String ITEMS_IN_ORDER           = "items";
    public static final String ORDER_AMOUNT             = "order_amount";

    /**
     * Item related constants
    */
    public static final String ITEM_ID                  = "item_id";
    public static final String ITEM_NAME                = "name";
    public static final String ITEM_QUANTITY            = "quantity";
    public static final String ITEM_PRICE               = "price";

    public static final String ORDER_SERVICE            = "order-service";
    public static final String PAYMENT_SERVICE          = "payment-service";
    public static final String JWT_ORDER_ID          = "orderId";

    public static final String ORDER_GET                = "order.get";

    // Exception Message
    public static final String INTERNAL_SERVER_ERROR    = "internal error";
    public static final String FAILED                   = "failed";
    public static final String IS_OLD_API               = "isOldApi";

    public static final String JWT_SECRET_KEY_ORDER_SERVICE = "Ak37osje2360KHy#0ncWR0J$xw29";
    public static final String JWT_SECRET_KEY_PAYMENT_SERVICE = "s7kys&923onwg81AE9$$qdvs45";

    public static final String JWT_CLIENT_ID = "Client-id";

    public static final String JWT_TOKEN_GENERATION_ERROR_MSG = "Error in jwt token generation";
    public static final int JWT_EXPIRATION_TIME         = 3600000;
}
