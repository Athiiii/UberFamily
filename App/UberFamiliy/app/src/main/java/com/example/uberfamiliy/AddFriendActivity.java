package com.example.uberfamiliy;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uberfamiliy.Actions.OnTextChangedAdapter;
import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.Service.ConvertJSON;
import com.example.uberfamiliy.model.User;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

public class AddFriendActivity extends AppCompatActivity implements CallAPIResponse {
    private ListView userListView;
    private List<User> users;
    private User selectedUser;

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
        this.addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //connectToServer.addFriend(getFirstUser().getUserId(), selectedUser.getUserId());

            }
        });

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setButtonEnabled();
                selectedUser = (User) (userListView).getAdapter().getItem(position);
            }
        });
    }

    private void init() {
        if (userListView == null) {
            userListView = findViewById(R.id.userList);
            setHeader();
        }

        if (users == null) {

            ConnectToDB.getInstance().getUsers(this);

        }
    }

    @Override
    public void processFinish(String output) {
        User user = getFirstUser();
        List<User> userList = ConvertJSON.getInstance().toFriends(output);
        this.users = userList;
        ConnectToDB.getInstance().getApprovedFriends(user.getUserId(), new CallAPIResponse() {
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

    private void setHeader() {
        TextView textView = new TextView(this);
        textView.setText(R.string.usernames);
        textView.setTextSize(15);
        this.userListView.addHeaderView(textView);
    }

    private void fillUpListView(List<User> userList) {
        ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, removeOwnUserAndFriends(userList));
        userListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        userListView.setAdapter(userArrayAdapter);
    }

    private List<User> removeOwnUserAndFriends(List<User> userList) {
        List<User> listWithoutOwnUser = new ArrayList<>();
        listWithoutOwnUser.addAll(userList);

        User user = getFirstUser();
        System.out.println(user.getUserId());
        for (User u : userList) {
            if (u != null) {
                if (u.getUserId().equals(user.getUserId())) {
                    listWithoutOwnUser.remove(u);
                }
            }
        }

        return listWithoutOwnUser;
    }

    private User getFirstUser() {
        List<User> users = User.listAll(User.class);
        User user = null;
        if (users != null && users.size() > 0) {
            user = users.get(0);
        }
        return user;
    }


}
