package com.example.uberfamiliy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.DBConnection.IConnectToDB;
import com.example.uberfamiliy.Service.Connectivity;
import com.example.uberfamiliy.Service.ConvertJSON;
import com.example.uberfamiliy.Service.SQLLight;
import com.example.uberfamiliy.model.User;
import com.orm.SugarContext;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements CallAPIResponse {
    private User user;
    private SQLLight sqlLight;

    @Override
    protected void onStart() {
        init();
        if (checkIfUserUsedRememberMe()) {
            user = getFirstUser();
            //UPDATES the ip address and the port from the device
            ConnectToDB.getInstance().updateUserConnect(user.getUserId(), "8080", Connectivity.getInstance().getIPAddress(), null);
            openMainScreen();
        } else {
            sqlLight.deleteAllFields(this);
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        init();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SugarContext.init(this);
        View register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterScreen();
            }
        });

        Button signInBtn = findViewById(R.id.signIn);
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryToSignIn();
            }
        });
    }

    private void init() {
        if (sqlLight == null) {
            sqlLight = SQLLight.getInstance();
        }
    }

    private boolean checkIfUserUsedRememberMe() {
        user = getFirstUser();
        return user != null && user.isRemembered();
    }

    private User getFirstUser() {
        List<User> users = User.listAll(User.class);
        User user = null;
        if (users != null && users.size() > 0) {
            user = users.get(0);
        }

        return user;
    }

    private void tryToSignIn() {
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        if (checkInputFields(username, password)) {
            verifyUser(username.getText().toString(), password.getText().toString());

        }
    }

    private boolean checkInputFields(EditText username, EditText password) {
        boolean inputFieldIsOK = true;
        if (username.getText().toString().trim().equals("")) {
            username.setError("Type in a username");
            inputFieldIsOK = false;
        }
        if (password.getText().toString().trim().equals("")) {
            password.setError("Type in a password");
            inputFieldIsOK = false;
        }
        return inputFieldIsOK;
    }


    private void verifyUser(String username, String password) {
        IConnectToDB connectToDB = ConnectToDB.getInstance();
        connectToDB.verifyUser(username, password, this);
    }

    @Override
    public void processFinish(String output) {
        EditText username = findViewById(R.id.username);
        CheckBox rememberMe = findViewById(R.id.rememberMe);

        User user = ConvertJSON.getInstance().toUser(output);

        if ((user) != null) {
            //UPDATES the ip address and the port from the device
            ConnectToDB.getInstance().updateUserConnect(user.getUserId(), "8080", Connectivity.getInstance().getIPAddress(), null);
            if (rememberMe.isChecked()) {
                user.setRemembered(true);

            } else {
                user.setRemembered(false);
            }
            sqlLight.saveUser(this, user);
            openMainScreen();
        } else {
            username.setError("Username or password is incorrect");
        }
    }

    private void openMainScreen() {
        Intent main = new Intent(this, NavigationActivity.class);
        startActivity(main);
    }

    private void openRegisterScreen() {
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
    }


}
