package com.example.foragentss.auth.retailers.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
import com.example.foragentss.auth.retailers.activities.DownloadActivity;
import com.example.foragentss.auth.retailers.activities.RetailerActivity;
import com.example.foragentss.auth.view_model.MeViewModel;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.http.MainHttpAdapter;
import com.example.foragentss.http.interfaces.LoginService;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class DownloadLoginFragment extends Fragment {

    private LinearLayout noConnectionLayout,downloadMainlayout;
    private EditText emailEditText,passwordEditText;
    private Button loginBtn;
    private ProgressBar loginLoading;
    private TextView errorShower;
    private MeViewModel viewModel;
    private String tag;
    public DownloadLoginFragment() {
        // Required empty public constructor
    }

    public DownloadLoginFragment(String tag){
        this.tag = tag;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_download_login, container, false);


        viewModel = ViewModelProviders.of(this).get(MeViewModel.class);


        emailEditText = view.findViewById(R.id.inputPhone);
        passwordEditText = view.findViewById(R.id.input_password);
        loginBtn = view.findViewById(R.id.loginBtn);
        errorShower = view.findViewById(R.id.errorShower);
        loginLoading = view.findViewById(R.id.loginLoading);

        noConnectionLayout = view.findViewById(R.id.wifiIsNotOn);
        downloadMainlayout = view.findViewById(R.id.downloadMainLayout);
        if (!Constants.isOnline(getActivity())){
            noConnectionLayout.setVisibility(View.VISIBLE);
            downloadMainlayout.setVisibility(View.GONE);
        }else {
            noConnectionLayout.setVisibility(View.GONE);
            downloadMainlayout.setVisibility(View.VISIBLE);
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (email.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your email");
                }else if (password.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your password");

                }else {
                    login(email,password);
                }
            }
        });
        return view;
    }


    public void login(String email,String password){
        loginLoading.setVisibility(View.VISIBLE);
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
                        setToken(response.body().getToken());

                        viewModel.me().observe(getActivity(), meResponse -> {
                            List<Device> devices = meResponse.getData().getRelations().getDevice();
                            String thisDeviceSerailNumber = Build.SERIAL;
                            proceed();

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


    public void proceed(){
        if (tag.equalsIgnoreCase("download_card_fragment")){
            DownloadActivity downloadActivity=(DownloadActivity)getActivity();
            downloadActivity.proceed();
        }
    }

    public void setToken(String token){
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.getTokenPrefence(),0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

}
