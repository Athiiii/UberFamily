package com.example.uberfamiliy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.uberfamiliy.Adapter.OnTextChangedAdapter;
import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.Service.SQLLight;
import com.example.uberfamiliy.model.User;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {
    private List<User> friends;
    private ListView friendsView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_friends, container, false);
        ConnectToDB.getInstance().getApprovedFriends(SQLLight.getInstance().getId(this.getContext()), new CallAPIResponse() {
            @Override
            public void processFinish(String output) {

            }
        });
        EditText searchText = root.findViewById(R.id.searchText);
        searchText.addTextChangedListener(new OnTextChangedAdapter() {
            @Override
            public void onTextChanged(CharSequence searchEntry, int start, int before, int count) {
                if (searchEntry.length() > 0) {
                    filterList(searchEntry.toString());
                } else {
                    fillUpListView(friends);
                }
            }
        });
        return root;
    }

    private void filterList(String searchEntry) {
        List<User> filteredUsers = new ArrayList<>();

        for (User user : this.friends) {
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
        ArrayAdapter<User> userArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_single_choice, userList);
        friendsView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        friendsView.setAdapter(userArrayAdapter);
    }


}