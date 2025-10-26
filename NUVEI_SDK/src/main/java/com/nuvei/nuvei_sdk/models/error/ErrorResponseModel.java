package com.nuvei.nuvei_sdk.models.error;

import com.google.gson.annotations.SerializedName;

public class ErrorResponseModel {
    private ErrorData error;

    public ErrorResponseModel(ErrorData error) {
        this.error = error;
    }

    public ErrorData getError() {
        return error;
    }

    public void setError(ErrorData error) {
        this.error = error;
    }
}
