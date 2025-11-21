package com.nuvei.nuvei_sdk.network;

import com.nuvei.nuvei_sdk.models.cres.CresConfirmRequest;
import com.nuvei.nuvei_sdk.models.cres.CresConsultingResponse;
import com.nuvei.nuvei_sdk.models.cres.CresDataResponse;
import com.nuvei.nuvei_sdk.models.cres.CresLoginRequest;
import com.nuvei.nuvei_sdk.models.cres.CresLoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CresService {
    @POST("/api/auth/login")
    Call<CresLoginResponse> loginCres(
            @Body CresLoginRequest cresLoginRequest
    );

    @POST("/api/cres/createreference")
    Call<CresConsultingResponse> createReferenceCres();

    @GET("/api/cres/get/{id}")
    Call<CresDataResponse> getDataCres(
            @Path("id") String id
    );

    @POST("/api/cres/confirm")
    Call<CresDataResponse> confirmCres(
            @Body CresConfirmRequest request
    );
}
