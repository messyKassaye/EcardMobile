package com.example.foragentss.auth.retailers.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.adapter.CardsAdapter;
import com.example.foragentss.auth.models.Card;
import com.example.foragentss.auth.response.CardsResponse;
import com.example.foragentss.auth.view_model.CardsViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCloudCardsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CardsViewModel cardsViewModel;
    private CardsAdapter adapter;
    private ArrayList<Card> arrayList = new ArrayList<>();
    public MyCloudCardsFragment() {
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
        View view= inflater.inflate(R.layout.fragment_my_cloud_cards, container, false);

        cardsViewModel = ViewModelProviders.of(getActivity()).get(CardsViewModel.class);

        progressBar = view.findViewById(R.id.cloudPRB);

        adapter = new CardsAdapter(getContext(),arrayList);
        recyclerView = view.findViewById(R.id.cloudsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        cardsViewModel.index().enqueue(new Callback<CardsResponse>() {
            @Override
            public void onResponse(Call<CardsResponse> call, Response<CardsResponse> response) {
                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    arrayList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<CardsResponse> call, Throwable t) {

            }
        });
        return view;
    }

}
