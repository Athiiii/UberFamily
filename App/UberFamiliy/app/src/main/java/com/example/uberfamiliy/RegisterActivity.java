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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.uberfamiliy.communicate.ConnectToAPI;
import com.example.uberfamiliy.communicate.ConnectToServer;
import com.example.uberfamiliy.model.User;

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

        View signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openSignInScreen();
            }
        });

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

        Button buttonRegister = findViewById(R.id.register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToRegister();
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

    private void tryToRegister() {
        EditText fullName = findViewById(R.id.fullName);
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        CheckBox rememberMe = findViewById(R.id.rememberMe);
        if (checkInputFields(username, password, fullName)) {
            User user = verifyUser();
            if ((user) != null) {
                if (rememberMe.isChecked()) {
                    user.setRemembered(true);
                } else {
                    user.setRemembered(false);
                }
                user.save();
                openMainScreen();
            } else {
                username.setError("Username or password is incorrect");
            }
        }
    }

    private User verifyUser() {
        ConnectToServer connectToServer = ConnectToAPI.getInstance();
        User user = connectToServer.verifyUser();
        return user;
    }


    private boolean checkInputFields(EditText username, EditText password, EditText fullName) {
        boolean inputFieldIsOK = true;
        if (username.getText().toString().trim().equals("")) {
            username.setError("Type in a username");
            inputFieldIsOK = false;
        }
        if (password.getText().toString().trim().equals("")) {
            password.setError("Type in a password");
            inputFieldIsOK = false;
        }
        if (fullName.toString().trim().equals("")) {
            fullName.setError("Type in a fullName");
            inputFieldIsOK = false;
        }
        return inputFieldIsOK;
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

    private void openSignInScreen() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    private void openMainScreen() {
        Intent main = new Intent(this, NavigationActivity.class);
        startActivity(main);
    }
}
