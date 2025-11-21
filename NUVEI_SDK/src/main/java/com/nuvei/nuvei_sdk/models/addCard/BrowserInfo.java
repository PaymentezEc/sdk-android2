package com.nuvei.nuvei_sdk.models.addCard;

import com.google.gson.annotations.SerializedName;

public class BrowserInfo {
    private String ip;
    private String language;
    @SerializedName("java_enabled")
    private boolean javaEnabled;
    @SerializedName("js_enabled")
    private boolean jsEnabled;
    @SerializedName("color_depth")
    private int colorDepth;
    @SerializedName("screen_height")
    private int screenHeight;
    @SerializedName("screen_width")
    private int screenWidth;
    @SerializedName("timezone_offset")
    private int timezoneOffset;
    @SerializedName("user_agent")
    private String userAgent;
    @SerializedName("accept_header")
    private String acceptHeader;

    public BrowserInfo(String ip, String language, boolean javaEnabled, boolean jsEnabled, int colorDepth, int screenHeight, int screenWidth, int timezoneOffset, String userAgent, String acceptHeader) {
        this.ip = ip;
        this.language = language;
        this.javaEnabled = javaEnabled;
        this.jsEnabled = jsEnabled;
        this.colorDepth = colorDepth;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.timezoneOffset = timezoneOffset;
        this.userAgent = userAgent;
        this.acceptHeader = acceptHeader;
    }
}
