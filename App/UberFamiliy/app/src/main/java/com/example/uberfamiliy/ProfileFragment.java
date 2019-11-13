package com.example.uberfamiliy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private View root = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);


        EditText fullname = root.findViewById(R.id.fullnameInput);
        ConnectToDB.getInstance().getSingleUser(SQLLight.getInstance().getFirstUser(getActivity()).getUserId(), new CallAPIResponse() {
            @Override
            public void processFinish(String output) {
                User user = ConvertJSON.getInstance().toUser(output);

                EditText fullname = root.findViewById(R.id.fullnameInput);
                fullname.setText(user.getFullName());

                TextView username = (TextView) root.findViewById(R.id.username);
                username.setText(user.getUsername());

                Switch isDriver = (Switch) root.findViewById(R.id.isDriver);
                isDriver.setChecked(user.isDriver());

                EditText password = root.findViewById(R.id.passwordInput);
                password.setText(user.getPassword());

            }
        });

        Button update = root.findViewById(R.id.updateUser);
        update.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        EditText fullname = root.findViewById(R.id.fullnameInput);
        TextView username = (TextView) root.findViewById(R.id.username);
        Switch isDriver = (Switch) root.findViewById(R.id.isDriver);
        EditText password = root.findViewById(R.id.passwordInput);
        User u = new User();
        u.setFullName(fullname.getText().toString());
        u.setUsername(username.getText().toString());
        u.setDriver(isDriver.isChecked());
        u.setPassword(password.getText().toString());
        u.setUserId(SQLLight.getInstance().getFirstUser(getActivity()).getUserId());

        ConnectToDB.getInstance().updateUser(u, null);
    }
}