package com.example.foragentss.auth.retailers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.fragments.ShowConnectionFragment;
import com.example.foragentss.auth.retailers.fragments.DownloadCardsFragment;
import com.example.foragentss.auth.retailers.fragments.DownloadLoginFragment;
import com.example.foragentss.auth.retailers.fragments.NoInternetFragment;
import com.example.foragentss.constants.Constants;

public class DownloadActivity extends AppCompatActivity {

    private String card_type_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Download cards");

        card_type_id = getIntent().getStringExtra("card_type_id");
        Toast.makeText(this,""+card_type_id,Toast.LENGTH_LONG).show();

        if (!Constants.isOnline(this)){
            showNoInternet();
        }else {
            showLogin();
        }


    }

    public void showNoInternet(){
        Fragment fragment = new NoInternetFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.download_content_frames,fragment);
        transaction.commit();
    }

    public void showLogin(){
        Fragment fragment = new DownloadLoginFragment("download_card_fragment");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.download_content_frames,fragment);
        transaction.commit();
    }
    public void proceed(){
        Fragment fragment = new DownloadCardsFragment(card_type_id);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.download_content_frames,fragment);
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
}
