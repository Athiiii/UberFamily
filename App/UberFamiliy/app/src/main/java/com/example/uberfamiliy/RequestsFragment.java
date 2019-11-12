package com.example.uberfamiliy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.Service.ConvertJSON;
import com.example.uberfamiliy.Service.SQLLight;
import com.example.uberfamiliy.model.Request;
import com.example.uberfamiliy.model.User;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RequestsFragment extends Fragment implements CallAPIResponse {
    private ListView userListView;
    private List<Request> requestList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();
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
            userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final Request request = (Request) userListView.getItemAtPosition(position);
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Accept request")
                            .setMessage("Are you sure you want to accept the request?")
                            .setNegativeButton(android.R.string.no, null)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ConnectToDB.getInstance().acceptRequest(request.getId(), SQLLight.getInstance().getFirstUser(getActivity()).getId(),
                                            new CallAPIResponse() {
                                                @Override
                                                public void processFinish(String output) {
                                                    // TODO SHOW ADRESS
                                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                    ft.replace(R.id.nav_home, new HomeFragment());
                                                }
                                            });
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });
        }
    }

    private void fillUpListView(List<Request> userList) {
        ArrayAdapter<Request> userArrayAdapter = new android.widget.ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, GetUsersTroughRequest(userList));
        userListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        userListView.setAdapter(userArrayAdapter);
    }

    public List<Request> GetUsersTroughRequest(List<Request> requests) {

        for (int i = 0; i < requests.size(); ++i) {
            for (int j = 0; j < userList.size(); ++j) {
                if (userList.get(j).getUserId() == requests.get(i).getRequester()) {
                    requests.get(j).setRequesterObject(userList.get(j));
                }
            }
        }
        return requests;
    }


    @Override
    public void processFinish(String output) {
        List<Request> tempList = ConvertJSON.getInstance().toRequests(output);
        for (Request item : tempList) {
            if (item.getDriver() == null) {
                requestList.add(item);
            }
        }

        ConnectToDB.getInstance().getUsers(new CallAPIResponse() {
            @Override
            public void processFinish(String output) {
                userList = ConvertJSON.getInstance().toUsers(output);
                fillUpListView(requestList);
            }
        });
    }
}