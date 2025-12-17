package com.nuvei.nuvei_sdk.models.addCard;

import com.google.gson.annotations.SerializedName;

public class BrowserResponse {
    @SerializedName("challenge_request")
    private String challengeRequest;
    @SerializedName("hidden_frame")
    private String hiddenIframe;

    public String getChallengeRequest() { return challengeRequest; }
    public void setChallengeRequest(String value) { this.challengeRequest = value; }

    public String getHiddenIframe() { return hiddenIframe; }
    public void setHiddenIframe(String value) { this.hiddenIframe = value; }
}
