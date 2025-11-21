package com.nuvei.nuvei_sdk_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nuvei.nuvei_sdk.listener.AddCardListener;
import com.nuvei.nuvei_sdk.models.error.ErrorResponseModel;
import com.nuvei.nuvei_sdk.widget.NuveiAddCardForm;

public class AddCardActivity extends AppCompatActivity {



    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        NuveiAddCardForm addCardForm = findViewById(R.id.card_form_widget);

        addCardForm.setUserData("4", "erick.guillen@nuvei.com");
        progressBar = findViewById(R.id.progress_bar);
        addCardForm.setFormListener(new AddCardListener() {
            @Override
            public void onSucces(boolean success, String message) {

                if (success) {
                    showAlert("Succesfully", message);
                } else {
                    showAlert("Failure", message);
                }
            }

            @Override
            public void onError(ErrorResponseModel errorResponseModel) {

                showAlert("Error", errorResponseModel.getError().getDescription());
            }

            @Override
            public void onLoading(boolean isLoading) {
                Log.e("NuveiSDK", "Error: " + isLoading);
                showLoading(isLoading);
            }
        });
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showAlert(String title, String message) {
        runOnUiThread(() -> {
            new androidx.appcompat.app.AlertDialog.Builder(AddCardActivity.this)
                    .setTitle(title)
                    .setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("Close", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }
}