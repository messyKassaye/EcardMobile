package com.example.foragentss.auth.agents.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.response.NearByResponse;
import com.example.foragentss.auth.view_model.NearByViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    private NearByViewModel nearByViewModel;
    private ProgressBar progressBar;
    private LinearLayout mainLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mainLayout = view.findViewById(R.id.homeMainLayout);
        progressBar = view.findViewById(R.id.homeLoading);
        nearByViewModel = ViewModelProviders.of(getActivity()).get(NearByViewModel.class);
        nearByViewModel.index().enqueue(new Callback<NearByResponse>() {
            @Override
            public void onResponse(Call<NearByResponse> call, Response<NearByResponse> response) {
                AgentsDashboard agentsDashboard = (AgentsDashboard)getActivity();
                if (response.body().isStatus()){
                    mainLayout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }else {
                    progressBar.setVisibility(View.GONE);
                    agentsDashboard.showAddress("You haven't set your address. Please set your address now");
                }
            }

            @Override
            public void onFailure(Call<NearByResponse> call, Throwable t) {
                System.out.println("NEARBYL "+t.getMessage());
            }
        });
        return view;
    }
}