package com.nuvei.nuvei_sdk.network;

import com.nuvei.nuvei_sdk.internal.NuveiSDK;
import com.nuvei.nuvei_sdk.models.Environment;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CresClient {
    private static final String BASE_URL_PROD = "https://cres.nuvei.com.ec";
    private static final String BASE_URL_TEST = "https://cres.nuvei.com.ec";


    public  Retrofit getClient(String token){
        Environment env = NuveiSDK.getInstance().getEnvironment();
        String baseUrl = env.isTestMode() ? BASE_URL_TEST : BASE_URL_PROD;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.addInterceptor(logging);

        client.addInterceptor(chain -> {
            Request request =  chain.request().newBuilder()
                    .addHeader("Content-type", "application/json")
                    .addHeader("Authorization", "Bearer " +token )
                    .build();
            return chain.proceed(request);
        });
        OkHttpClient newClient = client.build();

        return  new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(newClient)
                .build();

    }

}
