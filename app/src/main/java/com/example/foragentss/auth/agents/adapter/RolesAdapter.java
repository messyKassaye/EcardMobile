package com.example.foragentss.auth.agents.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.activities.CardRequestActivity;
import com.example.foragentss.auth.models.MyPartner;
import com.example.foragentss.auth.models.RoleUser;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.MyPartnerAgentViewModel;

import java.util.ArrayList;

public class RolesAdapter extends RecyclerView.Adapter<RolesAdapter.ViewHolder> {
    private ArrayList<RoleUser> users;
    private Context context;
    private MyPartnerAgentViewModel agentViewModel;
    private ViewHolder viewHolders;
    public RolesAdapter(Context context,ArrayList<RoleUser> placeList) {
        this.users = placeList;
        this.context = context;

    }

    @NonNull
    @Override
    public RolesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.role_layout, viewGroup, false);
        return new RolesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RolesAdapter.ViewHolder viewHolder, int i) {
        RoleUser user = users.get(i);
        viewHolders = viewHolder;

        agentViewModel = ViewModelProviders.of((AppCompatActivity)context).get(MyPartnerAgentViewModel.class);
        agentViewModel.storeResponse().observe((AppCompatActivity)context,this::consumeResponse);
        viewHolder.fullName.setText(user.getFirst_name()+" "+user.getLast_name());
        viewHolder.address.setText(user.getRegion_name()+" > "+user.getPhone());
        viewHolder.avatar.setText(String.valueOf(user.getFirst_name().charAt(0)));
        viewHolder.sendCardRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.buttonLayout.setVisibility(View.GONE);
                viewHolder.sendingLayout.setVisibility(View.VISIBLE);
                MyPartner myPartner=new MyPartner();
                myPartner.setAgent_id(user.getId());
                agentViewModel.store("agent_retailer",myPartner);
            }
        });
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                break;

            case SUCCESS:
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
                System.out.println("ERRORR: "+apiResponse.error.getMessage());
                break;

            default:
                break;
        }
    }

    /*
     * method to handle success response
     * */
    private void renderSuccessResponse(SuccessResponse response) {
        if(response.isStatus()) {
            viewHolders.sendPBR.setVisibility(View.GONE);
            viewHolders.sendingSuccess.setText(response.getMessage());
            viewHolders.sendingSuccess.setTextColor(Color.GREEN);
        }

    }


    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fullName,address,sendingSuccess;
        private Button avatar,sendCardRequest;
        private LinearLayout buttonLayout,sendingLayout;
        private ProgressBar sendPBR;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.full_user_name);
            avatar = itemView.findViewById(R.id.avatar);
            address = itemView.findViewById(R.id.partnerAddress);
            buttonLayout = itemView.findViewById(R.id.buttonsLayout);
            sendingLayout = itemView.findViewById(R.id.sendingLayout);
            sendCardRequest = itemView.findViewById(R.id.sendRequestBtn);

            sendingSuccess = itemView.findViewById(R.id.sendingText);
            sendPBR = itemView.findViewById(R.id.sendingPBR);
        }
    }
}