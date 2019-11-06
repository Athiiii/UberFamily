package com.example.uberfamiliy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uberfamiliy.communicate.ConnectToAPI;
import com.example.uberfamiliy.communicate.ConnectToServer;
import com.example.uberfamiliy.model.User;
import com.orm.SugarContext;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private boolean rememberMe = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SugarContext.init(this);

        if (checkIfUserIsInDB()) {
            openMainScreen();
        }

        CheckBox rememberMeBtn = findViewById(R.id.rememberMe);
        rememberMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rememberMe = ((CheckBox) view).isChecked();
            }
        });

        Button signInBtn = findViewById(R.id.signIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToSignIn();
            }
        });
    }

    private boolean checkIfUserIsInDB() {
        List<User> users = User.listAll(User.class);
        return users.size() > 0;
    }

    private void tryToSignIn() {
        System.out.println("asd");
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        if (checkInputFields(username, password)) {
            User user = null;
            if ((user) != null) {
                if (rememberMe) {
                    user.save();
                }
                openMainScreen();
            } else {
                username.setError("Username or password is incorrect");
            }
        }
    }

    private boolean checkInputFields(EditText username, EditText password) {
        boolean inputFieldIsOK = true;
        if (username.getText().toString().trim().equals("")) {
            username.setError("Type in a username");
            inputFieldIsOK = false;
        }
        if (password.getText().toString().trim().equals("")) {
            password.setError("Type in a password");
            inputFieldIsOK = false;
        }
        return inputFieldIsOK;
    }


    private User verifyUser() {
        ConnectToServer connectToServer = ConnectToAPI.getInstance();
        connectToServer.verifyUser();
        return new User();
    }

    private void openMainScreen() {
        Intent addFriendWindow = new Intent(this, MainActivity.class);
        startActivity(addFriendWindow);
    }
}
