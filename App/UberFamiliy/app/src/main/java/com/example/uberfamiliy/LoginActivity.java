package com.example.uberfamiliy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uberfamiliy.communicate.ConnectToAPI;
import com.example.uberfamiliy.communicate.ConnectToServer;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button signInBtn = findViewById(R.id.signIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyUser()) {
                    openMainScreen();
                }
            }
        });


    }

    private boolean verifyUser() {
        ConnectToServer connectToServer = ConnectToAPI.getInstance();
        connectToServer.verifyUser();
        return true;
    }

    private void openMainScreen() {
        Intent addFriendWindow = new Intent(this, MainActivity.class);
        startActivity(addFriendWindow);
    }
}
