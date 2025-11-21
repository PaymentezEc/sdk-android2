package com.nuvei.nuvei_sdk.network;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CresClient {

    public  Retrofit getClient(String token){

        String baseUrl = "https://nuvei-cres-dev-bkh4atahdegxa8dk.eastus-01.azurewebsites.net";
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
