package com.nuvei.nuvei_sdk.models;


import androidx.annotation.NonNull;

public class Environment {

    private final String appCode;
    private final String appKey;
    private final String serverCode;
    private final String serverKey;
    private final String clientId;
    private final String clientCode;
    private final boolean testMode;

    // Constructor
    public Environment(@NonNull String appCode,
                       @NonNull String appKey,
                       @NonNull String serverCode,
                       @NonNull String serverKey,
                       @NonNull String clientId,
                       @NonNull String clientCode,
                       boolean testMode) {
        this.appCode = appCode;
        this.appKey = appKey;
        this.serverCode = serverCode;
        this.serverKey = serverKey;
        this.clientId = clientId;
        this.clientCode = clientCode;
        this.testMode = testMode;
    }

    // Getters
    public String getAppCode() {
        return appCode;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getServerCode() {
        return serverCode;
    }

    public String getServerKey() {
        return serverKey;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientCode() {
        return clientCode;
    }

    public boolean isTestMode() {
        return testMode;
    }

    // Optional: método para imprimir todos los datos (debug)
    @NonNull
    @Override
    public String toString() {
        return "Environment{" +
                "appCode='" + appCode + '\'' +
                ", appKey='" + appKey + '\'' +
                ", serverCode='" + serverCode + '\'' +
                ", serverKey='" + serverKey + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientCode='" + clientCode + '\'' +
                ", testMode=" + testMode +
                '}';
    }
}
