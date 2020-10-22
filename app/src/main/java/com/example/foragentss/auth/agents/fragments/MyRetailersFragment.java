package com.example.foragentss.auth.agents.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.activities.RetailersActivity;
import com.example.foragentss.auth.agents.adapter.PartnersAdapter;
import com.example.foragentss.auth.helpers.GridSpacingItemDecoration;
import com.example.foragentss.auth.models.ConnectionsData;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.ConnectionsResponse;
import com.example.foragentss.auth.view_model.MyRetailerViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyRetailersFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayout noRetailerLayout;
    private ArrayList<User> userArrayList = new ArrayList<>();
    private PartnersAdapter adapter;
    private MyRetailerViewModel myRetailerViewModel;
    private Button showMeRetailers;
    public MyRetailersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_retailers, container, false);

        progressBar = view.findViewById(R.id.retailersLoader);
        noRetailerLayout = view.findViewById(R.id.noRetailerLayout);
        showMeRetailers = view.findViewById(R.id.showMeRetailers);

        adapter = new PartnersAdapter(getContext(),"Retailer",userArrayList);
        recyclerView = view.findViewById(R.id.retailersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10,getContext()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        myRetailerViewModel = ViewModelProviders.of(getActivity()).get(MyRetailerViewModel.class);
        myRetailerViewModel.show(1)
                .enqueue(new Callback<ConnectionsResponse>() {
                    @Override
                    public void onResponse(Call<ConnectionsResponse> call, Response<ConnectionsResponse> response) {
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            List<ConnectionsData> retailers = response.body().getData();
                            for (int i=0;i<retailers.size();i++){
                                userArrayList.add(retailers.get(i).getUser().get(0));
                            }

                            if (userArrayList.size()<=0){
                                noRetailerLayout.setVisibility(View.VISIBLE);
                            }else {
                                recyclerView.setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConnectionsResponse> call, Throwable t) {

                    }
                });

        showMeRetailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RetailersActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
