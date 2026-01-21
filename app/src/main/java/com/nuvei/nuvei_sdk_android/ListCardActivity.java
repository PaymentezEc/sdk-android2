package com.nuvei.nuvei_sdk_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.nuvei.nuvei_sdk.internal.NuveiSDK;
import com.nuvei.nuvei_sdk.helper.NuveiCallBack;
import com.nuvei.nuvei_sdk.models.deleteCard.DeleteCardResponse;
import com.nuvei.nuvei_sdk.models.error.ErrorResponseModel;
import com.nuvei.nuvei_sdk.models.listCard.CardItemList;
import com.nuvei.nuvei_sdk.models.listCard.ListCardResponse;
import com.nuvei.nuvei_sdk_android.adapter.CardAdapter;
import com.nuvei.nuvei_sdk_android.listener.OnCardActionListener;

import java.util.List;

public class ListCardActivity extends AppCompatActivity  implements OnCardActionListener {
    private ProgressBar progressBar;
    private CardAdapter cardAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.card_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MaterialButton buttonReload = findViewById(R.id.reload_button);
        buttonReload.setOnClickListener(v-> {
            recyclerView.setAdapter(null);
            loadCards();
        });

        MaterialButton addCardButton = findViewById(R.id.add_card_button);
        addCardButton.setOnClickListener(v->{
            Intent intent = new Intent(ListCardActivity.this, AddCardActivity.class);
            startActivity(intent);
        });


        loadCards();
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private  void loadCards(){
        showLoading(true);
        NuveiSDK.getInstance().getAllCard("4", new NuveiCallBack<ListCardResponse>() {
            @Override
            public void onSucces(ListCardResponse data) {
                showLoading(false);
                List<CardItemList> cardList =
                data.getCards();

                if(cardList != null){
                    cardAdapter = new CardAdapter(cardList, ListCardActivity.this);
                    recyclerView.setAdapter(cardAdapter);
                }

            }

            @Override
            public void onError(ErrorResponseModel error) {
                showLoading(false);
                Log.e("Integrador", "Error: " + error.getError().getType());


            }
        });
    }


    @Override
    public void onDeleteCard(String cardToken) {
        showLoading(true);
        //Log.v("elemincacion", "eliminar");
        NuveiSDK.getInstance().deleteCard("4", cardToken, new NuveiCallBack<DeleteCardResponse>() {
            @Override
            public void onSucces(DeleteCardResponse data) {
                loadCards();
                showLoading(false);
                Toast.makeText(ListCardActivity.this, "Delete Succesfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(ErrorResponseModel error) {
                showLoading(false);
                Toast.makeText(ListCardActivity.this, "Error to delete card", Toast.LENGTH_SHORT).show();
                Log.e("Integrador", "Error: " + error.getError().getType());

            }
        });
    }

    public void onCardSelected(CardItemList card) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("CARD_NAME", card.getHolderName());
        resultIntent.putExtra("CARD_NUMBER", card.getNumber());
        resultIntent.putExtra("CARD_TOKEN", card.getToken());
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}