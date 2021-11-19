package com.example.govimithuruapp.claimManagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.govimithuruapp.R;
import com.example.govimithuruapp.accountManagement.AuthController;
import com.example.govimithuruapp.accountManagement.User;
import com.example.govimithuruapp.core.UtilityManager;

import java.util.Calendar;
import java.util.Date;

public class Claim1FActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String CLAIM_OBJECT = "com.example.govimithuruapp.CLAIM_OBJECT";
    public static final int SUBMIT_EVIDENCE = 100;

    private static final Calendar CULT_CAL = Calendar.getInstance();
    private static final Calendar DAM_CAL = Calendar.getInstance();

    private static final String[] CAUSES = {"FLOOD", "DROUGHT", "WILD ELEPHANTS", "FIRE", "DISEASES/PESTS", "OTHER"};
    private static final String[] LEVELS = {"COMPLETE DAMAGE", "PARTIAL DAMAGE", "MINOR DAMAGE"};

    private Claim claim;

    private TextView tTopic;
    private EditText eCultDate, eDamDate;
    private TextView tBrieflyExplain;
    private EditText eOtherCause;
    private Button submitEvidenceButton;
    private EditText e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16;
    private static EditText[] mandatoryInputs;

    private final TextWatcher mandatoryWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean shouldEnable = true;
            for (EditText input : mandatoryInputs) {
                if (input.getText().toString().length() == 0) shouldEnable = false;
            }
            submitEvidenceButton.setEnabled(shouldEnable);
        }
    };

    private final TextWatcher topicWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = e0.getText().toString();
            tTopic.setText(text);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim1_f);

        submitEvidenceButton = (Button) findViewById(R.id.BTN_submitEvidence);
        submitEvidenceButton.setEnabled(false);

        e0 = (EditText) findViewById(R.id.ED_claimTopic);
        e1 = (EditText) findViewById(R.id.ED_agriServiceCenter);
        e2 = (EditText) findViewById(R.id.ED_farmerRegNo);
        e3 = (EditText) findViewById(R.id.ED_farmerName);
        e4 = (EditText) findViewById(R.id.ED_farmerPhone);
        e5 = (EditText) findViewById(R.id.ED_farmerNIC);
        e6 = (EditText) findViewById(R.id.ED_landRegNo);
        e7 = (EditText) findViewById(R.id.ED_areaOfLand);
        e8 = (EditText) findViewById(R.id.ED_crop);
        e9 = (EditText) findViewById(R.id.ED_areaOfCultivation);
        e10 = (EditText) findViewById(R.id.ED_areaOfDamage);
        e11 = (EditText) findViewById(R.id.ED_amountRequested);
        e12 = (EditText) findViewById(R.id.ED_bankAccountNo);
        e13= (EditText) findViewById(R.id.ED_bank);
        e14 = (EditText) findViewById(R.id.ED_branch);

        e15 = (EditText) findViewById(R.id.ED_gramaNiladhariDiv);
        e16 = (EditText) findViewById(R.id.ED_farmerAddress);

        eOtherCause = (EditText) findViewById(R.id.ED_otherCause);
        tBrieflyExplain = (TextView) findViewById(R.id.TX_brieflyExplain);

        mandatoryInputs = new EditText[] {e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14};
        for (EditText input : mandatoryInputs) input.addTextChangedListener(mandatoryWatcher);

        tTopic = (TextView) findViewById(R.id.TX_claimTopic);
        e0.addTextChangedListener(topicWatcher);

        Spinner damageCauses = (Spinner) findViewById(R.id.SPINNER_damageCause);
        ArrayAdapter<CharSequence> causesAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_damageCauses, android.R.layout.simple_spinner_item);
        causesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        damageCauses.setAdapter(causesAdapter);
        damageCauses.setOnItemSelectedListener(this);

        Spinner damageLevels = (Spinner) findViewById(R.id.SPINNER_damageLevel);
        ArrayAdapter<CharSequence> levelsAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_damageLevel, android.R.layout.simple_spinner_item);
        levelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        damageLevels.setAdapter(levelsAdapter);

        eCultDate = (EditText) findViewById(R.id.ED_cultivationDate);
        DatePickerDialog.OnDateSetListener cultListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                CULT_CAL.set(Calendar.YEAR, year);
                CULT_CAL.set(Calendar.MONTH, month);
                CULT_CAL.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCultEd();
            }
        };

        eCultDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Claim1FActivity.this, cultListener,
                        CULT_CAL.get(Calendar.YEAR), CULT_CAL.get(Calendar.MONTH),
                        CULT_CAL.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        eDamDate = (EditText) findViewById(R.id.ED_damageDate);
        DatePickerDialog.OnDateSetListener damListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                DAM_CAL.set(Calendar.YEAR, year);
                DAM_CAL.set(Calendar.MONTH, month);
                DAM_CAL.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDamEd();
            }
        };

        eDamDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Claim1FActivity.this, damListener,
                        DAM_CAL.get(Calendar.YEAR), DAM_CAL.get(Calendar.MONTH),
                        DAM_CAL.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        claim = new Claim("0001");
        autoFillFormData();
    }

    private void updateCultEd() {
        eCultDate.setText(UtilityManager.getInstance().formatDate(CULT_CAL.getTime()));
    }

    private void updateDamEd() {
        eDamDate.setText(UtilityManager.getInstance().formatDate(DAM_CAL.getTime()));
    }

    private void autoFillFormData() {
        e0.setText(getResources().getString(R.string.txt_defaultClaimTopic, UtilityManager.getInstance().formatDate(new Date())));

        User user = AuthController.getInstance().getCurrentUser();
        e1.setText(user.getAgriServiceCenter());
        e2.setText(user.getRegNo());
        e3.setText(user.getName());
        e4.setText(user.getPhone());
        e5.setText(user.getNIC());
        e15.setText(user.getGramaNiladhariDiv());
        e16.setText(user.getAddress());
    }

    private void extractFormData() {
        claim.setTopic(e0.getText().toString());
        claim.setAgriServiceCenter(e1.getText().toString());
        claim.setFarmerRegNo(e2.getText().toString());
        claim.setFarmerName(e3.getText().toString());
        claim.setFarmerPhone(e4.getText().toString());
        claim.setFarmerNIC(e5.getText().toString());
        claim.setLandRegNum(e6.getText().toString());
        claim.setLandArea(Float.parseFloat(e7.getText().toString()));
        claim.setCrop(e8.getText().toString());
        claim.setCultivatedArea(Float.parseFloat(e9.getText().toString()));
        claim.setDamageDate(DAM_CAL.getTime());
        Spinner s1 = (Spinner) findViewById(R.id.SPINNER_damageCause);
        claim.setDamageCause(s1.getSelectedItemPosition());
        Spinner s2 = (Spinner) findViewById(R.id.SPINNER_damageLevel);
        claim.setDamageLevel(s2.getSelectedItemPosition());
        claim.setDamageArea(Float.parseFloat(e10.getText().toString()));
        claim.setCompensationAmount(Float.parseFloat(e11.getText().toString()));
        claim.setBankAccountNo(e12.getText().toString());
        claim.setBank(e13.getText().toString());
        claim.setBranch(e14.getText().toString());

        claim.setGramaNiladhariDiv(e15.getText().toString());
        claim.setFarmerAddress(e16.getText().toString());

        EditText e17 = (EditText) findViewById(R.id.ED_landName);
        claim.setLandName(e17.getText().toString());
        EditText e18 = (EditText) findViewById(R.id.ED_timeToHarvest);
        float tth = (!e18.getText().toString().equals("")) ? Float.parseFloat(e18.getText().toString()) : 0;
        claim.setTimeToHarvest(tth);
        claim.setCultivatedDate(CULT_CAL.getTime());
    }

    public void submitEvidence(View view) {
        Intent intent = new Intent(this, EvidenceFActivity.class);
        extractFormData();
        intent.putExtra(CLAIM_OBJECT, (Parcelable) claim);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 5) {
            tBrieflyExplain.setVisibility(View.VISIBLE);
            eOtherCause.setVisibility(View.VISIBLE);
        } else {
            tBrieflyExplain.setVisibility(View.GONE);
            eOtherCause.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}