package com.example.foragentss.auth.agents.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.fragments.CardRequestCardSelectionFragment;
import com.example.foragentss.auth.commons.fragments.PaymentTransactionFragment;
import com.example.foragentss.auth.commons.fragments.ShowConnectionFragment;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.ConnectionsData;
import com.example.foragentss.auth.models.User;

import java.util.ArrayList;

public class FloatingButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_button);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Send new card request");

        Fragment fragment = new CardRequestCardSelectionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.card_request_content_frame,fragment);
        transaction.commit();
    }

    public void showConnection(ArrayList<CardRequest> cardRequests){
        Fragment fragment = new ShowConnectionFragment(cardRequests);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.card_request_content_frame,fragment);
        transaction.commit();
    }

    public void showPaymentTransaction(User selectedUser,ArrayList<CardRequest> cardRequests,ArrayList<Integer> sendedCardRequest){
        Fragment fragment = new PaymentTransactionFragment(selectedUser,cardRequests,sendedCardRequest);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.card_request_content_frame,fragment);
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
