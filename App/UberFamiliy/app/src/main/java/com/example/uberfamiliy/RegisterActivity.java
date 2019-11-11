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

import com.example.uberfamiliy.Actions.OnTextChangedAdapter;
import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.DBConnection.IConnectToDB;
import com.example.uberfamiliy.Service.ConvertJSON;
import com.example.uberfamiliy.Service.CreateUser;
import com.example.uberfamiliy.model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements CallAPIResponse {
    private static final int RESULT_TAKE_IMAGE = 7;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int RequestPermissionCode = 1;
    private Bitmap bitmap;
    private List<User> users;
    private User user;
    private EditText userName;
    private Button buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        users = new ArrayList<>();
        ConnectToDB.getInstance().getUsers(new CallAPIResponse() {
            @Override
            public void processFinish(String output) {
                List<User> userList = ConvertJSON.getInstance().toFriends(output);
                users = userList;
            }
        });

        userName = findViewById(R.id.username);
        userName.addTextChangedListener(new OnTextChangedAdapter() {
            @Override
            public void onTextChanged(CharSequence searchEntry, int start, int before, int count) {
                boolean taken = false;
                for (User user : users) {
                    if (userName.getText().toString().equals(user.getUsername())) {
                        taken = true;
                    }
                }
                if (taken) {
                    userName.setError("This username is already taken");
                    if (buttonRegister.isEnabled()) {
                        buttonRegister.setEnabled(false);
                        buttonRegister.setClickable(false);
                    }
                } else {
                    if (!buttonRegister.isEnabled()) {
                        buttonRegister.setEnabled(true);
                        buttonRegister.setClickable(true);
                    }

                }
            }
        });

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
        buttonRegister = findViewById(R.id.register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToRegister();
            }
        });

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
        if (checkInputFields(fullName, username, password)) {
            registerUser(fullName.getText().toString(), username.getText().toString(), password.getText().toString(), bitmap);
        }
    }

    private void registerUser(String fullName, String username, String password, Bitmap image) {
        IConnectToDB connectToDB = ConnectToDB.getInstance();
        connectToDB.registerUser(CreateUser.getInstance().createUser(fullName, username, password, image), this);
    }

    @Override
    public void processFinish(String output) {
        EditText username = findViewById(R.id.username);
        CheckBox rememberMe = findViewById(R.id.rememberMe);

        User user = ConvertJSON.getInstance().toUser(output);

        if ((user) != null) {
            User firstUser = getFirstUser();
            if (firstUser != null) {
                firstUser.deleteAll(User.class);
            }
            if (rememberMe.isChecked()) {
                user.setRemembered(true);
            } else {
                user.setRemembered(false);
            }
            user.save();
            openMainScreen();
        } else {
            username.setError("Something went wrong");
        }
    }

    private boolean checkInputFields(EditText fullName, EditText password, EditText username) {
        boolean inputFieldIsOK = true;
        if (username.getText().toString().trim().equals("")) {
            username.setError("Type in a username");
            inputFieldIsOK = false;
        }
        if (password.getText().toString().trim().equals("")) {
            password.setError("Type in a password");
            inputFieldIsOK = false;
        }
        if (fullName.getText().toString().trim().equals("")) {
            fullName.setError("Type in a fullName");
            inputFieldIsOK = false;
        }
        return inputFieldIsOK;
    }

    private User getFirstUser() {
        List<User> users = User.listAll(User.class);
        User user = null;
        if (users != null && users.size() > 0) {
            user = users.get(0);
        }

        return user;
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

    private void callCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 7);
    }


    private void pictureLoad(Intent data, ImageView imageView) {
        Uri selectedImage = data.getData();

        bitmap = null;

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        setImage(imageView);
    }

    private void pictureTaken(Intent data, ImageView imageView) {
        bitmap = (Bitmap) data.getExtras().get("data");

        setImage(imageView);
    }

    private void setImage(ImageView imageView) {
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    private void openSignInScreen() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    private void openMainScreen() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }


}
