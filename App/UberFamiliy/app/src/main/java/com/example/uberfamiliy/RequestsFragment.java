package com.example.uberfamiliy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.Service.ConvertJSON;
import com.example.uberfamiliy.model.Request;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RequestsFragment extends Fragment implements CallAPIResponse {
    private ListView userListView;
    private List<Request> requestList = new ArrayList<>();
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_requests, container, false);
        init();
        ConnectToDB.getInstance().getOpenRequest(this);
        return root;
    }

    private void init() {
        if (userListView == null) {
            userListView = root.findViewById(R.id.requestList);
        }
    }

    private void fillUpListView(List<Request> userList) {
        ArrayAdapter<Request> userArrayAdapter = new android.widget.ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_single_choice, userList);
        userListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        userListView.setAdapter(userArrayAdapter);
    }

    @Override
    public void processFinish(String output) {
        requestList = ConvertJSON.getInstance().toRequests(output);
        fillUpListView(requestList);
    }
}