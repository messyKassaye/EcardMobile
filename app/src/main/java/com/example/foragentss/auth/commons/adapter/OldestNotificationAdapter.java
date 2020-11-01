package com.example.foragentss.auth.commons.adapter;

import android.content.Context;
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
import com.example.foragentss.auth.models.Connect;
import com.example.foragentss.auth.models.MyPartner;
import com.example.foragentss.auth.models.Notification;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.MyPartnerAgentViewModel;

import java.util.ArrayList;

public class OldestNotificationAdapter extends  RecyclerView.Adapter<OldestNotificationAdapter.ViewHolder> {
    private ArrayList<Notification> cards;
    private Context context;
    private ViewHolder viewHolders;

    private MyPartnerAgentViewModel myPartnerAgentViewModel;
    public OldestNotificationAdapter(Context context,ArrayList<Notification> placeList) {
            this.context = context;
            this.cards = placeList;

            }

    @NonNull
    @Override
    public OldestNotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_layout, viewGroup, false);
            return new OldestNotificationAdapter.ViewHolder(view);
            }

    @Override
    public void onBindViewHolder(@NonNull OldestNotificationAdapter.ViewHolder viewHolder, int i) {
            Notification nearBy = cards.get(i);
            Toast.makeText(context,""+nearBy.getPath_id(),Toast.LENGTH_LONG).show();

            myPartnerAgentViewModel =ViewModelProviders.of((AppCompatActivity)context).get(MyPartnerAgentViewModel.class);
            myPartnerAgentViewModel.storeResponse().observe((AppCompatActivity)context,this::consumeResponse);

            viewHolders = viewHolder;
            viewHolder.notificationName.setText(nearBy.getEntity().getMessage());
            viewHolder.notifierName.setText(nearBy.getUser().get(0).getFirst_name());
            viewHolder.mainNotificationMessage.setText(nearBy.getMessage());
            viewHolder.avatar.setText(String.valueOf(nearBy.getUser().get(0).getFirst_name().charAt(0)));

        if (nearBy.getEntity().getId()==3){
            viewHolder.buttonLayout.setVisibility(View.GONE);
        }

        viewHolder.acceptBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.loadingLayout.setVisibility(View.VISIBLE);
                viewHolder.buttonLayout.setVisibility(View.GONE);
                if (nearBy.getUser().get(0).getRole().get(0).getId()==2){
                    MyPartner myPartner =new MyPartner();
                    myPartner.setPartner_id(nearBy.getUser().get(0).getId());
                    myPartner.setStatus(1);
                    myPartnerAgentViewModel.update("partner_agent",nearBy.getPath_id(),myPartner);
                }else {
                    MyPartner myPartner =new MyPartner();
                    myPartner.setAgent_id(nearBy.getUser().get(0).getId());
                    myPartner.setStatus(1);
                    myPartnerAgentViewModel.update("agent_retailer",nearBy.getPath_id(),myPartner);
                }
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                viewHolder.displayMessage.setVisibility(View.VISIBLE);
                                viewHolder.displayMessage.setText("Action is completed");
                                viewHolder.loadingLayout.setVisibility(View.GONE);
                            }
                        },
                        2500);
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
        System.out.println("STATUS: "+response.isStatus());
        Toast.makeText(context,response.getMessage(),Toast.LENGTH_LONG).show();
        if(response.isStatus()) {
            viewHolders.displayMessage.setVisibility(View.VISIBLE);
            viewHolders.displayMessage.setText("Action is completed");
            viewHolders.loadingLayout.setVisibility(View.GONE);
        }

    }



    @Override
    public int getItemCount() {
            return cards.size();
            }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView notifierName, notificationName,mainNotificationMessage;
        private  TextView displayMessage;
        private LinearLayout buttonLayout,loadingLayout;
        private Button avatar,acceptBTN;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notifierName = itemView.findViewById(R.id.notifierName);
            notificationName = itemView.findViewById(R.id.notificationName);
            mainNotificationMessage = itemView.findViewById(R.id.mainMessage);
            avatar = itemView.findViewById(R.id.avatar);
            acceptBTN = itemView.findViewById(R.id.acceptBtn);
            displayMessage = itemView.findViewById(R.id.displayMessage);
            buttonLayout = itemView.findViewById(R.id.notificationButtonLayout);
            loadingLayout = itemView.findViewById(R.id.loadingLayout);

        }
    }
}