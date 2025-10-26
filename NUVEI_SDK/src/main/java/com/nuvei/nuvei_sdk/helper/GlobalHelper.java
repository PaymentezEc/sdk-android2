package com.nuvei.nuvei_sdk.helper;

import android.util.Base64;
import android.util.Log;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class GlobalHelper {
    private static  String getUniqueToken( String client_key, String time_stamp_auth ){
        String unique_token = client_key +  time_stamp_auth;
        return new String(Hex.encodeHex(DigestUtils.sha256(unique_token)));
    }

    public static  String getAuthToken(String key, String code ){
        long time_stamp_time = System.currentTimeMillis()/1000;
        Log.v("Key", String.valueOf(key.isEmpty()));
        Log.v("code", String.valueOf(code.isEmpty()));
        String time_stamp_string = String.valueOf(time_stamp_time);
        String auth_token = code + ";" + time_stamp_string + ";" + getUniqueToken(key, time_stamp_string);
        Log.v("token", Base64.encodeToString(auth_token.getBytes(), Base64.NO_WRAP));
        return Base64.encodeToString(auth_token.getBytes(), Base64.NO_WRAP);
    }
}
