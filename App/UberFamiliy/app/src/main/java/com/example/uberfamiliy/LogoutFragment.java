package com.example.uberfamiliy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uberfamiliy.Service.SQLLight;

public class LogoutFragment extends Fragment {
    private SQLLight sqlLight;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        init();
        sqlLight.deleteAllFields();
        openLoginScreen();
        return root;
    }

    private void init() {
        if (sqlLight == null) {
            sqlLight = SQLLight.getInstance();
        }
    }

    private void openLoginScreen() {
        Intent main = new Intent(this.getContext(), LoginActivity.class);
        startActivity(main);
    }
}