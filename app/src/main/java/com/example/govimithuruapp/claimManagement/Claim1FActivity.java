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

import static com.example.govimithuruapp.accountManagement.WelcomeActivity.VIEW_CLAIM_ID;

public class Claim1FActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String VIEWING_CLAIM = "com.example.govimithuruapp.VIEWING_CLAIM";
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
    private EditText e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14, e15, e16, e17, e18;
    private static EditText[] mandatoryInputs;
    private Spinner damageCauses, damageLevels;
    private ArrayAdapter<CharSequence> causesAdapter, levelsAdapter;

    private boolean causeIsOther = false;

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
            if (causeIsOther && eOtherCause.getText().toString().length() == 0)
                shouldEnable = false;
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

    private final TextWatcher causeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mandatoryWatcher.afterTextChanged(s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim1_f);

        submitEvidenceButton = (Button) findViewById(R.id.BTN_submitEvidence);

        tTopic = (TextView) findViewById(R.id.TX_claimTopic);

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

        mandatoryInputs = new EditText[] {e0, e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11, e12, e13, e14};

        e15 = (EditText) findViewById(R.id.ED_gramaNiladhariDiv);
        e16 = (EditText) findViewById(R.id.ED_farmerAddress);
        e17 = (EditText) findViewById(R.id.ED_landName);
        e18 = (EditText) findViewById(R.id.ED_timeToHarvest);

        eOtherCause = (EditText) findViewById(R.id.ED_otherCause);
        tBrieflyExplain = (TextView) findViewById(R.id.TX_brieflyExplain);

        damageCauses = (Spinner) findViewById(R.id.SPINNER_damageCause);
        damageLevels = (Spinner) findViewById(R.id.SPINNER_damageLevel);

        eCultDate = (EditText) findViewById(R.id.ED_cultivationDate);
        eDamDate = (EditText) findViewById(R.id.ED_damageDate);

        causesAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_damageCauses, android.R.layout.simple_spinner_item);

        levelsAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_damageLevel, android.R.layout.simple_spinner_item);

        Intent intent = getIntent();
        String possibleID = intent.getStringExtra(VIEW_CLAIM_ID);
        if (possibleID.length() > 0) {
            claim = AuthController.getInstance().getCurrentUser().getClaim(possibleID);
            adjustForViewingClaim();
        } else {
            claim = ClaimManager.getInstance().createNewClaim();
            adjustForNewClaim();
        }
        System.out.println(claim.getClaimID());
    }

    private void adjustForNewClaim() {
        submitEvidenceButton.setEnabled(false);

        for (EditText input : mandatoryInputs) input.addTextChangedListener(mandatoryWatcher);
        eOtherCause.addTextChangedListener(causeWatcher);
        e0.addTextChangedListener(topicWatcher);

        causesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        damageCauses.setAdapter(causesAdapter);
        damageCauses.setOnItemSelectedListener(this);

        levelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        damageLevels.setAdapter(levelsAdapter);

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

        autoFillFormData();
    }

    private void adjustForViewingClaim() {
        TextView topicSection = (TextView) findViewById(R.id.TX_topicSection);
        TextView topicExplain = (TextView) findViewById(R.id.TX_topicExplain);

        topicSection.setVisibility(View.GONE);
        topicExplain.setVisibility(View.GONE);
        e0.setVisibility(View.GONE);

        damageCauses.setVisibility(View.GONE);
        damageLevels.setVisibility(View.GONE);

        EditText causeView = (EditText) findViewById(R.id.ED_viewDamageCause);
        EditText levelView = (EditText) findViewById(R.id.ED_viewDamageLevel);

        causeView.setVisibility(View.VISIBLE);
        levelView.setVisibility(View.VISIBLE);

        tTopic.setText(claim.getTopic());

        e1.setText(claim.getAgriServiceCenter());
        e2.setText(claim.getFarmerRegNo());
        e3.setText(claim.getFarmerName());
        e4.setText(claim.getFarmerPhone());
        e5.setText(claim.getFarmerNIC());
        e6.setText(claim.getLandRegNum());
        e7.setText(String.valueOf(claim.getLandArea()));
        e8.setText(claim.getCrop());
        e9.setText(String.valueOf(claim.getCultivatedArea()));
        e10.setText(String.valueOf(claim.getDamageArea()));
        e11.setText(String.valueOf(claim.getCompensationAmount()));
        e12.setText(claim.getBankAccountNo());
        e13.setText(claim.getBank());
        e14.setText(claim.getBranch());

        e15.setText(claim.getGramaNiladhariDiv());
        e16.setText(claim.getFarmerAddress());
        e17.setText(claim.getLandName());
        String tth = (claim.getTimeToHarvest() > 0) ? String.valueOf(claim.getTimeToHarvest()) : "";
        e18.setText(tth);

        if (claim.getDamageCause() < 5) causeView.setText(causesAdapter.getItem(claim.getDamageCause()));
        else causeView.setText(claim.getOtherCause());

        levelView.setText(levelsAdapter.getItem(claim.getDamageLevel()));

        eCultDate.setText(UtilityManager.getInstance().formatDate(claim.getCultivatedDate()));
        eDamDate.setText(UtilityManager.getInstance().formatDate(claim.getDamageDate()));

        for (EditText input : mandatoryInputs) {
            input.setKeyListener(null);
        }
        e15.setKeyListener(null); e16.setKeyListener(null); e17.setKeyListener(null); e18.setKeyListener(null);
        causeView.setKeyListener(null); levelView.setKeyListener(null);

        submitEvidenceButton.setText(getResources().getString(R.string.txt_viewEvidences));
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
        claim.setOtherCause(eOtherCause.getText().toString());

        claim.setLandName(e17.getText().toString());
        float tth = (!e18.getText().toString().equals("")) ? Float.parseFloat(e18.getText().toString()) : 0;
        claim.setTimeToHarvest(tth);
        if (eCultDate.getText().toString().length() > 0) claim.setCultivatedDate(CULT_CAL.getTime());
    }

    public void submitEvidence(View view) {
        Intent intent = new Intent(this, EvidenceFActivity.class);
        extractFormData();
        ClaimManager.getInstance().setCurrentClaim(claim);
        startActivityForResult(intent, SUBMIT_EVIDENCE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUBMIT_EVIDENCE && resultCode == RESULT_OK) {
            claim = ClaimManager.getInstance().getCurrentClaim();
            if (data != null) {
                if (!data.hasExtra(VIEWING_CLAIM)) finish();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position == 5) {
            tBrieflyExplain.setVisibility(View.VISIBLE);
            eOtherCause.setVisibility(View.VISIBLE);

            eOtherCause.setText("");
            causeIsOther = true;
            mandatoryWatcher.afterTextChanged(null);
        } else {
            tBrieflyExplain.setVisibility(View.GONE);
            eOtherCause.setVisibility(View.GONE);
            causeIsOther = false;
            mandatoryWatcher.afterTextChanged(null);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}