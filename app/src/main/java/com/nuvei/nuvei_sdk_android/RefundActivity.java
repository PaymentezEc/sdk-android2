package com.nuvei.nuvei_sdk_android;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.nuvei.nuvei_sdk.NuveiSDK;
import com.nuvei.nuvei_sdk.helper.NuveiCallBack;
import com.nuvei.nuvei_sdk.models.debit.DebitResponse;
import com.nuvei.nuvei_sdk.models.debit.DebitTransactionResponse;
import com.nuvei.nuvei_sdk.models.error.ErrorResponseModel;
import com.nuvei.nuvei_sdk.models.refund.OrderRefund;
import com.nuvei.nuvei_sdk.models.refund.RefundResponse;
import com.nuvei.nuvei_sdk.models.refund.TransactionRefund;

public class RefundActivity extends AppCompatActivity {

    private DebitResponse debitResponse;
    private RefundResponse refundResponse;
    private ProgressBar progressBar;
    private MaterialButton refundButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_refund);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView transactionIdTextView = findViewById(R.id.transaction_value);
        TextView amountTextView = findViewById(R.id.amount_value);
        TextView authorizationCodeTextView = findViewById(R.id.authorization_value);
        TextView statusTextView = findViewById(R.id.status_value);
        progressBar = findViewById(R.id.progress_bar);
        String debitResponseJson = getIntent().getStringExtra("DEBIT_RESPONSE");
        if(debitResponseJson != null){
            Gson gson = new Gson();
            debitResponse = gson.fromJson(debitResponseJson, DebitResponse.class);
            transactionIdTextView.setText(debitResponse.getTransaction().getId());
            amountTextView.setText("$" + debitResponse.getTransaction().getAmount().toString());
            authorizationCodeTextView.setText(debitResponse.getTransaction().getAuthorizationCode());
            statusTextView.setText(debitResponse.getTransaction().getStatus());
        }
        refundButton = findViewById(R.id.refund_button);
        refundButton.setEnabled(refundResponse == null);
        refundButton.setOnClickListener(view -> {
            refundPayment();
        });
    }

    private void refundPayment(){

        showLoading(true);

            DebitTransactionResponse transationInfo = debitResponse.getTransaction();

            TransactionRefund transactionRefund = new TransactionRefund(transationInfo.getId(), null);
            OrderRefund orderRefund = new OrderRefund(debitResponse.getTransaction().getAmount());
            NuveiSDK.getInstance().refundDebit(transactionRefund, orderRefund, true, new NuveiCallBack<RefundResponse>() {
                @Override
                public void onSucces(RefundResponse data) {
                    showLoading(false);
                    if (data != null) {
                        refundResponse = data;
                        refundButton.setEnabled(false);
                        refundButton.setBackgroundColor(Color.parseColor("#FF666666"));
                        refundButton.setTextColor(Color.parseColor("#FF000000"));
                        Toast.makeText(RefundActivity.this, "Refund status: " + data.getStatus(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RefundActivity.this, "Refund response is null.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(ErrorResponseModel error) {
                    showLoading(false);
                    Toast.makeText(RefundActivity.this, "Exception: " + error.getError().getDescription(), Toast.LENGTH_SHORT).show();
                }
            });




        }




    private void showLoading(boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            refundButton.setEnabled(false); // Disable the button while loading
        } else {
            progressBar.setVisibility(View.GONE);
            refundButton.setEnabled(true); // Re-enable the button
        }
    }

}