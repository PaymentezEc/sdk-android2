package com.nuvei.nuvei_sdk.models.addCard;

import com.google.gson.annotations.SerializedName;

public class The3dsResponse {
    private AuthenticationResponse authentication;
    @SerializedName("browser_response")
    private BrowserResponse browserResponse;
    @SerializedName("sdk_response")
    private SdkResponse sdkResponse;

    public AuthenticationResponse getAuthentication() { return authentication; }
    public void setAuthentication(AuthenticationResponse value) { this.authentication = value; }

    public BrowserResponse getBrowserResponse() { return browserResponse; }
    public void setBrowserResponse(BrowserResponse value) { this.browserResponse = value; }

    public SdkResponse getSDKResponse() { return sdkResponse; }
    public void setSDKResponse(SdkResponse value) { this.sdkResponse = value; }
}
