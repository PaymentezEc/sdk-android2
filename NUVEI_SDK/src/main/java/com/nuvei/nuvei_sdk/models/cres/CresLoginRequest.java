package com.nuvei.nuvei_sdk.models.cres;

public class CresLoginRequest {
    private  String clientId;
    private String clientSecret;

    public CresLoginRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
}
