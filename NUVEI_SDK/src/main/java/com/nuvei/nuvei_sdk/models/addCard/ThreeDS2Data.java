package com.nuvei.nuvei_sdk.models.addCard;

import com.google.gson.annotations.SerializedName;

public class ThreeDS2Data {
    @SerializedName("term_url")
    private String termUrl;
    @SerializedName("device_type")
    private String deviceType;

    public ThreeDS2Data(String termUrl, String deviceType) {
        this.termUrl = termUrl;
        this.deviceType = deviceType;
    }
}
