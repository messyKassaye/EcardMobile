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

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.adapter.PaymentTypesAdapter;
import com.example.foragentss.auth.helpers.GridSpacingItemDecoration;
import com.example.foragentss.auth.models.CardPrice;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.CardType;
import com.example.foragentss.auth.models.PaymentType;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.view_model.CardTypeViewModel;
import com.example.foragentss.auth.view_model.PaymentTypeViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PaymentTypesFragment extends Fragment {
    private PaymentTypeViewModel paymentTypeViewModel;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private PaymentTypesAdapter adapter;
    private ArrayList<PaymentType> arrayList = new ArrayList<>();
    private ArrayList<CardRequest> cardRequestArrayList;
    private User sendToUser;
    private ArrayList<CardPrice> cardPriceArrayList;
    public PaymentTypesFragment() {
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
        View view= inflater.inflate(R.layout.fragment_payment_types, container, false);



        return view;
    }


}
