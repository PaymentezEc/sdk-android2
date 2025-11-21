package com.nuvei.nuvei_sdk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.nuvei.nuvei_sdk.network.CresClient;
import com.nuvei.nuvei_sdk.network.CresService;

public class NuveiWebViewActivity extends AppCompatActivity {

    public static final String EXTRA_HTML = "extra_html";
    private static NuveiWebViewActivity currentInstance;

    public static void closeIfOpen() {
        if (currentInstance != null) {
            currentInstance.runOnUiThread(() -> currentInstance.finish());
            currentInstance = null;
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentInstance = this;

        WebView webView = new WebView(this);
        setContentView(webView);

        String html = getIntent().getStringExtra(EXTRA_HTML);
        if (html != null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentInstance == this) currentInstance = null;
    }
}