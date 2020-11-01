package com.example.foragentss.auth.retailers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.activities.CardRequestActivity;
import com.example.foragentss.auth.models.AgentPartner;

import java.util.ArrayList;

public class AgentsAdapter extends RecyclerView.Adapter<AgentsAdapter.ViewHolder> {
    private ArrayList<AgentPartner> banks;
    private Context context;
    public AgentsAdapter(Context context,ArrayList<AgentPartner> placeList) {
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
        AgentPartner nearBy = banks.get(i);
        if (nearBy.getUser().size()>0){
            viewHolder.agentFullName.setText(nearBy.getUser().get(0).getFirst_name());
            viewHolder.agentPhone.setText(nearBy.getUser().get(0).getPhone());
            viewHolder.agentAvatar.setText(String.valueOf(nearBy.getUser().get(0).getFirst_name().charAt(0)));

            viewHolder.agentCardRequestBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (nearBy.getUser().get(0).getCard_price().size()>0){
                        Intent intent = new Intent(context, CardRequestActivity.class);
                        intent.putExtra("Role","Retailer");
                        intent.putExtra("user_id",nearBy.getUser().get(0));
                        context.startActivity(intent);
                    }else {
                        Toast.makeText(context,"This agent haven't set his card selling price. Please select other agent",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
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