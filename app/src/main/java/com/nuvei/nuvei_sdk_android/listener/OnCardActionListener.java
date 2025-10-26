package com.nuvei.nuvei_sdk_android.listener;

import com.nuvei.nuvei_sdk.models.listCard.CardItemList;


public interface OnCardActionListener {
    void onDeleteCard(String cardToken);
    void onCardSelected(CardItemList card);
}
