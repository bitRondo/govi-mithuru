package com.example.govimithuruapp.accountManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.govimithuruapp.R;

import static com.example.govimithuruapp.core.LocaleManager.setContextLocale;

public class Login2FActivity extends AppCompatActivity {

    public static final String EXTRA_FARMER_REG_NO = "com.example.govimithuruapp.FARMER_REG_NO";
    private String nic = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContextLocale(this);
        setContentView(R.layout.activity_login2_f);

        Intent intent = getIntent();
        nic = intent.getStringExtra(Login1Activity.EXTRA_NIC);

        EditText ed_nic = (EditText) findViewById(R.id.ED_nic);
        ed_nic.setText(nic);
    }

    public void acceptFarmerRegNo(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        EditText ed_farmerRegNo = (EditText) findViewById(R.id.ED_farmerNo);
        String farmerRegNo = ed_farmerRegNo.getText().toString();
        intent.putExtra(Login1Activity.EXTRA_NIC, nic);
        intent.putExtra(EXTRA_FARMER_REG_NO, farmerRegNo);
        startActivity(intent);
    }
}