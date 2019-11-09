package com.example.uberfamiliy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.FileNotFoundException;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {
    private static final int RESULT_TAKE_IMAGE = 7;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int RequestPermissionCode = 1;
    private boolean accessOk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonLoadImage = findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        Button buttonTakePic = findViewById(R.id.takeAPic);
        buttonTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handleCameraPermissions()) {
                    callCamera();
                }
            }
        });

    }

    private boolean handleCameraPermissions() {
        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(RegisterActivity.this, "Permission Granted, Now Uber Familiy can access CAMERA.", Toast.LENGTH_LONG).show();
                    callCamera();

                } else {

                    Toast.makeText(RegisterActivity.this, "Permission Canceled, Uber Familiy cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    private void callCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = findViewById(R.id.imgView);

        if (requestCode == RESULT_TAKE_IMAGE && resultCode == RESULT_OK) {

            pictureTaken(data, imageView);
        }

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            pictureLoad(data, imageView);

        }


    }

    private void pictureLoad(Intent data, ImageView imageView) {
        Uri selectedImage = data.getData();

        Bitmap bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private void pictureTaken(Intent data, ImageView imageView) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        imageView.setImageBitmap(bitmap);
    }
}
