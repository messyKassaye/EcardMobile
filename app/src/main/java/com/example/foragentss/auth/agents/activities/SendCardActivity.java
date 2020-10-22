package com.example.foragentss.auth.agents.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.fragments.SendCardPaymentTransactionFragment;
import com.example.foragentss.auth.commons.fragments.CardRequestCardSelectionFragment;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.User;

import java.util.ArrayList;

public class SendCardActivity extends AppCompatActivity {
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_card);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Send card");
        user = (User)getIntent().getSerializableExtra("user_id");

        showHome();
    }

    public void showHome(){
        Fragment fragment = new CardRequestCardSelectionFragment(user,"send_card_fragment");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.card_send_content_frame,fragment);
        transaction.commit();
    }


    public void proceed(ArrayList<CardRequest> cardRequests){
        Fragment fragment = new SendCardPaymentTransactionFragment(cardRequests,user);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.card_send_content_frame,fragment);
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
