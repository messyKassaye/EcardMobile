package com.example.foragentss.auth.commons.fragments;

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
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.adapter.CardsAdapter;
import com.example.foragentss.auth.models.Card;
import com.example.foragentss.auth.response.CardsResponse;
import com.example.foragentss.auth.view_model.CardsViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCardsFragment extends Fragment {
    private CardsViewModel cardsViewModel;
    private RecyclerView recyclerView;
    private CardsAdapter adapter;
    private ArrayList<Card> cardArrayList = new ArrayList<>();
    private ProgressBar progressBar;
    public MyCardsFragment() {
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
        View view= inflater.inflate(R.layout.fragment_my_cards, container, false);


        adapter = new CardsAdapter(getContext(),cardArrayList);
        recyclerView = view.findViewById(R.id.cardsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        cardsViewModel = ViewModelProviders.of(getActivity()).get(CardsViewModel.class);
        cardsViewModel.index().enqueue(new Callback<CardsResponse>() {
            @Override
            public void onResponse(Call<CardsResponse> call, Response<CardsResponse> response) {
                 view.findViewById(R.id.my_cards_pr).setVisibility(View.GONE);
                if (response.isSuccessful()){
                    view.findViewById(R.id.cardsRecyclerView).setVisibility(View.VISIBLE);
                    cardArrayList.addAll(response.body().getData());
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
