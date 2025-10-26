package com.nuvei.nuvei_sdk.network;

import com.nuvei.nuvei_sdk.helper.GlobalHelper;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class AuthInterceptor implements Interceptor {
    private final String appCode;
    private final String appKey;

    public AuthInterceptor(String appCode, String appKey) {
        this.appCode = appCode;
        this.appKey = appKey;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        // Obtener token dinámicamente para cada petición
        String token = GlobalHelper.getAuthToken(appKey, appCode);

        Request request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Auth-Token", token)
                .build();

        return chain.proceed(request);
    }
}
