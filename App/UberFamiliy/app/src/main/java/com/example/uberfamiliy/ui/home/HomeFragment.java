package com.example.uberfamiliy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.uberfamiliy.Actions.OnPickMeUpClick;
import com.example.uberfamiliy.AddFriendActivity;
import com.example.uberfamiliy.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        Button pickUpBtn = root.findViewById(R.id.buttonPickUP);
        pickUpBtn.setOnClickListener(new OnPickMeUpClick());

        View addFriendIMG = root.findViewById(R.id.addFriend);
        addFriendIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddFriendWindow();
            }
        });

        return root;
    }


    private void openAddFriendWindow() {
        Intent addFriendWindow = new Intent(this.getContext(), AddFriendActivity.class);
        startActivity(addFriendWindow);
    }
}