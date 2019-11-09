package com.example.uberfamiliy.Actions;

import android.view.View;

import com.example.uberfamiliy.communicate.ConnectToAPI;
import com.example.uberfamiliy.communicate.ConnectToServer;

public class OnPickMeUpClick implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        ConnectToServer connectToServer = ConnectToAPI.getInstance();
        connectToServer.pickMeUp(1);
    }
}

