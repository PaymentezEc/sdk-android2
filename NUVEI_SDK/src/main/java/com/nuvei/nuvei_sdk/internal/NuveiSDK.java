package com.nuvei.nuvei_sdk.internal;

import com.nuvei.nuvei_sdk.helper.NuveiCallBack;

import com.nuvei.nuvei_sdk.models.Environment;
import com.nuvei.nuvei_sdk.models.debit.DebitRequest;
import com.nuvei.nuvei_sdk.models.debit.DebitResponse;
import com.nuvei.nuvei_sdk.models.deleteCard.DeleteCardResponse;
import com.nuvei.nuvei_sdk.models.listCard.ListCardResponse;
import com.nuvei.nuvei_sdk.models.refund.OrderRefund;
import com.nuvei.nuvei_sdk.models.refund.RefundResponse;
import com.nuvei.nuvei_sdk.models.refund.TransactionRefund;

public class NuveiSDK {
    private static NuveiSDK instance;
    private final NuveiSDKRepository nuveiSDKRepository;

    public NuveiSDK(
    ){
        this.nuveiSDKRepository = new NuveiSDKRepository();
    }

    public static synchronized NuveiSDK getInstance() {
        if (instance == null) {
            instance = new NuveiSDK();
        }
        return instance;
    }
    public void initEnvironment(String appCode, String appKey, String serverCode, String serverKey, String clientId, String clientCode, boolean testMode){
        nuveiSDKRepository.initEnvironment(appCode, appKey, serverCode, serverKey,clientId,clientCode,testMode);
    }
    public Environment getEnvironment() {
      return  nuveiSDKRepository.getEnvironment();
    }


     public void getAllCard(String userId, NuveiCallBack<ListCardResponse> callBack){
        nuveiSDKRepository.listCards(userId, callBack);
    }

    public void deleteCard(String userId, String cardToken, NuveiCallBack<DeleteCardResponse> callBack){
        nuveiSDKRepository.deleteCard(userId,cardToken, callBack);
    }

    public void paymentDebit(DebitRequest debitRequest, NuveiCallBack<DebitResponse> callBack){
        nuveiSDKRepository.paymentDebit(debitRequest, callBack);
    }

    public void  refundDebit(TransactionRefund transactionRefund, OrderRefund orderRefund, boolean moreInfo, NuveiCallBack<RefundResponse> callBack){
        nuveiSDKRepository.refundDebit(transactionRefund, orderRefund, moreInfo, callBack);
    }




}
