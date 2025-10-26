package com.nuvei.nuvei_sdk.models.listCard;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListCardResponse {
    private List<CardItemList> cards;
    @SerializedName("result_size")
    private int resultSize;

    public ListCardResponse(List<CardItemList> cards, int resultSize) {
        this.cards = cards;
        this.resultSize = resultSize;
    }

    public List<CardItemList> getCards() {
        return cards;
    }

    public int getResultSize() {
        return resultSize;
    }
}
