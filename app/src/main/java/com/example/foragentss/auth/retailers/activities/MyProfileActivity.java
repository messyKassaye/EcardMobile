package com.example.foragentss.auth.retailers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.auth.retailers.fragments.MyAgentsFragment;
import com.example.foragentss.auth.retailers.fragments.MyCloudCardsFragment;
import com.example.foragentss.auth.retailers.fragments.NoInternetFragment;
import com.example.foragentss.auth.retailers.fragments.SendCardRequestFragment;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.view_model.MeViewModel;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.http.MainHttpAdapter;
import com.example.foragentss.http.interfaces.LoginService;
import com.example.foragentss.rooms.entity.Retailer;
import com.example.foragentss.rooms.view_model.RetailersViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyProfileActivity extends AppCompatActivity {

    private MeViewModel meViewModel;
    private ProgressBar progressBar;
    private RetailersViewModel retailersViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.profilePBR);

        if (!Constants.isOnline(getApplicationContext())){
            showFragment(new NoInternetFragment());
        }else {

            retailersViewModel = ViewModelProviders.of(this).get(RetailersViewModel.class);
            retailersViewModel.getRetailer().observe(this,retailers -> {
                String phone = retailers.get(0).getPhone();
                String password = retailers.get(0).getPassword();
                login(phone,password);
            });

        }

    }

    public void login(String phone,String password){
        Retrofit retrofit= MainHttpAdapter.getAuthApi();
        LoginService loginService = retrofit.create(LoginService.class);

        Call<LoginResponse> call = loginService.login(phone,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    setToken(response.body().getToken());
                    progressBar.setVisibility(View.GONE);
                    continueRequest();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void continueRequest(){
        int activity = Integer.parseInt(getIntent().getStringExtra("activity"));
        switch (activity){
            case 1:
                showFragment(new MyAgentsFragment());
                getSupportActionBar().setTitle("My agents");
                break;
            case 2:
                showFragment(new MyCloudCardsFragment());
                getSupportActionBar().setTitle("My cards on Ecard");
                break;
            case 3:
                showFragment(new SendCardRequestFragment());
                getSupportActionBar().setTitle("Send card request");
                break;
        }
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.my_profile_content_frame,fragment);
        transaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void setToken(String token){
        SharedPreferences preferences = getSharedPreferences(Constants.getTokenPrefence(),0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("token",token);
        editor.commit();
    }
}
