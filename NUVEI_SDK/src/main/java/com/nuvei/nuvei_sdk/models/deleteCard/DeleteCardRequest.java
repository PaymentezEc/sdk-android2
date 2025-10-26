package com.nuvei.nuvei_sdk.models.deleteCard;

import com.google.gson.annotations.SerializedName;
import com.nuvei.nuvei_sdk.models.CardToken;

public class DeleteCardRequest {
    @SerializedName("user")
    private UserIdDelete userDelete;
    @SerializedName("card")
    private CardToken cardDelete;

    public DeleteCardRequest(CardToken cardDelete, UserIdDelete userDelete){
        this.cardDelete = cardDelete;
        this.userDelete = userDelete;
    }
}
