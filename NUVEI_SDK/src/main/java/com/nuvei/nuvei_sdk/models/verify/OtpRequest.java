package com.nuvei.nuvei_sdk.models.verify;

import com.google.gson.annotations.SerializedName;
import com.nuvei.nuvei_sdk.models.deleteCard.UserIdDelete;


public class OtpRequest {
    @SerializedName("user")
    private UserIdDelete user;
    @SerializedName("transaction")
    private TransactionRequest transaction;
    private String type;
    private String value;
    @SerializedName("more_info")
    private boolean moreInfo;

    public OtpRequest(UserIdDelete user, TransactionRequest transaction, String type, String value, boolean moreInfo) {
        this.user = user;
        this.transaction = transaction;
        this.type = type;
        this.value = value;
        this.moreInfo = moreInfo;
    }
}
