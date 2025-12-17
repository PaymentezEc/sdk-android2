package com.nuvei.nuvei_sdk.models.addCard;

public class SdkResponse {
    private String acsTransID;
    private String acsSignedContent;
    private String acsReferenceNumber;

    public String getAcsTransID() { return acsTransID; }
    public void setAcsTransID(String value) { this.acsTransID = value; }

    public String getAcsSignedContent() { return acsSignedContent; }
    public void setAcsSignedContent(String value) { this.acsSignedContent = value; }

    public String getAcsReferenceNumber() { return acsReferenceNumber; }
    public void setAcsReferenceNumber(String value) { this.acsReferenceNumber = value; }
}
