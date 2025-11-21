package com.nuvei.nuvei_sdk.helper;

import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;

import com.nuvei.nuvei_sdk.models.addCard.BrowserInfo;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.TimeZone;

public class GlobalHelper {
    private static  String getUniqueToken( String client_key, String time_stamp_auth ){
        String unique_token = client_key +  time_stamp_auth;
        return new String(Hex.encodeHex(DigestUtils.sha256(unique_token)));
    }

    public static  String getAuthToken(String key, String code ){
        long time_stamp_time = System.currentTimeMillis()/1000;
        Log.v("Key", String.valueOf(key.isEmpty()));
        Log.v("code", String.valueOf(code.isEmpty()));
        Log.v("timestap", String.valueOf(time_stamp_time));
        String time_stamp_string = String.valueOf(time_stamp_time);
        String auth_token = code + ";" + time_stamp_string + ";" + getUniqueToken(key, time_stamp_string);
        Log.v("token", Base64.encodeToString(auth_token.getBytes(), Base64.NO_WRAP));
        return Base64.encodeToString(auth_token.getBytes(), Base64.NO_WRAP);
    }


    public static BrowserInfo getBrowserInfo(Context context){
        try {
            URL url = new URL("https://api.ipify.org/?format=json");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            String ip = json.getString("ip");
            String language = Locale.getDefault().getLanguage();
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;
            int timezoneOffset = TimeZone.getDefault().getRawOffset() / (1000* 60 * 60);
            boolean javaEnabled = false;
            boolean jsEnabled = true;
            int colorDepth = 24;
            String acceptHeader = "text/html";
            String userAgent = getUserAgent();
            BrowserInfo browserInfo = new BrowserInfo(ip, language, javaEnabled, jsEnabled, colorDepth, screenHeight, screenWidth, timezoneOffset, userAgent, acceptHeader);
            return  browserInfo;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getUserAgent() {
        String ua;
        ua = System.getProperty("http.agent");
        return ua;
    }
}
