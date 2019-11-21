package com.rks.paymentservice.constants;

public class Constant {
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

    public static final String ORDER_GET                = "order.get";

    // Exception Message
    public static final String INTERNAL_SERVER_ERROR    = "Your request was declined due to an internal error. Please try again after sometime.";
    public static final String FAILED                   = "failed";
    public static final String IS_OLD_API               = "isOldApi";

}
