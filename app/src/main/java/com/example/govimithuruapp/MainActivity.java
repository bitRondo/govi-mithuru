package com.example.govimithuruapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.govimithuruapp.accountManagement.AuthController;
import com.example.govimithuruapp.accountManagement.Login1Activity;
import com.example.govimithuruapp.accountManagement.User;
import com.example.govimithuruapp.accountManagement.WelcomeActivity;
import com.example.govimithuruapp.core.LocaleManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = AuthController.getInstance().getSavedUser(this);
        Intent intent;
        if ((user != null) && (user.getUserType() != 'u')) {
            LocaleManager.initializeUserLocale(this);
            intent = new Intent(this, WelcomeActivity.class);
        } else {
            LocaleManager.initializeAppLocaleToEN(this);
            intent = new Intent(this, Login1Activity.class);
        }
        startActivity(intent);
        finish();
    }
}