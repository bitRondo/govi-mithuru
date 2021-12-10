package com.example.govimithuruapp.claimManagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.govimithuruapp.R;
import com.example.govimithuruapp.core.LocationController;
import com.example.govimithuruapp.core.UtilityManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.example.govimithuruapp.claimManagement.Claim1FActivity.VIEWING_CLAIM;
import static com.example.govimithuruapp.core.LocationController.PERMISSIONS_FINE_LOCATION;

public class EvidenceFActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;

    private ImageView imageView;
    private FloatingActionButton prevBtn, nextBtn, cameraBtn;
    private Button removeBtn, finalizeBtn;
    private CardView descCard;
    private EditText descText;
    private TextView maxAllowed;

    private Claim claim;
    private int evidenceCounter, maxEvidenceCounter;
    private String currentPhotoPath;
    private String currentEvidenceID;

    private boolean viewing;

    public static final int MAX_EVIDENCES_ALLOWED = 5;

    // Initiating the Evidence Submission UI and data
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence_f);

        claim = ClaimManager.getInstance().getCurrentClaim();
        maxEvidenceCounter = claim.getNumOfEvidences();

        imageView = (ImageView) findViewById(R.id.IMG_evidence);
        descCard = (CardView) findViewById(R.id.CARD_evidence_desc);
        descText = (EditText) findViewById(R.id.ED_evidence_desc);
        prevBtn = (FloatingActionButton) findViewById(R.id.BT_prevEvidence);
        nextBtn = (FloatingActionButton) findViewById(R.id.BT_nextEvidence);
        removeBtn = (Button) findViewById(R.id.BT_remove_evidence);
        cameraBtn = (FloatingActionButton) findViewById(R.id.BT_camera);
        finalizeBtn = (Button) findViewById(R.id.BTN_finalize);
        maxAllowed = (TextView) findViewById(R.id.TX_maxAllowed);

        if (claim.getState() > 0) {
            viewing = true;
            evidenceCounter = 0;
            adjustForViewingEvidences();
            viewEvidence(claim.getEvidence(evidenceCounter));
        } else {
            viewing = false;
            evidenceCounter = maxEvidenceCounter;
            if (maxEvidenceCounter >= 0) viewEvidence(claim.getEvidence(maxEvidenceCounter));
            initializeLocationController();
        }
    }

    // Create a temporary file to hold the capturing image
    private File createImageFile() throws IOException {
        maxEvidenceCounter++;
        // Create an image file name
        String imageFileName = EvidenceManager.getInstance().generateEvidenceID(claim.getClaimID(), maxEvidenceCounter);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        currentEvidenceID = imageFileName;

        return image;
    }

    public void capturePhoto(View view) {
        if (evidenceCounter >= 0)
            claim.getEvidence(evidenceCounter).setDescription(descText.getText().toString());

        // A new intent for Camera action
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            System.out.println("Error when creating filepath!");
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.govimithuruapp.fileprovider",
                    photoFile);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    // When returning from Camera action, show a new evidence
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Location loc = LocationController.getInstance().getLocation();
            Evidence evidence = new Evidence(currentEvidenceID, new Date(), loc.getLatitude(), loc.getLongitude(), currentPhotoPath);
            claim.addEvidence(evidence);
            evidenceCounter = maxEvidenceCounter;

            viewEvidence(evidence);
        }
    }

    // Place a photo on the imageView
    private void viewEvidence(Evidence evidence) {
        if (evidence != null) {
            String photoPath = evidence.getPhotoPath();

            // Get the dimensions of the View
            int targetW = imageView.getWidth();
            int targetH = imageView.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(photoPath, bmOptions);

            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
//        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = 1;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
            imageView.setImageBitmap(bitmap);
            descText.setText(evidence.getDescription());
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }
        setButtons();
    }

    public void removeEvidence(View view) {
        claim.removeEvidence(evidenceCounter);
        evidenceCounter--;
        maxEvidenceCounter = claim.getNumOfEvidences();
        if (evidenceCounter >= 0) viewEvidence(claim.getEvidence(evidenceCounter));
        else if (maxEvidenceCounter >= 0) {
            evidenceCounter = 0;
            viewEvidence(claim.getEvidence(evidenceCounter));
        } else viewEvidence(null);
    }

    public void goToPrevEvidence(View view) {
        if (evidenceCounter >= 0)
            claim.getEvidence(evidenceCounter).setDescription(descText.getText().toString());
        evidenceCounter--;
        viewEvidence(claim.getEvidence(evidenceCounter));
    }

    public void goToNextEvidence(View view) {
        if (evidenceCounter >= 0)
            claim.getEvidence(evidenceCounter).setDescription(descText.getText().toString());
        evidenceCounter++;
        viewEvidence(claim.getEvidence(evidenceCounter));
    }

    private void setButtons() {
        prevBtn.setVisibility(View.INVISIBLE);
        descCard.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);
        removeBtn.setVisibility(View.INVISIBLE);
        finalizeBtn.setVisibility(View.INVISIBLE);
        cameraBtn.setVisibility(View.VISIBLE);
        if (evidenceCounter == 0) {
            removeBtn.setVisibility(View.VISIBLE);
            finalizeBtn.setVisibility(View.VISIBLE);
            descCard.setVisibility(View.VISIBLE);
        } else if (evidenceCounter > 0) {
            prevBtn.setVisibility(View.VISIBLE);
            descCard.setVisibility(View.VISIBLE);
            removeBtn.setVisibility(View.VISIBLE);
            finalizeBtn.setVisibility(View.VISIBLE);
        }
        if (evidenceCounter < maxEvidenceCounter) {
            nextBtn.setVisibility(View.VISIBLE);
        }
        maxAllowed.setText(getResources().getString(R.string.txt_maxAllowed, MAX_EVIDENCES_ALLOWED,
                (MAX_EVIDENCES_ALLOWED-maxEvidenceCounter-1)));
        if (maxEvidenceCounter == -1) maxAllowed.setText("");
        if (maxEvidenceCounter == (MAX_EVIDENCES_ALLOWED-1)) {
            cameraBtn.setVisibility(View.INVISIBLE);
            maxAllowed.setText(getResources().getString(R.string.txt_noMoreEvidences));
        }
        if (viewing) {
            cameraBtn.setVisibility(View.INVISIBLE);
            maxAllowed.setVisibility(View.INVISIBLE);
            removeBtn.setVisibility(View.GONE);
            finalizeBtn.setVisibility(View.GONE);
        }
    }

    public void finalizeClaim(View view) {
        if (evidenceCounter >= 0)
            claim.getEvidence(evidenceCounter).setDescription(descText.getText().toString());
        // Go To Finalize Page
        Intent finalize = new Intent(this, Claim2FActivity.class);
        ClaimManager.getInstance().setCurrentClaim(claim);
        startActivity(finalize);

        // Go Back
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        super.finish();
    }

    // Going back to Claim details page
    @Override
    public void finish() {
        Intent data = new Intent();
        if (evidenceCounter >= 0)
            claim.getEvidence(evidenceCounter).setDescription(descText.getText().toString());
        ClaimManager.getInstance().setCurrentClaim(claim);
        if (viewing) data.putExtra(VIEWING_CLAIM, 1);
        setResult(RESULT_OK, data);
        super.finish();
    }

    private void initializeLocationController() {
        if (!LocationController.getInstance().checkHasPermissions()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // has permissions
                LocationController.getInstance().setHasPermissions(true);
            } else {
                // attempt to get permissions
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_FINE_LOCATION);
                }
            }
        }
        LocationController.getInstance().attachActivity(this);
    }

    private void adjustForViewingEvidences() {
        TextView title = (TextView) findViewById(R.id.TX_submitEvidence);
        title.setText(getResources().getString(R.string.txt_includedEvidences));

        descText.setKeyListener(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationController.getInstance().setHasPermissions(true);
                initializeLocationController();
            } else {
                Toast.makeText(this, "This App requires location permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}