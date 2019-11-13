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
import com.example.uberfamiliy.Service.ConvertJSON;
import com.example.uberfamiliy.Service.SQLLight;
import com.example.uberfamiliy.model.User;

public class ProfileFragment extends Fragment {
    private View root = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        ConnectToDB.getInstance().getSingleUser(SQLLight.getInstance().getFirstUser(getActivity()).getUserId(), new CallAPIResponse() {
            @Override
            public void processFinish(String output) {
                User user = ConvertJSON.getInstance().toUser(output);

                EditText fullname = (EditText) root.findViewById(R.id.fullName);
                //fullname.setText(user.getFullName());

                TextView username = (TextView) root.findViewById(R.id.username);
                //username.setText(user.getUsername());

                Switch isDriver = (Switch) root.findViewById(R.id.isDriver);
                //isDriver.setChecked(user.isDriver());

                EditText password = (EditText) root.findViewById(R.id.password);
                //password.setText(user.getPassword());

            }
        });

        return root;
    }
}