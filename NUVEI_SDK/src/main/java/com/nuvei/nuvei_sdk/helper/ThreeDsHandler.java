package com.nuvei.nuvei_sdk.helper;

import android.os.Handler;
import android.os.Looper;

import com.nuvei.nuvei_sdk.models.cres.CresDataResponse;
import com.nuvei.nuvei_sdk.network.CresService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThreeDsHandler {

    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isChecking = false;
    private final long pollingInterval = 5000; // 5 segundos
    private boolean isPollingActive = false;

    public void startPolling(CresService serviceCres, String token, String referenceId, ThreeDsCallback callback) {
        isPollingActive = true;
        final Runnable[] pollRunnable = new Runnable[1];
        pollRunnable[0] = new Runnable(){
            @Override
            public void run() {
                if (!isPollingActive) return;
                if (isChecking) {
                    handler.postDelayed(this, pollingInterval);
                    return;
                }

                isChecking = true;

                Call<CresDataResponse> call = serviceCres.getDataCres( referenceId);
                call.enqueue(new Callback<CresDataResponse>() {
                    @Override
                    public void onResponse(Call<CresDataResponse> call, Response<CresDataResponse> response) {
                        isChecking = false;

                        if (!isPollingActive) return;


                        if (response.isSuccessful() && response.body() != null && response.body().getData().getCres() != null) {
                            String cres = response.body().getData().getCres();
                            callback.onCresReceived(cres); // retorna el CRES
                        } else {
                            handler.postDelayed(pollRunnable[0], pollingInterval);
                        }
                    }

                    @Override
                    public void onFailure(Call<CresDataResponse> call, Throwable t) {
                        isChecking = false;
                        stopPolling();
                        handler.postDelayed(pollRunnable[0], pollingInterval);
                    }
                });
            }
        };

        handler.post(pollRunnable[0]);
    }

    public void stopPolling() {
        isPollingActive = false;
    }

    public interface ThreeDsCallback {
        void onCresReceived(String cres);
    }
}
