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
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.adapter.PartnersAdapter;
import com.example.foragentss.auth.models.NearbyData;
import com.example.foragentss.auth.response.NearByResponse;
import com.example.foragentss.auth.view_model.NearByViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NearBypartnersFragment extends Fragment {
    private NearByViewModel nearByViewModel;
    private RecyclerView recyclerView;
    private ArrayList<NearbyData> arrayList = new ArrayList<>();
    private PartnersAdapter adapter;

    public NearBypartnersFragment() {
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
        View view= inflater.inflate(R.layout.fragment_near_bypartners, container, false);

        adapter = new PartnersAdapter(getContext(),this,arrayList);
        recyclerView = view.findViewById(R.id.near_by_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        nearByViewModel = ViewModelProviders.of(getActivity()).get(NearByViewModel.class);
        nearByViewModel.index().enqueue(new Callback<NearByResponse>() {
            @Override
            public void onResponse(Call<NearByResponse> call, Response<NearByResponse> response) {
                if (response.isSuccessful()){
                    arrayList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NearByResponse> call, Throwable t) {
                System.out.println("NEARBY "+t.getMessage());
            }
        });
        return view;
    }


}
