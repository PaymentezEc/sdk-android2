package com.nuvei.nuvei_sdk.helper;

import android.os.Build;

import com.nuvei.nuvei_sdk.R;
import com.nuvei.nuvei_sdk.models.addCard.CardInfoModel;

import java.time.LocalDate;

public class CardHelper {

    /**
     * Checks the input string to see whether or not it is a valid Luhn number.
     *
     * @param cardNumber a String that may or may not represent a valid Luhn number
     * @return {@code true} if and only if the input value is a valid Luhn number
     */
    public static boolean validLuhnNumber(String cardNumber){
        boolean isOdd= true;
        int sum = 0;

        for(int index = cardNumber.length() - 1; index >= 0; index--){
            char c = cardNumber.charAt(index);
            if(!Character.isDigit(c)){
                return  false;
            }

            int digitInteger = Character.getNumericValue(c);
            isOdd = !isOdd;
            if(isOdd){
                digitInteger *=2;
            }

            if(digitInteger > 9){
                digitInteger -= 9;
            }

            sum += digitInteger;
        }
        return sum%10 == 0;
    }


    public static String formatCardNumber(String number) {
        // 1. limpiar: solo dígitos
        String clean = number.replaceAll("\\D", "");

        // 2. obtener la máscara según el tipo de tarjeta
        CardInfoModel def = getCardInfo(clean);
        String mask = def.getMask();

        // 3. construir resultado
        StringBuilder result = new StringBuilder();
        int i = 0;

        for (char c : mask.toCharArray()) {
            if (c == '#') {
                if (i >= clean.length()) break; // si no hay más dígitos
                result.append(clean.charAt(i++));
            } else {
                if (i < clean.length()) result.append(c);
            }
        }

        return result.toString();
    }


    /***
     * Expiry Date Utils
     */

    public static String validateExpiryDate(String expiry) {
        if (expiry == null || expiry.trim().length() != 5) {
            return "Expiry date is not valid";
        }

        String[] parts = expiry.split("/");
        if (parts.length != 2) {
            return "Expiry date is not valid";
        }

        int month, year;
        try {
            month = Integer.parseInt(parts[0]);
            year = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            return "Expiry date is not valid";
        }

        if (month < 1 || month > 12) {
            return "Expiry date is not valid";
        }

        // Fecha actual
        java.util.Calendar now = java.util.Calendar.getInstance();
        int currentYear = now.get(java.util.Calendar.YEAR) % 100; // últimos 2 dígitos
        int currentMonth = now.get(java.util.Calendar.MONTH) + 1;  // Calendar.MONTH es 0-index

        if (year < currentYear || (year == currentYear && month < currentMonth)) {
            return "Expiry date is not valid";
        }

        return null; // válido
    }



    public static int completeYear(int year) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int currentYear = LocalDate.now().getYear();
            int currentCentury = (currentYear / 100) * 100;

            if (year < (currentYear % 100)) {
                return currentCentury + 100 + year;
            } else {
                return currentCentury + year;
            }
        }
        return 0;
    }


    public static String formatExpiryInput(String value) {
        if (value == null) return "";

        // Solo mantener dígitos
        String digits = value.replaceAll("\\D", "");

        // Cortar a máximo 4 dígitos (MMYY)
        if (digits.length() > 4) {
            digits = digits.substring(0, 4);
        }

        if (digits.isEmpty()) {
            return "";
        }

        String month;
        String year = "";

        if (digits.length() >= 2) {
            month = digits.substring(0, 2);
            year = digits.substring(2);
        } else {
            month = digits;
        }

        // Si se ingresa un solo dígito mayor a '1', lo convertimos a "0X"
        if (month.length() == 1 && Character.getNumericValue(month.charAt(0)) > 1) {
            month = "0" + month;
            digits = month + (digits.length() > 1 ? digits.substring(1) : "");
            year = digits.length() > 2 ? digits.substring(2) : "";
        }

        // Si ya hay 2 dígitos y el mes no es válido, lo corregimos a "01"
        if (month.length() == 2) {
            int monthNum = Integer.parseInt(month);
            if (monthNum < 1 || monthNum > 12) {
                // Corregimos automáticamente
                month = "01";
            }
        }

        // Construir salida con "/"
        if (!year.isEmpty()) {
            return month + "/" + year;
        }
        else {
            return month;
        }
    }




    public static CardInfoModel getCardInfo(String number) {
        String clean = number.replaceAll("\\D", "");

        for (CardInfoModel info : CardTypes.ALL) {
            if (info.getRegex().matcher(clean).find()) {
                return info;
            }
        }

        return new CardInfoModel(
                "Unknown",
                "^$",
                "#### #### #### ####",
                3,
                java.util.Arrays.asList(16),
                "",
                R.drawable.ic_unknown,
                new int[]{0xFF333333, 0xFF000000}
        );
    }
}
