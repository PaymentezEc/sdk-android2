package com.nuvei.nuvei_sdk.network;

import com.nuvei.nuvei_sdk.NuveiSDK;
import com.nuvei.nuvei_sdk.helper.GlobalHelper;
import com.nuvei.nuvei_sdk.models.Environment;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NuveiClient {
    private static final String BASE_URL_PROD = "https://ccapi.paymentez.com";
    private static final String BASE_URL_TEST = "https://ccapi-stg.paymentez.com";
    private final String baseUrl;
    private final OkHttpClient client;


    public NuveiClient(String appCode, String appKey) {
        Environment env = NuveiSDK.getInstance().getEnvironment();
        this.baseUrl = env.isTestMode() ? BASE_URL_TEST : BASE_URL_PROD;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        this.client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new AuthInterceptor(appCode, appKey))
                .build();


    }

    public NuveiService getService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(NuveiService.class);
    }
}