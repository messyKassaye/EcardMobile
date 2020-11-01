package com.example.foragentss.auth.commons.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.fragments.CardRequestCardSelectionFragment;
import com.example.foragentss.auth.commons.fragments.ShowConnectionFragment;
import com.example.foragentss.auth.models.CardType;
import com.example.foragentss.auth.models.ConnectionsData;

import java.util.ArrayList;

public class ShowConnectionAdapter extends RecyclerView.Adapter<ShowConnectionAdapter.ViewHolder>{
    private ArrayList<ConnectionsData> cardTypes;
    private Context context;
    private ShowConnectionFragment fragment;
    public ShowConnectionAdapter(Context context,ShowConnectionFragment showConnectionFragment ,ArrayList<ConnectionsData> placeList){
        this.cardTypes=placeList;
        this.context=context;
        this.fragment = showConnectionFragment;
    }

    @NonNull
    @Override
    public ShowConnectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.near_by_layout,viewGroup,false);
        return new ShowConnectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowConnectionAdapter.ViewHolder viewHolder,int i){
        ConnectionsData nearBy=cardTypes.get(i);


        viewHolder.full_company_name.setText(nearBy.getUser().get(0).getFirst_name());
        viewHolder.address.setText(nearBy.getUser().get(0).getRole().get(0).getName());
        viewHolder.avatar.setText(String.valueOf(nearBy.getUser().get(0).getFirst_name().charAt(0)));
        if (nearBy.getUser().get(0).getVerification()!=null){
            viewHolder.verification.setVisibility(View.VISIBLE);
        }
        if (nearBy.getUser().get(0).getConnection()!=null){
            viewHolder.connect.setVisibility(View.GONE);
            viewHolder.connectionLoading.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.GONE);
            if(nearBy.getUser().get(0).getConnection().getStatus().equals("waiting")){
                viewHolder.connectText.setTextColor(Color.RED);
            }else {
                viewHolder.connectText.setTextColor(Color.GREEN);
            }
            viewHolder.connectText.setText(nearBy.getUser().get(0).getConnection().getStatus());
        }

        viewHolder.selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.sendTo(nearBy);
            }
        });

    }
    @Override
    public int getItemCount(){
        return cardTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView full_company_name,address,roleName;
        private Button avatar,connect,selectBtn;
        private LinearLayout connectionLoading;
        private ProgressBar progressBar;
        private TextView connectText;
        private ImageView verification;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            full_company_name = itemView.findViewById(R.id.full_company_name);
            address = itemView.findViewById(R.id.address);
            avatar = itemView.findViewById(R.id.avatar);
            roleName = itemView.findViewById(R.id.roleName);
            connect = itemView.findViewById(R.id.connectBtn);
            connect.setVisibility(View.GONE);

            selectBtn = itemView.findViewById(R.id.selectBtn);
            selectBtn.setVisibility(View.VISIBLE);
            connectionLoading = itemView.findViewById(R.id.connectionLoadingLayout);
            progressBar = itemView.findViewById(R.id.connectPR);
            connectText = itemView.findViewById(R.id.connectText);
            verification = itemView.findViewById(R.id.verification);
        }
    }
}