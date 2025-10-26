package com.nuvei.nuvei_sdk.models.refund;

import com.nuvei.nuvei_sdk.models.listCard.CardItemList;

public class RefundResponse {
    private String message; // For simple success message
    private String status;
    private RefundTransaction transaction;
    private String detail;
    private CardItemList card;

    // Getters
    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public RefundTransaction getTransaction() {
        return transaction;
    }

    public String getDetail() {
        return detail;
    }

    public CardItemList getCard() {
        return card;
    }
}
