package com.example.foragentss.auth.retailers.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.retailers.activities.QRCodeShowerActivity;
import com.example.foragentss.rooms.entity.Card;
import com.example.foragentss.rooms.entity.CardTypeRoom;
import com.example.foragentss.rooms.view_model.CardsRoomViewModel;

import java.util.ArrayList;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private ArrayList<CardTypeRoom> banks;
    private Context context;
    private CardsRoomViewModel cardsRoomViewModel;
    public CardsAdapter(Context context,ArrayList<CardTypeRoom> placeList) {
        this.banks = placeList;
        this.context = context;

    }

    @NonNull
    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_layout, viewGroup, false);
        return new CardsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardsAdapter.ViewHolder viewHolder, int i) {
        CardTypeRoom nearBy = banks.get(i);

        cardsRoomViewModel = ViewModelProviders.of((AppCompatActivity)context).get(CardsRoomViewModel.class);
        viewHolder.cardname.setText(nearBy.getValue()+" Birr card");

        viewHolder.sellCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardsRoomViewModel.showCard(nearBy.getId()).observe((AppCompatActivity)context,cards -> {
                    if (cards.size()>0){
                        generateQRCode(cards.get(0));
                    }else {
                        showNoCardDialog();
                    }
                });
            }
        });
    }

    public void generateQRCode(Card card){
        Intent intent = new Intent(context, QRCodeShowerActivity.class);
        intent.putExtra("card_number",card.getNumber());
        intent.putExtra("card_id",String.valueOf(card.getId()));
        intent.putExtra("card_type_id",String.valueOf(card.getCard_type_id()));
        context.startActivity(intent);
    }


    public void showNoCardDialog(){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.confirmation_dialog);
        TextView securityMessage=dialog.findViewById(R.id.security_message);
        securityMessage.setText("We can't find cards on this phone. Please add your agent and download your cards");
        ((TextView)dialog.findViewById(R.id.confirmTitle)).setText("Confirmation message");
        ((Button)dialog.findViewById(R.id.addFileButton))
                .setText("Okay");

        ((Button)dialog.findViewById(R.id.addFileButton))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
        ((Button)dialog.findViewById(R.id.closeButton))
                .setVisibility(View.GONE);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView cardname;
        private Button sellCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardname = itemView.findViewById(R.id.cardTypeName);
            sellCard = itemView.findViewById(R.id.sellCard);

        }
    }
}