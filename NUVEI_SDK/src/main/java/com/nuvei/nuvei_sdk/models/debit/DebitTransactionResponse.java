package com.nuvei.nuvei_sdk.models.debit;

import com.google.gson.annotations.SerializedName;

public class DebitTransactionResponse {
    private String status;
    @SerializedName("current_status")
    private String currentStatus;
    @SerializedName("payment_date")
    private String paymentDate;
    private Double amount;
    @SerializedName("authorization_code")
    private String authorizationCode;
    private int installments;
    @SerializedName("dev_reference")
    private String devReference;
    private String message;
    @SerializedName("carrier_code")
    private String carrierCode;
    private String id;
    @SerializedName("status_detail")
    private int statusDetail;
    @SerializedName("installments_type")
    private String installmentsType;
    @SerializedName("payment_method_type")
    private String paymentMethodType;
    @SerializedName("product_description")
    private String productDescription;

    public String getStatus() {
        return status;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public int getInstallments() {
        return installments;
    }

    public String getDevReference() {
        return devReference;
    }

    public String getMessage() {
        return message;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public String getId() {
        return id;
    }

    public int getStatusDetail() {
        return statusDetail;
    }

    public String getInstallmentsType() {
        return installmentsType;
    }

    public String getPaymentMethodType() {
        return paymentMethodType;
    }

    public String getProductDescription() {
        return productDescription;
    }
}
