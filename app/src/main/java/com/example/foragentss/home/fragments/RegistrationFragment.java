package com.example.foragentss.home.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.MainActivity;
import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.home.RegistrationActivity;
import com.example.foragentss.http.MainHttpAdapter;
import com.example.foragentss.http.interfaces.LoginService;
import com.example.foragentss.rooms.view_model.UserViewModel;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RegistrationFragment extends Fragment {
    private UserViewModel userViewModel;
    private LinearLayout mainLayout,confirmationLayout;
    private ProgressBar loading;
    public RegistrationFragment() {
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
        View view= inflater.inflate(R.layout.fragment_registration, container, false);

        mainLayout = view.findViewById(R.id.mainLayout);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        EditText fullNameEdit = view.findViewById(R.id.full_name);
        EditText phoneEdit = view.findViewById(R.id.inputPhone);
        EditText emailEdit = view.findViewById(R.id.email);
        EditText passwordEdit = view.findViewById(R.id.input_password);
        Button registerBtn = view.findViewById(R.id.registerBtn);
        TextView errorShower = view.findViewById(R.id.errorShower);
        RadioGroup radioGroup = view.findViewById(R.id.roleSelection);
        loading = view.findViewById(R.id.signUPLoading);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEdit.getText().toString();
                String phoneNumber = phoneEdit.getText().toString();
                String emailAddress = emailEdit.getText().toString();
                int selectedRoleId = radioGroup.getCheckedRadioButtonId();
                String password = passwordEdit.getText().toString();

                if (fullName.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your name");
                }else if(phoneNumber.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your phone number");
                }else if(emailAddress.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your email address");
                }else if(selectedRoleId<=0){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please select your registration type. are you our agent or retailer?");
                }else if(password.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your password");
                } else if(!fullName.contains(" ")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("You haven't enter your full name. E.g Mahder Girma");
                } else {
                    registerBtn.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Registering...");
                    errorShower.setTextColor(Color.GREEN);
                    int role_id =0;
                    if(selectedRoleId==R.id.agent){
                        role_id = 3;
                    }else if (selectedRoleId==R.id.retailer){
                        role_id = 4;
                    }
                    String first_name = fullName.substring(0,fullName.lastIndexOf(" ")+1);
                    String last_name = fullName.substring(fullName.lastIndexOf(" ")+1,fullName.length());
                    User user =new User(first_name,last_name,emailAddress,phoneNumber,password,role_id);

                    Retrofit retrofit= MainHttpAdapter.getAuthApi();
                    LoginService loginService = retrofit.create(LoginService.class);
                    loginService.signUP(user)
                            .enqueue(new Callback<LoginResponse>() {
                                @Override
                                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                    if (response.isSuccessful()){
                                        setToken(response.body().getToken());
                                        int loginRoleId = response.body().getRole().getId();
                                        RegistrationActivity registrationActivity =(RegistrationActivity) getActivity();
                                        registrationActivity.showAddressFragment(loginRoleId);
                                    }else {

                                        if (response.code()==409){
                                            errorShower.setTextColor(Color.RED);
                                            errorShower.setText("Some is register by your email or phone number. please change your email or phone number");
                                            registerBtn.setVisibility(View.VISIBLE);
                                            loading.setVisibility(View.GONE);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginResponse> call, Throwable t) {
                                    if(t instanceof SocketTimeoutException){
                                        loading.setVisibility(View.GONE);
                                        registerBtn.setVisibility(View.VISIBLE);
                                        errorShower.setText("It take too much time. Please try again");
                                    }
                                }
                            });

                }

            }
        });
        return view;
    }


    public void setToken(String token){
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.getTokenPrefence(),0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

}
