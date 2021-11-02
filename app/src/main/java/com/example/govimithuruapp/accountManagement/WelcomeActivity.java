package com.example.govimithuruapp.accountManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.govimithuruapp.R;
import com.example.govimithuruapp.claimManagement.EvidenceFActivity;

import static com.example.govimithuruapp.core.LocaleManager.setContextLocale;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContextLocale(this);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        String nic = intent.getStringExtra(Login1Activity.EXTRA_NIC);
        String regNo = intent.getStringExtra(Login2FActivity.EXTRA_FARMER_REG_NO);

        TextView tx_nic = (TextView) findViewById(R.id.TX_nicVal);
        tx_nic.setText(nic);
        TextView tx_regNo = (TextView) findViewById(R.id.TX_regNoVal);
        tx_regNo.setText(regNo);
    }

    public void goToEvidenceSubmission(View view) {
        Intent intent = new Intent(this, EvidenceFActivity.class);
        startActivity(intent);
    }
}