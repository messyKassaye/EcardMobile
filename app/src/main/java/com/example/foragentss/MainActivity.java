package com.example.foragentss;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.foragentss.auth.retailers.fragments.DeviceConfigurationFragment;
import com.example.foragentss.auth.retailers.fragments.RetailersHomeFragment;
import com.example.foragentss.home.LoginActivity;
import com.example.foragentss.home.RegistrationActivity;
import com.example.foragentss.home.fragments.AddressFragment;
import com.example.foragentss.home.fragments.HomePageFragment;
import com.example.foragentss.rooms.view_model.DeviceRoomViewModel;
import com.example.foragentss.rooms.view_model.UserViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static {
        System.loadLibrary("keys");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showHome();

    }

    public void showHome(){
        Fragment fragment =new HomePageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_content_frame,fragment);
        transaction.commit();
    }

    public  native String getNativeKey();

    @Override
    public void onClick(View view) {

    }
}
