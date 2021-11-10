package com.example.govimithuruapp.claimManagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.govimithuruapp.R;

public class Claim1FActivity extends AppCompatActivity {

    public static final String CLAIM_OBJECT = "com.example.govimithuruapp.CLAIM_OBJECT";
    public static final int SUBMIT_EVIDENCE = 100;
    private Claim claim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim1_f);

        claim = new Claim("0001");
    }

    public void submitEvidence(View view) {
        Intent intent = new Intent(this, EvidenceFActivity.class);
        intent.putExtra(CLAIM_OBJECT, claim);
        startActivityForResult(intent, SUBMIT_EVIDENCE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUBMIT_EVIDENCE && resultCode == RESULT_OK) {
            if (data != null) {
                claim = (Claim) data.getParcelableExtra(CLAIM_OBJECT);
            }
        }
    }
}