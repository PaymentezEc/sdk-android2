package com.nuvei.nuvei_sdk.models.debit;

public class DebitResponse {
    private DebitTransactionResponse transaction;
    private DebitCardResponse card;


    public DebitTransactionResponse getTransaction() {
        return transaction;
    }

    public DebitCardResponse getCard() {
        return card;
    }
}

