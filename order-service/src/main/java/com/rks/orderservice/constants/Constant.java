package com.rks.orderservice.constants;

public class Constant {

    /**
     * Order related constants
     */
    public static final String ORDER_DATE                 = "order_date";
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

    // Exception Message
    public static final String IS_OLD_API               = "isOldApi";

    public static final String INVALID_ORDER_ID_MSG     = "Invalid Order Id";
    public static final String UPDATE_ORDER_FAILED_ERROR_MSG = "Order update operation failed";
    public static final String UPDATE_ORDER_SUCCESS_MSG = "Order updated successfully";

    public static final String INTERNAL_SERVER_ERROR_MSG    = "Your request was declined due to an internal error. Please try again after sometime.";
    public static final String DB_NOT_AVAILABLE_ERROR_MSG = "Database not available";

    public static final String JWT_SECRET_KEY_ORDER_SERVICE = "Ak37osje2360KHy#0ncWR0J$xw29";
    public static final String JWT_SECRET_KEY_PAYMENT_SERVICE = "s7kys&923onwg81AE9$$qdvs45";
}
