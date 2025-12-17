package com.nuvei.nuvei_sdk.models.addCard;

import com.google.gson.annotations.SerializedName;

public class CardModel {
    private String number;
    @SerializedName("holder_name")
    private String holderName;
    @SerializedName("expiry_month")
    private int expiryMonth;
    @SerializedName("expiry_year")
    private int expiryYear;
    private String cvc;
    private String type;

    public CardModel(String number, String holderName, int expiryMonth, int expiryYear, String cvc, String type) {
        this.number = number;
        this.holderName = holderName;
        this.expiryMonth = expiryMonth;
        this.expiryYear = expiryYear;
        this.cvc = cvc;
        this.type = type;
    }
}
