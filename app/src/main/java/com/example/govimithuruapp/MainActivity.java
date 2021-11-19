package com.example.govimithuruapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.govimithuruapp.accountManagement.AuthController;
import com.example.govimithuruapp.accountManagement.Login1Activity;
import com.example.govimithuruapp.accountManagement.WelcomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // boolean saved = AuthController.getInstance().saveUser(this);

        boolean gotUser = AuthController.getInstance().getSavedUser(this) && false;
        Intent intent;
        if (gotUser) {
            intent = new Intent(this, WelcomeActivity.class);
        } else {
            intent = new Intent(this, Login1Activity.class);
        }
        startActivity(intent);
        finish();
    }
}