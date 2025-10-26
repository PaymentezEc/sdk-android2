package com.nuvei.nuvei_sdk.helper;

import com.nuvei.nuvei_sdk.models.error.ErrorResponseModel;

public interface NuveiCallBack<T>{
    void onSucces(T data);
    void onError(ErrorResponseModel error);
}
