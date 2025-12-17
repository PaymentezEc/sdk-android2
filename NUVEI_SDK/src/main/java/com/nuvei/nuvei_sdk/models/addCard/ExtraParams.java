package com.nuvei.nuvei_sdk.models.addCard;

import com.google.gson.annotations.SerializedName;

public class ExtraParams {
    @SerializedName("threeDS2_data")
    private ThreeDS2Data threeDS2Data;
    @SerializedName("browser_info")
    private BrowserInfo browserInfo;

    public ExtraParams(ThreeDS2Data threeDS2Data, BrowserInfo browserInfo) {
        this.threeDS2Data = threeDS2Data;
        this.browserInfo = browserInfo;
    }
}
