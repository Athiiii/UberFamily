package com.example.uberfamiliy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.uberfamiliy.Actions.OnButtonClick;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button pickUpBtn = (Button) findViewById(R.id.buttonPickUP);
        pickUpBtn.setOnClickListener(new OnButtonClick());
    }



}
