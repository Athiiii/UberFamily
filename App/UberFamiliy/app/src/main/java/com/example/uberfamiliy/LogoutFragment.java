package com.example.uberfamiliy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uberfamiliy.model.User;

public class LogoutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        final TextView textView = root.findViewById(R.id.text_send);
        openLoingScreen();
        User.deleteAll(User.class);
        return root;
    }

    private void openLoingScreen() {
        Intent main = new Intent(this.getContext(), LoginActivity.class);
        startActivity(main);
    }
}