package com.example.govimithuruapp.accountManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.govimithuruapp.R;

import static com.example.govimithuruapp.core.LocaleManager.setAppLocaleEnglish;
import static com.example.govimithuruapp.core.LocaleManager.setAppLocaleSinhala;
import static com.example.govimithuruapp.core.LocaleManager.setContextLocale;

public class Login1Activity extends AppCompatActivity {
    public static final String EXTRA_NIC = "com.example.govimithuruapp.NIC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
    }

    public void acceptNIC(View view) {
        Intent intent = new Intent(this, Login2FActivity.class);
        EditText ed_nic = (EditText) findViewById(R.id.ED_nic);
        String nic = ed_nic.getText().toString();
        intent.putExtra(EXTRA_NIC, nic);
        startActivity(intent);
    }

    public void changeLocaleToEN(View view) {
        setAppLocaleEnglish();
        setContextLocale(this);
        setContentView(R.layout.activity_login1);
    }

    public void changeLocaleToSI(View view) {
        setAppLocaleSinhala();
        setContextLocale(this);
        setContentView(R.layout.activity_login1);
    }

}