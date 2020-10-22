package com.example.foragentss.auth.agents.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.models.CardRequestData;
import com.example.foragentss.auth.response.CardRequestResponse;
import com.example.foragentss.auth.view_model.CardRequestViewModel;

import java.util.ArrayList;


public class NewCardRequestFragment extends Fragment implements View.OnClickListener {
    CardView cardView;
    private Button requestedAmount;
    private CardRequestViewModel cardRequestViewModel;
    private ProgressBar loading;
    private LinearLayout mainLayout;
    private LinearLayout noCardRequest;
    private ArrayList<CardRequestData> cardRequestResponses = new ArrayList<>();
    public NewCardRequestFragment() {
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
        View view= inflater.inflate(R.layout.fragment_new_card_request, container, false);

        //card request view model
        cardRequestViewModel = ViewModelProviders.of(getActivity()).get(CardRequestViewModel.class);

        loading = view.findViewById(R.id.cardRequestLoader);

        mainLayout = view.findViewById(R.id.cardRequestMainLayout);

        requestedAmount = view.findViewById(R.id.requested_amount);
        requestedAmount.setOnClickListener(this::onClick);

        cardView = view.findViewById(R.id.newCardRequestCard);
        cardView.setOnClickListener(this::onClick);

        noCardRequest = view.findViewById(R.id.noCardRequestIsFound);

        cardRequestViewModel.show("on_progress")
                .observe(getActivity(),cardRequestResponse -> {
                    loading.setVisibility(View.GONE);

                    cardRequestResponses.addAll(cardRequestResponse.getData());

                    if (cardRequestResponse.getData().size()<=0){
                        noCardRequest.setVisibility(View.VISIBLE);
                    }else {
                        mainLayout.setVisibility(View.VISIBLE);
                        requestedAmount.setText(""+cardRequestResponse.getData().size());
                    }
                });
        return  view;
    }


    @Override
    public void onClick(View view) {
        AgentsDashboard dashboard = (AgentsDashboard)getContext();
        dashboard.showCardRequest(cardRequestResponses,"New card requests");
    }
}
