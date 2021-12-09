package com.example.govimithuruapp.accountManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.govimithuruapp.R;
import com.example.govimithuruapp.claimManagement.Claim1FActivity;

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

        TextView welcomeText = (TextView) findViewById(R.id.TX_welcome);
        welcomeText.setText(getResources().getString(R.string.txt_Welcome));

        TextView tx_nic = (TextView) findViewById(R.id.TX_nicVal);
        tx_nic.setText(nic);
        TextView tx_regNo = (TextView) findViewById(R.id.TX_regNoVal);
        tx_regNo.setText(regNo);

        System.out.println(AuthController.getInstance().getCurrentUser().getAllClaims().size());
    }

    public void openNewClaim(View view) {
        Intent intent = new Intent(this, Claim1FActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        AuthController.getInstance().saveUser(this);
        System.out.println(AuthController.getInstance().getCurrentUser().getAllClaims().size());
        super.onRestart();
    }

    @Override
    public void finish() {
        AuthController.getInstance().saveUser(this);
        super.finish();
    }
}