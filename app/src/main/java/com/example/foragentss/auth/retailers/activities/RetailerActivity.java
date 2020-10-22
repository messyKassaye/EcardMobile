package com.example.foragentss.auth.retailers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.fragments.CardRequestCardSelectionFragment;
import com.example.foragentss.auth.commons.fragments.NearBypartnersFragment;
import com.example.foragentss.auth.commons.fragments.ShowConnectionFragment;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.retailers.fragments.DownloadLoginFragment;
import com.example.foragentss.constants.Constants;

import java.util.ArrayList;

public class RetailerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Ecard Retailers");
        showHome();
    }


    public void showHome(){
        Fragment fragment = new DownloadLoginFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.download_content_frame, fragment);
        ft.commit();
    }


    public void showCardRequest(){
        /*Fragment fragment = new CardRequestCardSelectionFragment("retailer");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.download_content_frame, fragment);
        ft.commit();*/
    }

    public void showConnection(ArrayList<CardRequest> cardRequests){
        Fragment fragment = new ShowConnectionFragment(cardRequests);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.download_content_frame,fragment);
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
