package com.example.foragentss.auth.agents.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.adapter.CardRequestAdapter;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.CardRequestData;
import com.example.foragentss.auth.response.CardRequestResponse;
import com.example.foragentss.auth.view_model.CardRequestViewModel;

import java.util.ArrayList;


public class AllCardRequestFragment extends Fragment {
    private CardRequestViewModel cardRequestViewModel;
    private ArrayList<CardRequestData> cardRequestArrayList =new ArrayList<>();
    private CardRequestAdapter adapter;
    private String info;

    private RecyclerView recyclerView;
    private TextView textView;
    public AllCardRequestFragment() {
        // Required empty public constructor
    }

    public AllCardRequestFragment(ArrayList<CardRequestData> cardRequests, String info){
        this.cardRequestArrayList.addAll(cardRequests);
        this.info = info;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_all_card_request, container, false);

        textView = view.findViewById(R.id.requestInfo);
        textView.setText(info);

        adapter = new CardRequestAdapter(getContext(),cardRequestArrayList);
        recyclerView = view.findViewById(R.id.allCardRequestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

}
