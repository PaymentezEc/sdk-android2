package com.nuvei.nuvei_sdk.models.verify;

import com.google.gson.annotations.SerializedName;

public class TransactionOtp {
    @SerializedName("status")
    private String status;

    @SerializedName("payment_date")
    private String paymentDate;

    @SerializedName("amount")
    private double amount;

    @SerializedName("authorization_code")
    private String authorizationCode;

    @SerializedName("installments")
    private int installments;

    @SerializedName("dev_reference")
    private String devReference;

    @SerializedName("message")
    private String message;

    @SerializedName("carrier_code")
    private String carrierCode;

    @SerializedName("id")
    private String id;

    @SerializedName("status_detail")
    private int statusDetail;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPayment_date() { return paymentDate; }
    public void setPayment_date(String payment_date) { this.paymentDate = payment_date; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getAuthorization_code() { return authorizationCode; }
    public void setAuthorization_code(String authorization_code) { this.authorizationCode = authorization_code; }

    public int getInstallments() { return installments; }
    public void setInstallments(int installments) { this.installments = installments; }

    public String getDev_reference() { return devReference; }
    public void setDev_reference(String dev_reference) { this.devReference = dev_reference; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCarrier_code() { return carrierCode; }
    public void setCarrier_code(String carrier_code) { this.carrierCode = carrier_code; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public int getStatus_detail() { return statusDetail; }
    public void setStatus_detail(int status_detail) { this.statusDetail = status_detail; }

}
