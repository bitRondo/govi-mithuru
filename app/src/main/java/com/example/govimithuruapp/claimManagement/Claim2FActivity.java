package com.example.govimithuruapp.claimManagement;

import com.example.govimithuruapp.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Claim2FActivity extends AppCompatActivity {

    private CheckBox checkBox;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim2_f);

        checkBox = (CheckBox) findViewById(R.id.CHECK_pledge);
        sendButton = (Button) findViewById(R.id.BT_sendClaim);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sendButton.setEnabled(isChecked);
            }
        });
    }

    public synchronized void sendClaim(View view) {
        sendButton.setEnabled(false);
        sendButton.setText(getResources().getString(R.string.btnText_submitting));
        Intent intent = new Intent(this, Claim3FActivity.class);
        startActivity(intent);
        finish();
    }
}