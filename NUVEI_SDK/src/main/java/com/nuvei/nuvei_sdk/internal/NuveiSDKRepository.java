package com.nuvei.nuvei_sdk.internal;

import android.util.Log;

import com.google.gson.Gson;
import com.nuvei.nuvei_sdk.helper.NuveiCallBack;
import com.nuvei.nuvei_sdk.models.Environment;
import com.nuvei.nuvei_sdk.models.debit.DebitRequest;
import com.nuvei.nuvei_sdk.models.CardToken;
import com.nuvei.nuvei_sdk.models.debit.DebitResponse;
import com.nuvei.nuvei_sdk.models.deleteCard.DeleteCardRequest;
import com.nuvei.nuvei_sdk.models.deleteCard.DeleteCardResponse;
import com.nuvei.nuvei_sdk.models.deleteCard.UserIdDelete;
import com.nuvei.nuvei_sdk.models.error.ErrorData;
import com.nuvei.nuvei_sdk.models.error.ErrorResponseModel;
import com.nuvei.nuvei_sdk.models.listCard.CardItemList;
import com.nuvei.nuvei_sdk.models.listCard.ListCardResponse;
import com.nuvei.nuvei_sdk.models.refund.OrderRefund;
import com.nuvei.nuvei_sdk.models.refund.RefundRequest;
import com.nuvei.nuvei_sdk.models.refund.RefundResponse;
import com.nuvei.nuvei_sdk.models.refund.TransactionRefund;
import com.nuvei.nuvei_sdk.network.NuveiClient;
import com.nuvei.nuvei_sdk.network.NuveiService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuveiSDKRepository {

    private Environment environment;

    private ErrorResponseModel parseError(Response<?> response) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(response.errorBody().string(), ErrorResponseModel.class);
        } catch (Exception e) {
            return new ErrorResponseModel(
                    new ErrorData("Unknown Error", "", "Error parsing response: " + e.getMessage())
            );
        }
    }


    public void initEnvironment(String appCode, String appKey, String serverCode, String serverKey, String clientId, String clientCode, boolean testMode){
        this.environment = new Environment(appCode, appKey, serverCode, serverKey, clientId, clientCode, testMode);
        Log.v("Environment", this.environment.toString());
    }


    public Environment getEnvironment() {
        if (environment == null) {
            throw new IllegalStateException("NuveiSDK not initializated. First call initEnvironment().");
        }
        return environment;
    }


    public  void listCards(String userId, NuveiCallBack callback){
         NuveiClient client = new NuveiClient(getEnvironment().getServerCode(), getEnvironment().getServerKey());
         NuveiService service = client.getService();
         Call<ListCardResponse> call = service.getAllCards(userId);
         call.enqueue(new Callback<ListCardResponse>() {
             @Override
             public void onResponse(Call<ListCardResponse> call, Response<ListCardResponse> response) {
                 if(response.isSuccessful() && response.body() != null){
                     List<CardItemList> listCardResponse = response.body().getCards().stream().filter(cardItemList ->
                         cardItemList.getStatus().equals("valid")
                     ).collect(Collectors.toList());

                     ListCardResponse avaliableCards = new ListCardResponse(listCardResponse, listCardResponse.size());
                     callback.onSucces(avaliableCards);
                 }else{
                     ErrorResponseModel error = parseError(response);
                     callback.onError(error);
                 }
             }

             @Override
             public void onFailure(Call<ListCardResponse> call, Throwable t) {
                 ErrorResponseModel error = new ErrorResponseModel(
                         new ErrorData("NetworkError", "", t.getMessage())
                 );
                 Log.v("ErrorLoadCard", error.getError().getDescription() );
                 callback.onError(error);
             }
         });
     }


     public  void deleteCard(String userId, String cardToken,  NuveiCallBack callback){
         NuveiClient client = new NuveiClient(getEnvironment().getServerCode(), getEnvironment().getServerKey());
         NuveiService service = client.getService();
         CardToken cardTokenDelete = new CardToken(cardToken);
         UserIdDelete userIdDelete = new UserIdDelete(userId);
         DeleteCardRequest deleteCardRequest = new DeleteCardRequest(cardTokenDelete, userIdDelete);

         Call<DeleteCardResponse> call = service.deleteCard(deleteCardRequest);
         call.enqueue(new Callback<DeleteCardResponse>() {
             @Override
             public void onResponse(Call<DeleteCardResponse> call, Response<DeleteCardResponse> response) {
                 if(response.isSuccessful() && response.body() != null){
                     callback.onSucces(response.body());
                 }else{
                     ErrorResponseModel error = parseError(response);
                     callback.onError(error);
                 }
             }

             @Override
             public void onFailure(Call<DeleteCardResponse> call, Throwable t) {
                 ErrorResponseModel error = new ErrorResponseModel(
                         new ErrorData("NetworkError", "", t.getMessage())
                 );
                 Log.v("Error on delete Card", error.getError().getDescription() );
                 callback.onError(error);
             }
         });
     }


     public void paymentDebit(DebitRequest debitRequest,  NuveiCallBack callBack){
         NuveiClient client = new NuveiClient(getEnvironment().getServerCode(), getEnvironment().getServerKey());
         NuveiService service = client.getService();
         Call<DebitResponse> call = service.debit(debitRequest);
         call.enqueue(new Callback<DebitResponse>() {
             @Override
             public void onResponse(Call<DebitResponse> call, Response<DebitResponse> response) {
                 if(response.isSuccessful() && response.body() != null){
                     callBack.onSucces(response.body());
                 }else{
                     ErrorResponseModel error = parseError(response);
                     callBack.onError(error);
                 }
             }

             @Override
             public void onFailure(Call<DebitResponse> call, Throwable t) {
                 ErrorResponseModel error = new ErrorResponseModel(
                         new ErrorData("NetworkError", "", t.getMessage())
                 );
                 Log.v("Error on delete Card", error.getError().getDescription() );
                 callBack.onError(error);
             }
         });

     }

    public void refundDebit(TransactionRefund transactionRefund, OrderRefund orderRefund, boolean moreInfo, NuveiCallBack callBack) {


        RefundRequest request = new RefundRequest(transactionRefund, orderRefund, moreInfo);
        NuveiClient client = new NuveiClient(getEnvironment().getServerCode(), getEnvironment().getServerKey());
        NuveiService service = client.getService();

        Call<RefundResponse> call = service.refund(request);

        call.enqueue(new Callback<RefundResponse>() {
            @Override
            public void onResponse(Call<RefundResponse> call, Response<RefundResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    callBack.onSucces(response.body());
                }else{
                    ErrorResponseModel error = parseError(response);
                    callBack.onError(error);
                }
            }

            @Override
            public void onFailure(Call<RefundResponse> call, Throwable t) {
                ErrorResponseModel error = new ErrorResponseModel(
                        new ErrorData("NetworkError", "", t.getMessage())
                );
                Log.v("Error on delete Card", error.getError().getDescription() );
                callBack.onError(error);
            }
        });

    }
}
