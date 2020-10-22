package com.example.foragentss.auth.retailers.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foragentss.R;
import com.example.foragentss.auth.retailers.adapter.TabAdapter;
import com.example.foragentss.rooms.entity.UserRoom;
import com.example.foragentss.rooms.view_model.UserViewModel;
import com.google.android.material.tabs.TabLayout;


public class MyProfileFragment extends Fragment {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private TextView fullName;
    private Button avatar;
    private UserViewModel userViewModel;
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

        viewPager = (ViewPager)view.findViewById(R.id.profileViewPager);
        tabLayout = (TabLayout)view.findViewById(R.id.profileTabLayout);
        adapter = new TabAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new MyAgentsFragment(), "My agents");
        adapter.addFragment(new MyCardRequestFragment(),"Cards on server");
        adapter.addFragment(new MyCardRequestFragment(),"Card requests");
        adapter.addFragment(new RetailersNotificationFragment(),"Notifications");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


}
