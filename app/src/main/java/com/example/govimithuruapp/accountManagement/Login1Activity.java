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

import static com.example.govimithuruapp.core.LocaleManager.setAppLocaleEnglish;
import static com.example.govimithuruapp.core.LocaleManager.setAppLocaleSinhala;

public class Login1Activity extends AppCompatActivity {
    public static final String EXTRA_NIC = "com.example.govimithuruapp.NIC";

    private EditText eNIC;
    private Button bSubmit;
    private TextView tLoginError;

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            tLoginError.setVisibility(View.GONE);
        }

        @Override
        public void afterTextChanged(Editable s) {
            bSubmit.setEnabled(eNIC.getText().toString().length() > 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        eNIC = (EditText) findViewById(R.id.ED_nic);
        bSubmit = (Button) findViewById(R.id.BT_login2);
        tLoginError = (TextView) findViewById(R.id.TX_loginError1);

        eNIC.addTextChangedListener(watcher);
    }

    public void checkLoginStep1(View view) {
        String nic = eNIC.getText().toString();
        AuthController.getInstance().loginStep1(this, nic);
    }

    public void setResultOfLoginStep1(boolean success) {
        if (success) {
            char userType = AuthController.getInstance().getCurrentUser().getUserType();
            Intent intent;
            if (userType == 'f') intent = new Intent(this, Login2FActivity.class);
            else intent = new Intent(this, Login2AActivity.class);
            intent.putExtra(EXTRA_NIC, AuthController.getInstance().getCurrentUser().getNIC());
            startActivity(intent);
            finish();
        } else {
            showError();
        }
    }

    private void showError() {
        tLoginError.setVisibility(View.VISIBLE);
        eNIC.setText("");
    }

    private void setView() {
        setContentView(R.layout.activity_login1);

        eNIC = (EditText) findViewById(R.id.ED_nic);
        bSubmit = (Button) findViewById(R.id.BT_login2);
        tLoginError = (TextView) findViewById(R.id.TX_loginError1);

        eNIC.addTextChangedListener(watcher);

    }

    public void changeLocaleToEN(View view) {
        setAppLocaleEnglish(this);
        setView();
    }

    public void changeLocaleToSI(View view) {
        setAppLocaleSinhala(this);
        setView();
    }

}