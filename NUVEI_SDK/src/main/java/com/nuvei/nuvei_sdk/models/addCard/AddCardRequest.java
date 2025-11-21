package com.nuvei.nuvei_sdk.models.addCard;

import com.nuvei.nuvei_sdk.models.debit.UserDebit;

public class AddCardRequest {
    private UserDebit user;
    private CardModel card;
    private ExtraParams extra_params;

    public AddCardRequest(UserDebit user, CardModel card, ExtraParams extraParams) {
        this.user = user;
        this.card = card;
        this.extra_params = extraParams;
    }
}
