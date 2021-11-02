package com.example.govimithuruapp.core;

import android.content.Intent;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LocaleManager {

    private static final Locale LOCALE_EN = new Locale("en");
    private static final Locale LOCALE_SI = new Locale("si");
    private static Configuration config = new Configuration();

    public static void setAppLocaleEnglish() {
        Locale.setDefault(LOCALE_EN);
        config.locale = LOCALE_EN;
    }

    public static void setAppLocaleSinhala() {
        Locale.setDefault(LOCALE_SI);
        config.locale = LOCALE_SI;
    }

    public static void setContextLocale(AppCompatActivity activity) {
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
    }
}
