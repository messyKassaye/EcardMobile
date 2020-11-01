package com.example.foragentss.auth.retailers.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.AgentPartner;
import com.example.foragentss.auth.models.Device;
import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.auth.response.AgentPartnerResponse;
import com.example.foragentss.auth.retailers.adapter.AgentsAdapter;
import com.example.foragentss.auth.view_model.MeViewModel;
import com.example.foragentss.auth.view_model.MyPartnerAgentViewModel;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.http.MainHttpAdapter;
import com.example.foragentss.http.interfaces.LoginService;
import com.example.foragentss.rooms.entity.AgentAndPartner;
import com.example.foragentss.rooms.view_model.AgentPartnerViewModel;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MyAgentsFragment extends Fragment {
    private LinearLayout noAgentLayout,nearByLayout,mainLayout;
    private Button showMeNearByAgents;
    private Dialog loginDialog;
    private TextView errorShower;
    private MeViewModel meViewModel;

    private ArrayList<AgentPartner> agentAndPartnerArrayList = new ArrayList<>();
    private AgentsAdapter adapter;
    private RecyclerView recyclerView;

    private MyPartnerAgentViewModel myPartnerAgentViewModel;
    public MyAgentsFragment() {
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
        View view= inflater.inflate(R.layout.fragment_my_agents, container, false);

        meViewModel = ViewModelProviders.of(getActivity()).get(MeViewModel.class);

        loginDialog = new Dialog(getContext());
        loginDialog.setContentView(R.layout.login_dialog);

        noAgentLayout = view.findViewById(R.id.noAgentLayout);
        showMeNearByAgents = view.findViewById(R.id.showMeMyNearByAgent);
        nearByLayout = view.findViewById(R.id.nearByLayout);
        mainLayout = view.findViewById(R.id.myProfileMainLayout);

        adapter = new AgentsAdapter(getContext(),agentAndPartnerArrayList);
        recyclerView  =view.findViewById(R.id.agentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        myPartnerAgentViewModel = ViewModelProviders.of(getActivity()).get(MyPartnerAgentViewModel.class);
        myPartnerAgentViewModel.index("agent_retailer").enqueue(new Callback<AgentPartnerResponse>() {
            @Override
            public void onResponse(Call<AgentPartnerResponse> call, Response<AgentPartnerResponse> response) {
                if (response.body().getData().size()>0){
                    mainLayout.setVisibility(View.VISIBLE);
                    nearByLayout.setVisibility(View.GONE);
                    noAgentLayout.setVisibility(View.GONE);
                    agentAndPartnerArrayList.clear();
                    agentAndPartnerArrayList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }else {
                    noAgentLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<AgentPartnerResponse> call, Throwable t) {

            }
        });

        showMeNearByAgents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNearLayout();
            }
        });

        return view;
    }

    public void showNearLayout(){
        noAgentLayout.setVisibility(View.GONE);
        nearByLayout.setVisibility(View.VISIBLE);
    }


}
