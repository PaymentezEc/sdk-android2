package com.nuvei.nuvei_sdk.models.cres;

import com.google.gson.annotations.SerializedName;

public class CresDataResponse {

    @SerializedName("data")
    private Data data;

    @SerializedName("confirmed")
    private boolean confirmed;

    public CresDataResponse(Data data, boolean confirmed) {
        this.data = data;
        this.confirmed = confirmed;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public static class Data {
        @SerializedName("cres")
        private String cres;

        @SerializedName("transStatus")
        private String transStatus;

        public Data(String cres, String transStatus) {
            this.cres = cres;
            this.transStatus = transStatus;
        }

        public static Data empty() {
            return new Data(null, null);
        }

        public String getCres() {
            return cres;
        }

        public void setCres(String cres) {
            this.cres = cres;
        }

        public String getTransStatus() {
            return transStatus;
        }

        public void setTransStatus(String transStatus) {
            this.transStatus = transStatus;
        }
    }
}
