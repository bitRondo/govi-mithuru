package com.example.govimithuruapp.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilityManager {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat dateAndTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Singleton
    private static UtilityManager instance;
    private UtilityManager () {}

    public static UtilityManager getInstance() {
        if (instance == null) instance = new UtilityManager();
        return instance;
    }

    public String formatDate(Date date) {
        if (date == null) date = new Date();
        return dateFormat.format(date);
    }

    public String formatDateAndTime(Date date) {
        if (date == null) date = new Date();
        return dateAndTimeFormat.format(date);
    }

    public String padNumber(int number, int padSize, String padCharacter) {
        String pad = String.format("%d", number);
        for (int i = pad.length(); i < padSize; i++) pad = padCharacter + pad;
        return pad;
    }

}
