package com.nuvei.nuvei_sdk.network;

import com.nuvei.nuvei_sdk.models.debit.DebitRequest;
import com.nuvei.nuvei_sdk.models.debit.DebitResponse;
import com.nuvei.nuvei_sdk.models.deleteCard.DeleteCardRequest;
import com.nuvei.nuvei_sdk.models.deleteCard.DeleteCardResponse;
import com.nuvei.nuvei_sdk.models.listCard.ListCardResponse;
import com.nuvei.nuvei_sdk.models.refund.RefundRequest;
import com.nuvei.nuvei_sdk.models.refund.RefundResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NuveiService {
    @GET("/v2/card/list")
    Call<ListCardResponse> getAllCards(
            @Query("uid") String uid
    );

    @POST("/v2/card/delete")
    Call<DeleteCardResponse> deleteCard(
            @Body DeleteCardRequest request
    );
    @POST("/v2/transaction/debit/")
    Call<DebitResponse> debit(@Body DebitRequest request);

    @POST("/v2/transaction/refund/")
    Call<RefundResponse> refund(@Body RefundRequest request);
}
