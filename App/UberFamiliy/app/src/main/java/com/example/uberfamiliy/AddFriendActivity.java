package com.example.uberfamiliy;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uberfamiliy.Adapter.OnTextChangedAdapter;
import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.Service.ConvertJSON;
import com.example.uberfamiliy.Service.SQLLight;
import com.example.uberfamiliy.model.User;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

public class AddFriendActivity extends AppCompatActivity implements CallAPIResponse {
    private ListView userListView;
    private List<User> users;
    private User firstUser;
    private User selectedUser;
    private SQLLight sqlLight;

    Button addFriendBtn;

    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        init();
        SugarContext.init(this);

        EditText searchText = findViewById(R.id.searchText);
        searchText.addTextChangedListener(new OnTextChangedAdapter() {
            @Override
            public void onTextChanged(CharSequence searchEntry, int start, int before, int count) {
                if (searchEntry.length() > 0) {
                    filterList(searchEntry.toString());
                } else {
                    fillUpListView(users);
                }
            }
        });

        this.addFriendBtn = findViewById(R.id.addFriend);
        setButtonDisabled();

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setButtonEnabled();
                selectedUser = (User) (userListView).getAdapter().getItem(position);
            }
        });

        this.addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectToDB.getInstance().sendFriendRequest(firstUser.getUserId(), selectedUser.getUserId(), new CallAPIResponse() {
                    @Override
                    public void processFinish(String output) {
                        ConnectToDB.getInstance().getAllFriends(firstUser.getUserId(), new CallAPIResponse() {
                            @Override
                            public void processFinish(String output) {
                                List<User> friends = ConvertJSON.getInstance().toFriends(output);
                                users.removeAll(friends);
                                fillUpListView(users);
                            }
                        });
                        Context context = getApplicationContext();

                        CharSequence text = "You have send a friend request to " + selectedUser.getUsername();
                        setButtonDisabled();
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();


                    }

                });

            }
        });
    }

    private void init() {
        if (sqlLight == null) {
            sqlLight = SQLLight.getInstance();
        }
        if (userListView == null) {
            userListView = findViewById(R.id.userList);
        }
        if (firstUser == null) {
            firstUser = sqlLight.getFirstUser(this);
        }
        if (users == null) {

            ConnectToDB.getInstance().getUsers(this);
        }

    }

    @Override
    public void processFinish(String output) {
        List<User> userList = ConvertJSON.getInstance().toFriends(output);
        this.users = userList;
        ConnectToDB.getInstance().getAllFriends(firstUser.getUserId(), new CallAPIResponse() {
            @Override
            public void processFinish(String output) {
                List<User> friends = ConvertJSON.getInstance().toFriends(output);
                users.removeAll(friends);
                fillUpListView(users);
            }
        });

    }

    private void setButtonEnabled() {
        addFriendBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.Button)));
        addFriendBtn.setClickable(true);
        addFriendBtn.setEnabled(true);
    }

    private void setButtonDisabled() {
        addFriendBtn.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.ButtonGreyedOut)));
        addFriendBtn.setEnabled(false);
        addFriendBtn.setClickable(false);
    }

    private void filterList(String searchEntry) {
        List<User> filteredUsers = new ArrayList<>();

        for (User user : this.users) {
            if (user != null
                    && user.getUsername() != null
                    && !user.getUsername().trim().equals("")) {
                if (user.getUsername().toLowerCase().matches(".*" + searchEntry.toLowerCase() + ".*")) {
                    filteredUsers.add(user);
                }
            }
        }
        fillUpListView(filteredUsers);
    }


    private void fillUpListView(List<User> userList) {
        ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, removeOwnUserAndFriends(userList));
        userListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        userListView.setAdapter(userArrayAdapter);
    }

    private List<User> removeOwnUserAndFriends(List<User> userList) {
        List<User> listWithoutOwnUser = new ArrayList<>();
        listWithoutOwnUser.addAll(userList);


        for (User u : userList) {
            if (u != null) {
                if (u.getUserId().equals(firstUser.getUserId())) {
                    listWithoutOwnUser.remove(u);
                }
            }
        }

        return listWithoutOwnUser;
    }


}
