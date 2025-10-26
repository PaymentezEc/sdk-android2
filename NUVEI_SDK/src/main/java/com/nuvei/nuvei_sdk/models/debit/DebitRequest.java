package com.nuvei.nuvei_sdk.models.debit;

import com.google.gson.annotations.SerializedName;
import com.nuvei.nuvei_sdk.models.CardToken;

public class DebitRequest {
    @SerializedName("user")
    private UserDebit user;
    @SerializedName("order")
    private DebitOrder order;
    @SerializedName("card")
    private CardToken card;

    public DebitRequest(UserDebit user, DebitOrder order, CardToken card) {
        this.user = user;
        this.order = order;
        this.card = card;
    }
}
