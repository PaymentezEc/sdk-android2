package com.nuvei.nuvei_sdk.models.addCard;

import com.google.gson.annotations.SerializedName;

public class CardResponse {
    private String number;
    private String bin;
    private String type;
    @SerializedName("transaction_reference")
    private String transactionReference;
    private String status;
    private String token;
    private String expiryYear;
    private String expiryMonth;
    private String origin;
    private String message;

    public String getNumber() { return number; }
    public void setNumber(String value) { this.number = value; }

    public String getBin() { return bin; }
    public void setBin(String value) { this.bin = value; }

    public String getType() { return type; }
    public void setType(String value) { this.type = value; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String value) { this.transactionReference = value; }

    public String getStatus() { return status; }
    public void setStatus(String value) { this.status = value; }

    public String getToken() { return token; }
    public void setToken(String value) { this.token = value; }

    public String getExpiryYear() { return expiryYear; }
    public void setExpiryYear(String value) { this.expiryYear = value; }

    public String getExpiryMonth() { return expiryMonth; }
    public void setExpiryMonth(String value) { this.expiryMonth = value; }

    public String getOrigin() { return origin; }
    public void setOrigin(String value) { this.origin = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

}
