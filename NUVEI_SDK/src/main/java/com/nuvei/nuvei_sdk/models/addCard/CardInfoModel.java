package com.nuvei.nuvei_sdk.models.addCard;

import java.util.List;
import java.util.regex.Pattern;

public class CardInfoModel {
    private final String type;
    private final Pattern regex;
    private final String mask;
    private final int cvcNumber;
    private final List<Integer> validLengths;
    private final String typeCode;
    private final int iconRes;
    private final int[] gradientColor;

    public CardInfoModel(String type,
                         String regex,
                         String mask,
                         int cvcNumber,
                         List<Integer> validLengths,
                         String typeCode,
                         int iconRes,
                         int[] gradientColor) {
        this.type = type;
        this.regex = Pattern.compile(regex);
        this.mask = mask;
        this.cvcNumber = cvcNumber;
        this.validLengths = validLengths;
        this.typeCode = typeCode;
        this.iconRes = iconRes;
        this.gradientColor = gradientColor;
    }

    // Getters
    public String getType() { return type; }
    public Pattern getRegex() { return regex; }
    public String getMask() { return mask; }
    public int getCvcNumber() { return cvcNumber; }
    public List<Integer> getValidLengths() { return validLengths; }
    public String getTypeCode() { return typeCode; }
    public int getIconRes() {
        return iconRes;
    }
    public int[] getGradientColor() { return gradientColor; }
}
