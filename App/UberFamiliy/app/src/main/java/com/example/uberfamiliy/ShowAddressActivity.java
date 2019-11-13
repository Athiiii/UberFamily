package com.example.uberfamiliy;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_address);
        Intent main = new Intent();
        Bundle extras = main.getExtras();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        String longitude = extras.getString("longitude");
        String latitude = extras.getString("latitude");
        TextView text = findViewById(R.id.coordinates);
        text.setText("longitude: " + longitude + "\n" + "latitude: " + latitude);

    }
}
