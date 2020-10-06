package com.example.foragentss.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foragentss.MainActivity;
import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.http.MainHttpAdapter;
import com.example.foragentss.http.interfaces.LoginService;
import com.example.foragentss.rooms.view_model.UserViewModel;
import com.example.foragentss.security.CardEncryptor;
import com.example.foragentss.security.KeyCastor;

import java.net.SocketTimeoutException;

import javax.crypto.SecretKey;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {

    private EditText phone,passowrd;
    private TextView errorShower;
    private Button loginBtn;
    private UserViewModel userViewModel;
    private ProgressBar loginLoading;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        phone = findViewById(R.id.inputPhone);
        passowrd = findViewById(R.id.input_password);
        errorShower = findViewById(R.id.errorShower);
        loginBtn = findViewById(R.id.loginBtn);
        loginLoading = findViewById(R.id.loginLoading);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneText = phone.getText().toString();
                String passwordText = passowrd.getText().toString();
                if (phoneText.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your phone");
                }else if (passwordText.equals("")){
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setText("Please enter your password");
                }else {
                    loginBtn.setVisibility(View.GONE);
                    errorShower.setVisibility(View.VISIBLE);
                    errorShower.setTextColor(Color.GREEN);
                    errorShower.setText("Login on progress...");
                    loginLoading.setVisibility(View.VISIBLE);
                    login(phoneText,passwordText);
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(String email,String password){

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

                    if(response.body().getRole().getId()<3&&response.body().getRole().getId()>5){
                        loginLoading.setVisibility(View.GONE);
                        loginBtn.setVisibility(View.VISIBLE);
                        errorShower.setTextColor(Color.RED);
                        errorShower.setText("This application is created for car owners only. please use our web app for your purpose");
                    }else {
                        if(response.body().getRole().getId()==2){

                            setToken(response.body().getToken());
                        }else if (response.body().getRole().getId()==3){
                            setToken(response.body().getToken());
                            Intent intent = new Intent(getApplicationContext(), AgentsDashboard.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }

                }else {
                    loginLoading.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                    errorShower.setTextColor(Color.RED);
                    errorShower.setText("Something is not Good. This is not your mistake please get support from http://tabadvet.com/support");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if(t instanceof SocketTimeoutException){
                    loginLoading.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                    errorShower.setTextColor(Color.RED);
                    errorShower.setText("It takes much time. Please check your connection");
                }
            }
        });
    }

    public void setToken(String token){
        SharedPreferences preferences = getSharedPreferences(Constants.getTokenPrefence(),0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }
}
