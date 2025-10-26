package com.nuvei.nuvei_sdk_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nuvei.nuvei_sdk.models.listCard.CardItemList;
import com.nuvei.nuvei_sdk_android.R;
import com.nuvei.nuvei_sdk_android.listener.OnCardActionListener;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{
    private List<CardItemList> cardList;
    private OnCardActionListener listener;
    public CardAdapter(List<CardItemList> cardList, OnCardActionListener listener) {
        this.cardList = cardList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);

        return new CardViewHolder(view);
    }

    public void deleteCard(String tokencard){



    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardItemList card = cardList.get(position);
        holder.cardName.setText("Name: " + card.getHolderName());
        holder.cardNumber.setText("Card's number: **** **** **** " + card.getNumber());
        holder.deleteButton.setOnClickListener(view -> {
            if(listener != null){
                listener.onDeleteCard(card.getToken());
            }
        });
        holder.itemView.setOnClickListener(v->{
            if(listener != null){
                listener.onCardSelected(card);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardLogo;
        ImageView deleteButton;
        TextView cardName;
        TextView cardNumber;

        // ... Y los demás TextViews

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardLogo = itemView.findViewById(R.id.card_logo);
            deleteButton = itemView.findViewById(R.id.delete_button);
            cardName = itemView.findViewById(R.id.card_name);
            cardNumber = itemView.findViewById(R.id.card_number);
            // ...
        }
    }
}
