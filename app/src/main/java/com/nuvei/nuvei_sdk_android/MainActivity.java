package com.nuvei.nuvei_sdk_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.nuvei.nuvei_sdk.internal.NuveiSDK;
import com.nuvei.nuvei_sdk_android.helper.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        MaterialButton initSimulation = findViewById(R.id.simulationButton);
        initSimulation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NuveiSDK.getInstance().initEnvironment(Constants.APPCODE, Constants.APPKEY, Constants.SERVERCODE, Constants.SERVERKEY, Constants.CLIENTID, Constants.CLIENTCODE, true);
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });
    }
}