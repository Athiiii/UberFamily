package com.example.uberfamiliy.CloudMessage;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;

public class FirebaseCloudMessaging extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String token) {
        System.out.print("TOKEN: " + token);
    }
}