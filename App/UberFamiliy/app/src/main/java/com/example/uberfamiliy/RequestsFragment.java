package com.example.uberfamiliy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uberfamiliy.DBConnection.CallAPIResponse;
import com.example.uberfamiliy.DBConnection.ConnectToDB;
import com.example.uberfamiliy.model.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RequestsFragment extends Fragment {

    List<Request> requestList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_requests, container, false);
        ConnectToDB.getInstance().getOpenRequest(new CallAPIResponse() {
            @Override
            public void processFinish(String output) {
                if (output != null) {
                    try {
                        JSONArray responses = new JSONArray(output);
                        requestList = new ArrayList<>();

                        for (int i = 0; i < responses.length(); ++i) {
                            Request request = new Request();
                            JSONObject object = responses.getJSONObject(i);
                            request.setId(object.getLong("id"));
                            request.setRequester(object.getLong("requester"));

                            String driver = object.getString("driver");
                            if (!driver.equals("null"))
                                request.setDriver(object.getLong("driver"));

                            request.setOpen(object.getInt("open") == 1);
                            request.setAdress(object.getString("adress"));
                            requestList.add(request);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return root;
    }
}