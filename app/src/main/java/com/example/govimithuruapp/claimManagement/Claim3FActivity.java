package com.example.govimithuruapp.claimManagement;

import com.example.govimithuruapp.R;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Claim3FActivity extends AppCompatActivity {
    private Claim claim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim3_f);

        claim = ClaimManager.getInstance().getCurrentClaim();
        sendClaim();
    }

    private void sendClaim() {
        ClaimManager.getInstance().submitClaim(claim, this);
        finish();
    }
}