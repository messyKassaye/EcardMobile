package com.example.foragentss.auth.agents.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.adapter.RolesAdapter;
import com.example.foragentss.auth.models.RoleUser;
import com.example.foragentss.auth.response.RoleResponse;
import com.example.foragentss.auth.view_model.RoleViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailersActivity extends AppCompatActivity {

    private RoleViewModel roleViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textView;
    private ArrayList<RoleUser> arrayList = new ArrayList<>();
    private RolesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("All retailers");

        progressBar = findViewById(R.id.retailerPBR);
        textView = findViewById(R.id.noRetailerFound);

        adapter = new RolesAdapter(this,arrayList);
        recyclerView  = findViewById(R.id.retailerRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        roleViewModel = ViewModelProviders.of(this).get(RoleViewModel.class);
        roleViewModel.show(4)
                .enqueue(new Callback<RoleResponse>() {
                    @Override
                    public void onResponse(Call<RoleResponse> call, Response<RoleResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.isSuccessful()){
                            arrayList.addAll(response.body().getData().getUser());
                            adapter.notifyDataSetChanged();
                        }

                        if (arrayList.size()>0){
                            recyclerView.setVisibility(View.VISIBLE);
                        }else {
                            textView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<RoleResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

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
}
