package com.example.govimithuruapp.accountManagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.govimithuruapp.R;
import com.example.govimithuruapp.claimManagement.Claim;
import com.example.govimithuruapp.claimManagement.Claim1FActivity;
import com.example.govimithuruapp.core.BackendManager;
import com.example.govimithuruapp.core.LocaleManager;

import java.util.HashSet;
import java.util.Map;

public class WelcomeActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private HashSet<String> claimsToView;
    private final LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    private TextView myClaims, welcomeText;
    private Button bLocale;

    public static final String VIEW_CLAIM_ID = "com.example.govimithuruapp.CLAIM_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeText = (TextView) findViewById(R.id.TX_welcome);
        welcomeText.setText(getResources().getString(R.string.txt_Welcome));

        bLocale = (Button) findViewById(R.id.BT_toggleLan3);
        bLocale.setText(LocaleManager.getToggleText(this));

        myClaims = (TextView) findViewById(R.id.TX_myClaims);
        layoutParams.setMargins(0, 0, 0, 16);
        this.linearLayout = (LinearLayout) findViewById(R.id.LINLAY_claims);
        claimsToView = new HashSet<>();
        showClaims();
    }

    public void openNewClaim(View view) {
        Intent intent = new Intent(this, Claim1FActivity.class);
        intent.putExtra(VIEW_CLAIM_ID, "");
        startActivity(intent);
    }

    private void showClaims() {
        if (AuthController.getInstance().getCurrentUser().getNumOfClaims() > 0)
            myClaims.setText(getResources().getString(R.string.txt_myClaims));
        else myClaims.setText(getResources().getString(R.string.txt_noClaims));

        for (Map.Entry<String, Claim> entry : AuthController.getInstance().getCurrentUser().getAllClaims().entrySet()) {
            if (!claimsToView.contains(entry.getKey())) {
                Button btn = new Button(this);
                btn.setLayoutParams(layoutParams);
                btn.setText(entry.getValue().getTopic());
                btn.setAllCaps(false);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), Claim1FActivity.class);
                        intent.putExtra(VIEW_CLAIM_ID, entry.getValue().getClaimID());
                        startActivity(intent);
                    }
                });
                claimsToView.add(entry.getKey());
                this.linearLayout.addView(btn);
            }
        }
    }

    public void refresh(View view) {
        BackendManager.getInstance(this).getData(BackendManager.CLAIM_SUFFIX, BackendManager.ActionCodes.ONLY_CHECKING);
        AuthController.getInstance().getSavedUser(this);
        showClaims();
    }

    @Override
    protected void onRestart() {
        refresh(null);
        super.onRestart();
    }

    private void setView() {
        setContentView(R.layout.activity_welcome);

        welcomeText = (TextView) findViewById(R.id.TX_welcome);
        welcomeText.setText(getResources().getString(R.string.txt_Welcome));

        bLocale = (Button) findViewById(R.id.BT_toggleLan3);
        bLocale.setText(LocaleManager.getToggleText(this));

        myClaims = (TextView) findViewById(R.id.TX_myClaims);
        layoutParams.setMargins(0, 0, 0, 16);
        this.linearLayout = (LinearLayout) findViewById(R.id.LINLAY_claims);
        claimsToView = new HashSet<>();
        showClaims();
    }

    public void toggleLocale(View view) {
        LocaleManager.toggleLocale(this);
        setView();
    }
}