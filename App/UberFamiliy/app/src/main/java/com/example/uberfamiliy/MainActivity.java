package com.example.uberfamiliy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uberfamiliy.Actions.OnPickMeUpClick;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button pickUpBtn = findViewById(R.id.buttonPickUP);
        pickUpBtn.setOnClickListener(new OnPickMeUpClick());
        View addFriendIMG = findViewById(R.id.addFriend);
        addFriendIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddFriendWindow();
            }
        });
    }

    private void openAddFriendWindow() {
        Intent addFriendWindow = new Intent(this, LoginActivity.class);
        startActivity(addFriendWindow);
    }


}
