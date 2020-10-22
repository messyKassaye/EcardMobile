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
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.retailers.adapter.MyCardsAdapter;
import com.example.foragentss.rooms.entity.CardTypeRoom;
import com.example.foragentss.rooms.view_model.CardTypeRoomViewModel;

import java.util.ArrayList;


public class RetailersMyCardsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<CardTypeRoom> cardTypeRoomArrayList = new ArrayList<>();
    private MyCardsAdapter adapter;
    private CardTypeRoomViewModel cardTypeRoomViewModel;
    public RetailersMyCardsFragment() {
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
        View view= inflater.inflate(R.layout.fragment_retailers_my_cards, container, false);

        adapter = new MyCardsAdapter(getContext(),cardTypeRoomArrayList);
        recyclerView = view.findViewById(R.id.homeMyCardRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        cardTypeRoomViewModel = ViewModelProviders.of(getActivity()).get(CardTypeRoomViewModel.class);
        cardTypeRoomViewModel.index().observe(getActivity(),cardTypeRooms -> {
            if (cardTypeRooms.size()>0){
                cardTypeRoomArrayList.clear();
                cardTypeRoomArrayList.addAll(cardTypeRooms);
                adapter.notifyDataSetChanged();
            }
        });


        return view;
    }

}
