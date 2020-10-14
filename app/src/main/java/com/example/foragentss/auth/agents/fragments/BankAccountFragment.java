package com.example.foragentss.auth.agents.fragments;

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

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.adapter.BanksAdapter;
import com.example.foragentss.auth.helpers.GridSpacingItemDecoration;
import com.example.foragentss.auth.models.Bank;
import com.example.foragentss.auth.response.BankResponse;
import com.example.foragentss.auth.view_model.BanksViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BankAccountFragment extends Fragment {

    private BanksViewModel banksViewModel;
    private BanksAdapter adapter;
    private RecyclerView recyclerView;
    private ArrayList<Bank> bankArrayList = new ArrayList<>();
    public BankAccountFragment() {
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
        View view= inflater.inflate(R.layout.fragment_bank_account, container, false);
        banksViewModel = ViewModelProviders.of(getActivity()).get(BanksViewModel.class);
        adapter = new BanksAdapter(getContext(),bankArrayList);
        recyclerView = view.findViewById(R.id.bankAccountRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        banksViewModel.index().enqueue(new Callback<BankResponse>() {
            @Override
            public void onResponse(Call<BankResponse> call, Response<BankResponse> response) {
                if (response.isSuccessful()){
                    bankArrayList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BankResponse> call, Throwable t) {

            }
        });
        return view;
    }

}
