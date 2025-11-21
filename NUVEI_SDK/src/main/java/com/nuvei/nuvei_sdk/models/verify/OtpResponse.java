package com.nuvei.nuvei_sdk.models.verify;

import com.google.gson.annotations.SerializedName;
import com.nuvei.nuvei_sdk.models.addCard.CardResponse;
import com.nuvei.nuvei_sdk.models.addCard.The3dsResponse;

public class OtpResponse {
    @SerializedName("transaction")
    private TransactionOtp transactionOtpresponse;
    @SerializedName("card")
    private CardResponse cardResponse;
    @SerializedName("3ds")
    private The3dsResponse the3dsResponse;


    public TransactionOtp getTransactionOtpresponse() {
        return transactionOtpresponse;
    }

    public void setTransactionOtpresponse(TransactionOtp transactionOtpresponse) {
        this.transactionOtpresponse = transactionOtpresponse;
    }

    public CardResponse getCardResponse() {
        return cardResponse;
    }

    public void setCardResponse(CardResponse cardResponse) {
        this.cardResponse = cardResponse;
    }

    public The3dsResponse getThe3dsResponse() {
        return the3dsResponse;
    }

    public void setThe3dsResponse(The3dsResponse the3dsResponse) {
        this.the3dsResponse = the3dsResponse;
    }
}
