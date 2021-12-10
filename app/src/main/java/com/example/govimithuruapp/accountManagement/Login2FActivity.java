package com.example.govimithuruapp.accountManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.govimithuruapp.R;
import com.example.govimithuruapp.core.LocaleManager;

public class Login2FActivity extends AppCompatActivity {

    public static final String EXTRA_FARMER_REG_NO = "com.example.govimithuruapp.FARMER_REG_NO";
    private String nic = "";

    private EditText eRegNo, ed_nic;
    private Button bLogin, bLocale;
    private TextView tLoginError;
    private Intent intent;

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            bLogin.setEnabled(eRegNo.getText().toString().length() > 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2_f);

        intent = getIntent();
        nic = intent.getStringExtra(Login1Activity.EXTRA_NIC);

        ed_nic = (EditText) findViewById(R.id.ED_nic);
        ed_nic.setText(nic);

        eRegNo = (EditText) findViewById(R.id.ED_farmerNo);
        bLogin = (Button) findViewById(R.id.BT_login2);
        bLocale = (Button) findViewById(R.id.BT_toggleLan2);
        tLoginError = (TextView) findViewById(R.id.TX_loginError2);

        bLocale.setText(LocaleManager.getToggleText(this));
        eRegNo.addTextChangedListener(watcher);
    }

    public void checkLoginStep2F(View view) {
        String farmerRegNo = eRegNo.getText().toString();
        tLoginError.setVisibility(View.GONE);
        boolean success = AuthController.getInstance().loginStep2(farmerRegNo);
        if (success) {
            AuthController.getInstance().saveUser(this);
            LocaleManager.initializeUserLocale(this);

            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            showError();
        }
    }

    private void showError() {
        tLoginError.setVisibility(View.VISIBLE);
        eRegNo.setText("");
    }

    private void setView() {
        setContentView(R.layout.activity_login2_f);

        intent = getIntent();
        nic = intent.getStringExtra(Login1Activity.EXTRA_NIC);

        ed_nic = (EditText) findViewById(R.id.ED_nic);
        ed_nic.setText(nic);

        eRegNo = (EditText) findViewById(R.id.ED_farmerNo);
        bLogin = (Button) findViewById(R.id.BT_login2);
        bLocale = (Button) findViewById(R.id.BT_toggleLan2);
        tLoginError = (TextView) findViewById(R.id.TX_loginError2);

        bLocale.setText(LocaleManager.getToggleText(this));
        eRegNo.addTextChangedListener(watcher);
    }

    public void toggleLocale(View view) {
        LocaleManager.toggleLocale(this);
        setView();
    }
}