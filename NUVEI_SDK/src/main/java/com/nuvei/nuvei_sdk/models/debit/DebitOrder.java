package com.nuvei.nuvei_sdk.models.debit;

import com.google.gson.annotations.SerializedName;

public class DebitOrder {
    private Double amount;
    private String description;
    @SerializedName("dev_reference")
    private String devReference;
    private Double vat;
    @SerializedName("taxable_amount")
    private Integer taxableAmount;
    @SerializedName("tax_percentage")
    private Integer taxPercentage;


    public DebitOrder(Double amount, String description, String devReference, Double vat, Integer taxableAmount, Integer taxPercentage) {
        this.amount = amount;
        this.description = description;
        this.devReference = devReference;
        this.vat = vat;
        this.taxableAmount = taxableAmount;
        this.taxPercentage = taxPercentage;
    }
}
