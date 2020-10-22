package com.example.foragentss.auth.agents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foragentss.R;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.constants.Constants;

import java.util.ArrayList;

import static com.example.foragentss.constants.Constants.getCardTypeValue;

public class SendCardPaymentTAdapter extends RecyclerView.Adapter<SendCardPaymentTAdapter.ViewHolder> {
    private ArrayList<CardRequest> cardRequestArrayList;
    private Context context;
    private double price;

    public SendCardPaymentTAdapter(Context context,double price, ArrayList<CardRequest> placeList) {
        this.cardRequestArrayList = placeList;
        this.context = context;
        this.price = price;

    }

    @NonNull
    @Override
    public SendCardPaymentTAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.total_payment_layout, viewGroup, false);
        return new SendCardPaymentTAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SendCardPaymentTAdapter.ViewHolder viewHolder, int i) {
        CardRequest nearBy = cardRequestArrayList.get(i);
        double unitPrice = getCardTypeValue(nearBy.getCard_type_id())*price;
        viewHolder.cardName.setText(getCardTypeValue(nearBy.getCard_type_id())+" Birr card");
        viewHolder.totalAmount.setText("Total amount: "+ Constants.getFormatedAmount(nearBy.getAmount()));
        viewHolder.unitPrice.setText("Unit price: "+unitPrice);
        viewHolder.totalPrice.setText("Total price: "+Constants.getFormatedAmount(nearBy.getAmount()*unitPrice));

    }

    @Override
    public int getItemCount() {
        return cardRequestArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardName,totalAmount,unitPrice,totalPrice;
        private LinearLayout centerLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardName = itemView.findViewById(R.id.cardTypeName);
            centerLayout = itemView.findViewById(R.id.centerLayout);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            unitPrice = itemView.findViewById(R.id.unitPrice);
            totalPrice = itemView.findViewById(R.id.totalPrice);

        }
    }

}