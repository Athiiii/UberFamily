package com.example.uberfamiliy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.Service.SQLLight;

public class ProfileFragment extends Fragment {
    private View root = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        ConnectToDB.getInstance().getSingleUser(SQLLight.getInstance().getFirstUser(getActivity()).getUserId(), new CallAPIResponse() {
            @Override
            public void processFinish(String output) {
                EditText fullname = root.findViewById(R.id.fullName);
                TextView username = root.findViewById(R.id.fullName);
                Switch isDriver = root.findViewById(R.id.fullName);
                EditText password = root.findViewById(R.id.fullName);
            }
        });

        return root;
    }
}