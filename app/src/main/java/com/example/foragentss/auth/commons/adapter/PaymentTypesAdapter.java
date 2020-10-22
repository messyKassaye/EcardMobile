package com.example.foragentss.auth.commons.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.fragments.SendCardPaymentTransactionFragment;
import com.example.foragentss.auth.models.BankAccount;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.CardRequestPayment;
import com.example.foragentss.auth.models.PaymentType;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.CardRequestPaymentViewModel;
import com.example.foragentss.auth.view_model.CardRequestViewModel;
import com.example.foragentss.auth.view_model.CardSendViewModel;

import java.util.ArrayList;

public class PaymentTypesAdapter extends RecyclerView.Adapter<PaymentTypesAdapter.ViewHolder> {
    private ArrayList<PaymentType> bankAccounts;
    private Context context;
    private User sendToUser;
    private ArrayList<CardRequest> cardRequestArrayList;
    private CardSendViewModel cardSendViewModel;
    private SendCardPaymentTransactionFragment fragment;
    public PaymentTypesAdapter(Context context,SendCardPaymentTransactionFragment fragment,ArrayList<PaymentType> placeList,
                               ArrayList<CardRequest> cardRequests,User sendToUser) {
        this.bankAccounts = placeList;
        this.context = context;
        this.cardRequestArrayList = cardRequests;
        this.sendToUser = sendToUser;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public PaymentTypesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.payment_type_layout, viewGroup, false);
        return new PaymentTypesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentTypesAdapter.ViewHolder viewHolder, int i) {
        PaymentType nearBy = bankAccounts.get(i);
        cardSendViewModel = ViewModelProviders.of((AppCompatActivity)context).get(CardSendViewModel.class);
        cardSendViewModel.storeResponse().observe((AppCompatActivity)context,this::consumeResponse);
        viewHolder.selectBTN.setText(nearBy.getName());
        viewHolder.selectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.showSendingOnProccess();

                CardRequest cardRequest =new CardRequest();
                cardRequest.setCard_type_id(cardRequestArrayList.get(0).getCard_type_id());
                cardRequest.setPayment_type_id(nearBy.getId());
                cardRequest.setAmount(cardRequestArrayList.get(0).getAmount());
                cardRequest.setRequester_id(sendToUser.getId());
                cardRequest.setStatus("Sold");
                cardSendViewModel.store(cardRequest);

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
        System.out.println("INDEX:"+response.getIndex());
        fragment.responseLayout(response);

    }
    @Override
    public int getItemCount() {
        return bankAccounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final Button selectBTN;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            selectBTN = itemView.findViewById(R.id.selectPayment);
        }
    }
}