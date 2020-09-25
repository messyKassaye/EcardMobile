package com.example.foragentss.dashboard.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.foragentss.R;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.dashboard.ui.activities.AcceptCardActivity;
import com.example.foragentss.dashboard.ui.activities.SellCardActivity;
import com.example.foragentss.rooms.view_model.CardsViewModel;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CardsViewModel cardsViewModel;
    private LinearLayout mainLayout,noSellLayout;
    private Button acceptCardBtn,sellCardBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        cardsViewModel = ViewModelProviders.of(this).get(CardsViewModel.class);

        mainLayout = root.findViewById(R.id.mainLayout);
        noSellLayout = root.findViewById(R.id.noSellLayout);

        cardsViewModel.showSell(Constants.currentDate(),"5")
                .observe(getActivity(),cards -> {
                    if (cards.size()<=0){
                        mainLayout.setVisibility(View.GONE);
                        noSellLayout.setVisibility(View.VISIBLE);
                    }else {
                        mainLayout.setVisibility(View.VISIBLE);
                        noSellLayout.setVisibility(View.GONE);
                    }
                });

        acceptCardBtn = root.findViewById(R.id.acceptCards);
        sellCardBtn = root.findViewById(R.id.sellCards);

        acceptCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AcceptCardActivity.class);
                getActivity().startActivity(intent);
            }
        });

        sellCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SellCardActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return root;
    }
}