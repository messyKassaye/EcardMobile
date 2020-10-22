package com.example.foragentss.auth.retailers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.foragentss.R;
import com.example.foragentss.auth.retailers.fragments.RetailersHomeFragment;

public class RetailersDashboard extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailers_dashboard);
        getSupportActionBar().setTitle("ECard Retailers");

        showHome();

    }

    public void showHome(){
        Fragment fragment =new RetailersHomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.retailer_content_frame,fragment);
        transaction.commit();
    }


}
