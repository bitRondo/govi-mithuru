package com.example.govimithuruapp.claimManagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.govimithuruapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EvidenceFActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;

    private ImageView imageView;
    private FloatingActionButton prevBtn, nextBtn;
    private Button removeBtn;
    private CardView descCard;
    private EditText descText;

    private ArrayList<String> filepathList = new ArrayList<>();
    private ArrayList<String> descList = new ArrayList<>();
    private int photoCounter = -1, maxPhotoCounter = -1;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evidence_f);

        imageView = (ImageView) findViewById(R.id.IMG_evidence);
        descCard = (CardView) findViewById(R.id.CARD_evidence_desc);
        descText = (EditText) findViewById(R.id.ED_evidence_desc);
        prevBtn = (FloatingActionButton) findViewById(R.id.BT_prevEvidence);
        nextBtn = (FloatingActionButton) findViewById(R.id.BT_nextEvidence);
        removeBtn = (Button) findViewById(R.id.BT_remove_evidence);

        setButtons();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void capturePhoto(View view) {
        if (photoCounter >= 0) descList.set(photoCounter, descText.getText().toString());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            filepathList.add(currentPhotoPath);
            descList.add("");
            maxPhotoCounter++;
            photoCounter = maxPhotoCounter;
            viewPhoto(currentPhotoPath);
            descText.setText("");
            setButtons();
        }
    }

    private void viewPhoto(String photoPath) {
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
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    public void goToPrevEvidence(View view) {
        if (photoCounter >= 0) descList.set(photoCounter, descText.getText().toString());
        photoCounter--;
        viewPhoto(filepathList.get(photoCounter));
        descText.setText(descList.get(photoCounter));
        setButtons();
    }

    public void goToNextEvidence(View view) {
        if (photoCounter >= 0) descList.set(photoCounter, descText.getText().toString());
        photoCounter++;
        viewPhoto(filepathList.get(photoCounter));
        descText.setText(descList.get(photoCounter));
        setButtons();
    }

    private void setButtons() {
        prevBtn.setVisibility(View.INVISIBLE);
        descCard.setVisibility(View.INVISIBLE);
        nextBtn.setVisibility(View.INVISIBLE);
        if (photoCounter == 0) {
            removeBtn.setVisibility(View.VISIBLE);
            descCard.setVisibility(View.VISIBLE);
        } else if (photoCounter > 0) {
            prevBtn.setVisibility(View.VISIBLE);
            descCard.setVisibility(View.VISIBLE);
            removeBtn.setVisibility(View.VISIBLE);
        }
        if (photoCounter < maxPhotoCounter) {
            nextBtn.setVisibility(View.VISIBLE);
        }
    }
}