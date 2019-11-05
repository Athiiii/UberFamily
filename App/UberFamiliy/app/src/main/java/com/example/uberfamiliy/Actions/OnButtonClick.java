package com.example.uberfamiliy.Actions;

import android.view.View;

import com.example.uberfamiliy.communicate.ConnectToServer;

public class OnButtonClick implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        ConnectToServer connectToServer = null;
        connectToServer.pickMeUp(1);
    }
}

