package com.example.foragentss.auth.agents.adapter;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foragentss.R;
import com.example.foragentss.auth.agents.activities.CardRequestActivity;
import com.example.foragentss.auth.agents.activities.SendCardActivity;
import com.example.foragentss.auth.models.User;

import java.util.ArrayList;

public class PartnersAdapter extends RecyclerView.Adapter<PartnersAdapter.ViewHolder> {
    private ArrayList<User> users;
    private Context context;
    private String role;
    public PartnersAdapter(Context context,String role,ArrayList<User> placeList) {
        this.users = placeList;
        this.context = context;
        this.role = role;

    }

    @NonNull
    @Override
    public PartnersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_layout, viewGroup, false);
        return new PartnersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PartnersAdapter.ViewHolder viewHolder, int i) {
        User user = users.get(i);

        viewHolder.fullName.setText(user.getFirst_name());
        viewHolder.avatar.setText(""+user.getFirst_name().charAt(0));
        if (user.getCompany()==null){
            viewHolder.address.setText(user.getUser_name());
        }else {
            viewHolder.address.setText(user.getCompany().getName());
        }
        viewHolder.phoneNumber.setText("Phone number: "+user.getPhone());
        if (role.equalsIgnoreCase("Retailer")){
            viewHolder.sendCardRequest.setText("Send card");
        }
        viewHolder.sendCardRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (role.equalsIgnoreCase("Retailer")){
                    Intent intent = new Intent(context, SendCardActivity.class);
                    intent.putExtra("user_id",user);
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context,CardRequestActivity.class);
                    intent.putExtra("user_id",user);
                    intent.putExtra("Role","Agent");
                    context.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fullName,address,phoneNumber,emailAddress;
        private Button avatar,sendCardRequest;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.full_partner_name);
            avatar = itemView.findViewById(R.id.avatar);
            address = itemView.findViewById(R.id.partnerAddress);
            phoneNumber = itemView.findViewById(R.id.phoneNumber);
            emailAddress = itemView.findViewById(R.id.emailAddress);
            sendCardRequest = itemView.findViewById(R.id.sendCardRequest);
        }
    }
}