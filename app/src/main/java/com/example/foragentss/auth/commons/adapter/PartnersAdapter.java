package com.example.foragentss.auth.commons.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.fragments.NearBypartnersFragment;
import com.example.foragentss.auth.models.MyPartner;
import com.example.foragentss.auth.models.NearbyData;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.MyPartnerAgentViewModel;
import com.example.foragentss.auth.view_model.MyPartnersViewModel;
import com.example.foragentss.rooms.entity.AgentAndPartner;
import com.example.foragentss.rooms.view_model.AgentPartnerViewModel;

import java.util.ArrayList;

public class PartnersAdapter extends RecyclerView.Adapter<PartnersAdapter.ViewHolder> {
    private ArrayList<NearbyData> nearbyData;
    private Context context;
    private ViewHolder viewHolders;
    private NearBypartnersFragment fragment;
    private MyPartnerAgentViewModel myPartnerAgentViewModel;
    private AgentPartnerViewModel agentPartnerViewModel;
    public PartnersAdapter(Context context,NearBypartnersFragment fragment,ArrayList<NearbyData> placeList) {
        this.nearbyData = placeList;
        this.context = context;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public PartnersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.near_by_layout, viewGroup, false);
        return new PartnersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartnersAdapter.ViewHolder viewHolder, int i) {
        NearbyData nearBy = nearbyData.get(i);

        myPartnerAgentViewModel = ViewModelProviders.of((AppCompatActivity)context).get(MyPartnerAgentViewModel.class);
        myPartnerAgentViewModel.storeResponse().observe((AppCompatActivity)context,this::consumeResponse);

        agentPartnerViewModel = ViewModelProviders.of((AppCompatActivity)context).get(AgentPartnerViewModel.class);

        viewHolder.full_company_name.setText(nearBy.getUser().get(0).getFirst_name());
        viewHolder.address.setText(nearBy.getRegion_name()+" > "+nearBy.getCity_name());
        viewHolder.avatar.setText(String.valueOf(nearBy.getUser().get(0).getFirst_name().charAt(0)));
        viewHolder.roleName.setText(nearBy.getUser().get(0).getRole().get(0).getName());
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
        viewHolder.connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nearBy.getUser().get(0).getVerification()==null){
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    final Window window = dialog.getWindow();
                    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                    window.setGravity(Gravity.CENTER);
                    dialog.setContentView(R.layout.confirmation_dialog);
                    TextView securityMessage=dialog.findViewById(R.id.security_message);
                    if (nearBy.getUser().get(0).getCompany()==null){
                        securityMessage.setText("This person is not verified by any company or agent. You shouldn't connect with unverified person or company by Ecard or by any partner or agents. do you want to connect ?");
                    }else {
                        securityMessage.setText("This Company is not verified by any company or agent. You shouldn't connect with unverified person or company by Ecard or by any partner or agents. do you want to connect ?");
                    }
                    ((Button)dialog.findViewById(R.id.addFileButton))
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                    connect(viewHolder,nearBy.getUser().get(0));
                                    //addFile();
                                }
                            });
                    ((Button)dialog.findViewById(R.id.closeButton))
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();

                                }
                            });
                    dialog.show();
                }else {
                    connect(viewHolder,nearBy.getUser().get(0));
                }
            }
        });

    }

    public  void  connect(ViewHolder viewHolder, User user){
        viewHolders = viewHolder;
        viewHolder.buttonsLayout.setVisibility(View.GONE);
        viewHolder.connectionLoading.setVisibility(View.VISIBLE);

        if (user.getRole().get(0).getId()==2){
            MyPartner myPartner =new MyPartner();
            myPartner.setPartner_id(user.getId());
            myPartnerAgentViewModel.store("partner_agent",myPartner);
        }else {
            MyPartner myPartner =new MyPartner();
            myPartner.setAgent_id(user.getId());
            myPartnerAgentViewModel.store("agent_retailer",myPartner);
            AgentAndPartner agentAndPartner =new AgentAndPartner();
            agentAndPartner.setFull_name(user.getFirst_name());
            agentAndPartner.setPhone(user.getPhone());
            agentAndPartner.setAgent_partner_id(user.getId());
            agentPartnerViewModel.store(agentAndPartner);
        }
    }

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.status) {

            case LOADING:
                break;

            case SUCCESS:
                renderSuccessResponse(apiResponse.data);
                break;

            case ERROR:
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
            viewHolders.progressBar.setVisibility(View.GONE);
            viewHolders.connectText.setText("Connection request sent");
            viewHolders.connectText.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }

    }
    @Override
    public int getItemCount() {
        return nearbyData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView full_company_name,address,roleName;
        private Button avatar,connect;
        private LinearLayout connectionLoading,buttonsLayout;
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
            connectionLoading = itemView.findViewById(R.id.connectionLoadingLayout);
            progressBar = itemView.findViewById(R.id.connectPR);
            connectText = itemView.findViewById(R.id.connectText);
            verification = itemView.findViewById(R.id.verification);
            buttonsLayout = itemView.findViewById(R.id.connectionButtonLayout);
        }
    }

}
