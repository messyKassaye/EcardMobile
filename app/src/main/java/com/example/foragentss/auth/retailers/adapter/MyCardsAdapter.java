package com.example.foragentss.auth.retailers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.retailers.activities.DownloadActivity;
import com.example.foragentss.auth.retailers.fragments.RetailersHomeFragment;
import com.example.foragentss.rooms.entity.CardTypeRoom;
import com.example.foragentss.rooms.view_model.CardsRoomViewModel;

import java.util.ArrayList;

public class MyCardsAdapter extends RecyclerView.Adapter<MyCardsAdapter.ViewHolder> {
    private ArrayList<CardTypeRoom> banks;
    private Context context;
    private CardsRoomViewModel cardsRoomViewModel;
    public MyCardsAdapter(Context context, ArrayList<CardTypeRoom> placeList) {
        this.banks = placeList;
        this.context = context;

    }

    @NonNull
    @Override
    public MyCardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.retailer_home_my_cards_layout, viewGroup, false);
        return new MyCardsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCardsAdapter.ViewHolder viewHolder, int i) {
        CardTypeRoom nearBy = banks.get(i);
        viewHolder.cardname.setText(nearBy.getValue() + " Birr card");

        cardsRoomViewModel = ViewModelProviders.of((AppCompatActivity)context).get(CardsRoomViewModel.class);
        cardsRoomViewModel.showCard(nearBy.getId())
                .observe((AppCompatActivity)context,cards -> {
                    if (cards.size()<=0){
                        viewHolder.noCardslayout.setVisibility(View.VISIBLE);
                        viewHolder.totalCardNumbers.setVisibility(View.GONE);
                    }else {
                        viewHolder.noCardslayout.setVisibility(View.GONE);
                        viewHolder.homeCardsMainLayout.setVisibility(View.VISIBLE);
                        viewHolder.totalCardNumbers.setText(""+cards.size());
                    }
                });

        viewHolder.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DownloadActivity.class);
                intent.putExtra("card_type_id",String.valueOf(nearBy.getId()));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return banks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardname,totalCardNumbers;
        private LinearLayout noCardslayout,homeCardsMainLayout;
        private Button downloadButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardname = itemView.findViewById(R.id.homeMyCardsCardTypeName);
            totalCardNumbers = itemView.findViewById(R.id.totalMyCardNumber);
            noCardslayout = itemView.findViewById(R.id.noCardsLayout);
            downloadButton = itemView.findViewById(R.id.downloadCards);
            homeCardsMainLayout = itemView.findViewById(R.id.homeCardsMainLayout);


        }
    }
}
