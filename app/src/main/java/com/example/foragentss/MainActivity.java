package com.example.foragentss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.foragentss.home.LoginFragment;
import com.example.foragentss.home.RegistrationFragment;
import com.example.foragentss.rooms.view_model.UserViewModel;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("keys");
    }
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.index().observe(this,users -> {
            if (users.size()<=0){
                RegistrationFragment fragment = new RegistrationFragment();
                showFragment(fragment);
            }else {
                LoginFragment registrationFragment = new LoginFragment();
                showFragment(registrationFragment);
            }
        });
    }

    public void showFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public  native String getNativeKey();
}
