package com.example.foragentss.auth.retailers.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.retailers.activities.MyProfileActivity;
import com.example.foragentss.auth.retailers.adapter.TabAdapter;
import com.example.foragentss.rooms.entity.UserRoom;
import com.example.foragentss.rooms.view_model.UserViewModel;
import com.google.android.material.tabs.TabLayout;


public class MyProfileFragment extends Fragment implements View.OnClickListener {
    private TextView fullName;
    private Button avatar;
    private UserViewModel userViewModel;
    private Button showMyAgents,showMeMyCards,sendCardRequest,setting;
    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_profile, container, false);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        fullName = view.findViewById(R.id.profileFullName);
        avatar = view.findViewById(R.id.profileAvatart);

        userViewModel.index().observe(getActivity(),userRooms -> {
            if (userRooms.size()>0){
                UserRoom userRoom = userRooms.get(0);
                fullName.setText(userRoom.getFull_name());
                avatar.setText(String.valueOf(userRoom.getFull_name().charAt(0)));
            }
        });

        showMyAgents = view.findViewById(R.id.showMeMyAgents);
        showMyAgents.setOnClickListener(this::onClick);

        showMeMyCards = view.findViewById(R.id.showMeMyCardOnEcard);
        showMeMyCards.setOnClickListener(this::onClick);

        sendCardRequest = view.findViewById(R.id.sendCardRequest);
        sendCardRequest.setOnClickListener(this::onClick);

        setting = view.findViewById(R.id.settings);
        setting.setOnClickListener(this::onClick);


        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.showMeMyAgents:
                    showActivity(1);
                break;
            case R.id.showMeMyCardOnEcard:
                showActivity(2);
                break;
            case R.id.sendCardRequest:
                showActivity(3);
                break;
            case R.id.settings:
                showActivity(4);
                break;
        }
    }

    public void showActivity(int activity){
        Intent intent =new Intent(getActivity(), MyProfileActivity.class);
        intent.putExtra("activity",String.valueOf(activity));
        startActivity(intent);
    }
}
