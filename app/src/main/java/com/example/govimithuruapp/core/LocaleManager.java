package com.example.govimithuruapp.core;

import android.content.Intent;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatActivity;

import com.example.govimithuruapp.accountManagement.AuthController;

import java.util.Locale;

public class LocaleManager {

    private static final Locale LOCALE_EN = new Locale("en");
    private static final Locale LOCALE_SI = new Locale("si");
    private static Configuration config = new Configuration();

    public static void setAppLocaleEnglish(AppCompatActivity activity) {
        Locale.setDefault(LOCALE_EN);
        config.locale = LOCALE_EN;
        setContextLocale(activity);
    }

    public static void setAppLocaleSinhala(AppCompatActivity activity) {
        Locale.setDefault(LOCALE_SI);
        config.locale = LOCALE_SI;
        setContextLocale(activity);
    }

    private static void setContextLocale(AppCompatActivity activity) {
        activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
    }

    public static void initializeUserLocale(AppCompatActivity activity) {
        if (AuthController.getInstance().getCurrentUser().getPreferredLocale() == 's') {
            setAppLocaleSinhala(activity);
        } else {
            setAppLocaleEnglish(activity);
        }
        setContextLocale(activity);
    }
}
