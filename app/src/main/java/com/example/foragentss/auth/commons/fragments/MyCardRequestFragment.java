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

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.adapter.MyCardRequestAdapter;
import com.example.foragentss.auth.models.MyCardRequest;
import com.example.foragentss.auth.models.MyCardRequestData;
import com.example.foragentss.auth.view_model.MyCardRequestViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCardRequestFragment extends Fragment {

    private MyCardRequestViewModel myCardRequestViewModel;
    private ArrayList<MyCardRequest> myCardRequestArrayList = new ArrayList<>();
    private MyCardRequestAdapter adapter;

    private RecyclerView recyclerView;
    public MyCardRequestFragment() {
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
        View view = inflater.inflate(R.layout.fragment_my_card_request, container, false);

        adapter = new MyCardRequestAdapter(getContext(),myCardRequestArrayList);
        recyclerView = view.findViewById(R.id.myCardRequestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        myCardRequestViewModel = ViewModelProviders.of(getActivity()).get(MyCardRequestViewModel.class);
        myCardRequestViewModel.index().enqueue(new Callback<MyCardRequestData>() {
            @Override
            public void onResponse(Call<MyCardRequestData> call, Response<MyCardRequestData> response) {
                if (response.isSuccessful()){
                    view.findViewById(R.id.myCardRequestLoading).setVisibility(View.GONE);
                    view.findViewById(R.id.myCardRequestMainLayout).setVisibility(View.VISIBLE);

                   myCardRequestArrayList.addAll(response.body().getData());
                   adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MyCardRequestData> call, Throwable t) {

            }
        });
        return view;
    }

}
