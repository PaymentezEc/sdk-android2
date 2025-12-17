package com.nuvei.nuvei_sdk.listener;

import com.nuvei.nuvei_sdk.models.error.ErrorResponseModel;

public interface AddCardListener {
    void onSucces(boolean success, String message);
    void onError(ErrorResponseModel errorResponseModel);
    void onLoading(boolean isLoading);
}
