package com.nuvei.nuvei_sdk.models.cres;

import com.google.gson.annotations.SerializedName;

public class CresConsultingResponse {

    @SerializedName("status")
    private boolean status;

    @SerializedName("id")
    private String id = "";

    @SerializedName("message")
    private String message = "";

    public CresConsultingResponse(boolean status, String id, String message) {
        this.status = status;
        this.id = id != null ? id : "";
        this.message = message != null ? message : "";
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id != null ? id : "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message != null ? message : "";
    }
}