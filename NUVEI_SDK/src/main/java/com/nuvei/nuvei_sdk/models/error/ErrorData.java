package com.nuvei.nuvei_sdk.models.error;

import com.google.gson.annotations.SerializedName;

public class ErrorData {
    private String type;
    private String help;
    private String description;

    public ErrorData(String type, String help, String description) {
        this.type = type;
        this.help = help;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getHelp() {
        return help;
    }

    public String getDescription() {
        return description;
    }
}
