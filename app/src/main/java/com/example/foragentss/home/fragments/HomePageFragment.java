package com.example.foragentss.home.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foragentss.R;
import com.example.foragentss.home.LoginActivity;
import com.example.foragentss.home.RegistrationActivity;
import com.example.foragentss.rooms.view_model.UserViewModel;


public class HomePageFragment extends Fragment implements View.OnClickListener{

    private UserViewModel userViewModel;
    private Button startSellingCard,login,signUpBtn;
    public HomePageFragment() {
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
        View  view = inflater.inflate(R.layout.fragment_home_page, container, false);

        startSellingCard = view.findViewById(R.id.start_selling_card);
        startSellingCard.setOnClickListener(this::onClick);

        login = view.findViewById(R.id.loginBtn);
        login.setOnClickListener(this::onClick);

        signUpBtn = view.findViewById(R.id.signUP);
        signUpBtn.setOnClickListener(this::onClick);
        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.start_selling_card||view.getId()==R.id.loginBtn){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getActivity(), RegistrationActivity.class);
            startActivity(intent);
        }
    }
}
