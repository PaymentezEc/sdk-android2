package com.nuvei.nuvei_sdk.models.refund;

public class RefundRequest {
    private TransactionRefund transaction;
    private OrderRefund order;
    private Boolean more_info;

    public RefundRequest(TransactionRefund transaction, OrderRefund order, Boolean more_info) {
        this.transaction = transaction;
        this.order = order;
        this.more_info = more_info;
    }
}
