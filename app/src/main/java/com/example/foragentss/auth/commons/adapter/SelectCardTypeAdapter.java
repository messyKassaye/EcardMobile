package com.example.foragentss.auth.commons.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.fragments.CardRequestCardSelectionFragment;
import com.example.foragentss.auth.models.CardType;

import java.util.ArrayList;

public class SelectCardTypeAdapter extends RecyclerView.Adapter<SelectCardTypeAdapter.ViewHolder>{
private ArrayList<CardType> cardTypes;
private Context context;
private CardRequestCardSelectionFragment cardSelectionFragment;
public SelectCardTypeAdapter(Context context, CardRequestCardSelectionFragment fragment, ArrayList<CardType> placeList){
        this.cardTypes=placeList;
        this.context=context;
        this.cardSelectionFragment = fragment;

        }

    @NonNull
    @Override
    public SelectCardTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
            View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_card_type_layout,viewGroup,false);
            return new SelectCardTypeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCardTypeAdapter.ViewHolder viewHolder,int i){
            CardType nearBy=cardTypes.get(i);
            viewHolder.cardName.setText(nearBy.getValue()+" Birr card");
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardSelectionFragment.addCardType(nearBy);
                }
            });
    }
    @Override
    public int getItemCount(){
            return cardTypes.size();
            }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardName;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardName = itemView.findViewById(R.id.cardTypeName);
            cardView = itemView.findViewById(R.id.cardTypeCardView);

        }
    }
}