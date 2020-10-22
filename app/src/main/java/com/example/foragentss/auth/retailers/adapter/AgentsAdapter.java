package com.example.foragentss.auth.retailers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.activities.CardRequestActivity;
import com.example.foragentss.rooms.entity.AgentAndPartner;

import java.util.ArrayList;

public class AgentsAdapter extends RecyclerView.Adapter<AgentsAdapter.ViewHolder> {
    private ArrayList<AgentAndPartner> banks;
    private Context context;
    public AgentsAdapter(Context context,ArrayList<AgentAndPartner> placeList) {
        this.banks = placeList;
        this.context = context;

    }

    @NonNull
    @Override
    public AgentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agents_card_layout, viewGroup, false);
        return new AgentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentsAdapter.ViewHolder viewHolder, int i) {
        AgentAndPartner nearBy = banks.get(i);
        viewHolder.agentFullName.setText(nearBy.getFull_name());
        viewHolder.agentPhone.setText(nearBy.getPhone());
        viewHolder.agentAvatar.setText(String.valueOf(nearBy.getFull_name().charAt(0)));

        viewHolder.agentCardRequestBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CardRequestActivity.class);

                intent.putExtra("Role","Retailer");
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return banks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView agentFullName,agentPhone;
        private Button agentAvatar,agentCardRequestBTN;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            agentFullName = itemView.findViewById(R.id.agentFullName);
            agentPhone = itemView.findViewById(R.id.agentPhone);
            agentAvatar = itemView.findViewById(R.id.agentAvatar);
            agentCardRequestBTN = itemView.findViewById(R.id.agentCardRequestBTN);

        }
    }
}