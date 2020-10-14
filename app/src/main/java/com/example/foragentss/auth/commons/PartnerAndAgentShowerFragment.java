package com.example.foragentss.auth.commons;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.response.NearByResponse;
import com.example.foragentss.auth.view_model.NearByViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PartnerAndAgentShowerFragment extends Fragment {

    public PartnerAndAgentShowerFragment() {
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
        View view= inflater.inflate(R.layout.fragment_partner_and_agent_shower, container, false);

        return view;
    }

}
