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
import com.example.foragentss.auth.models.Device;
import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.auth.retailers.adapter.AgentsAdapter;
import com.example.foragentss.auth.view_model.MeViewModel;
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
    private AgentPartnerViewModel agentPartnerViewModel;
    private LinearLayout noAgentLayout,nearByLayout;
    private Button showMeNearByAgents;
    private Dialog loginDialog;
    private TextView errorShower;
    private MeViewModel meViewModel;

    private ArrayList<AgentAndPartner> agentAndPartnerArrayList = new ArrayList<>();
    private AgentsAdapter adapter;
    private RecyclerView recyclerView;

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

        adapter = new AgentsAdapter(getContext(),agentAndPartnerArrayList);
        recyclerView  =view.findViewById(R.id.agentsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        agentPartnerViewModel = ViewModelProviders.of(getActivity()).get(AgentPartnerViewModel.class);
        agentPartnerViewModel.index().observe(getActivity(),agentAndPartners -> {

            if (agentAndPartners.size()>0){
                agentAndPartnerArrayList.clear();
                agentAndPartnerArrayList.addAll(agentAndPartners);
                adapter.notifyDataSetChanged();
            }else {
                noAgentLayout.setVisibility(View.VISIBLE);
            }
        });

        showMeNearByAgents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)loginDialog.findViewById(R.id.loginInfo))
                        .setText("Login to see partners and agents");

                if (!Constants.isOnline(getContext())){
                    loginDialog.findViewById(R.id.wifiIsNotOn).setVisibility(View.VISIBLE);
                    loginDialog.findViewById(R.id.downloadMainLayout).setVisibility(View.GONE);
                }

                loginDialog.show();

            }
        });

        ((Button)loginDialog.findViewById(R.id.dialogLoginBtn))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = ((EditText)loginDialog.findViewById(R.id.dialogInputPhone))
                                .getText().toString();
                        String password =((EditText)loginDialog.findViewById(R.id.dialogInputpassword))
                                .getText().toString();
                         errorShower = ((TextView)loginDialog.findViewById(R.id.dialogErrorShower));
                        if (email.equals("")){
                            errorShower.setText("Please enter your email address");
                        }else if (password.equals("")){
                            errorShower.setText("Please enter your password");
                        }else {
                            login(email,password);
                        }
                    }
                });

        return view;
    }

    public void showNearLayout(){
        noAgentLayout.setVisibility(View.GONE);
        nearByLayout.setVisibility(View.VISIBLE);
    }

    public void login(String email,String password){
       ProgressBar loginLoading =((ProgressBar)loginDialog.findViewById(R.id.dialogLoginLoading));
       loginLoading.setVisibility(View.VISIBLE);
       Button loginBtn = ((Button)loginDialog.findViewById(R.id.dialogLoginBtn));
       loginBtn.setVisibility(View.GONE);

        Retrofit retrofit= MainHttpAdapter.getAuthApi();
        LoginService loginService = retrofit.create(LoginService.class);

        Call<LoginResponse> call = loginService.login(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.code()==403){
                    loginLoading.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                    errorShower.setTextColor(Color.RED);
                    errorShower.setText("Incorrect email or password is used.");
                }else if(response.code()==200){

                    if(response.body().getRole().getId()==4){

                        meViewModel.me().observe(getActivity(), meResponse -> {
                            List<Device> devices = meResponse.getData().getRelations().getDevice();
                            String thisDeviceSerailNumber = Build.SERIAL;
                            if (devices.size()>0&&devices.get(0).getSerial_number().equalsIgnoreCase(thisDeviceSerailNumber)){
                                setToken(response.body().getToken());
                                showNearLayout();
                                loginDialog.dismiss();
                            }else {

                            }
                        });

                    }

                }else {
                    loginLoading.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                    errorShower.setTextColor(Color.RED);
                    errorShower.setText("Something is not Good. This is not your mistake please get support from http://ecard.com/support");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    loginLoading.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                    errorShower.setTextColor(Color.RED);
                    errorShower.setText("It takes much time. Please check your connection");
                }else {
                    Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void setToken(String token){
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.getTokenPrefence(),0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }


}
