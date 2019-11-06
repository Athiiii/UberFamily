package com.example.uberfamiliy;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uberfamiliy.communicate.ConnectToAPI;
import com.example.uberfamiliy.communicate.ConnectToServer;
import com.example.uberfamiliy.model.User;

import java.util.List;

public class AddFriendActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        ConnectToServer connectToServer = ConnectToAPI.getInstance();
        fillUpList(connectToServer.getAllUsers());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
    }

    public void setHeader(ListView listView) {
        TextView textView = new TextView(this);
        textView.setText(R.string.usernames);
        textView.setTextSize(15);
        listView.addHeaderView(textView);
    }

    private void fillUpList(List<User> users) {
        ListView userListView = findViewById(R.id.userList);
        setHeader(userListView);

        ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, users);
        userListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        userListView.setAdapter(userArrayAdapter);
    }
}
