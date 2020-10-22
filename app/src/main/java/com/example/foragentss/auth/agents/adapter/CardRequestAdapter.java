package com.example.foragentss.auth.agents.adapter;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.CardRequestData;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.CardRequestViewModel;
import com.example.foragentss.constants.Constants;

import java.util.ArrayList;

public class CardRequestAdapter extends RecyclerView.Adapter<CardRequestAdapter.ViewHolder> {
    private ArrayList<CardRequestData> cardRequestArrayList;
    private Context context;
    private CardRequestViewModel cardRequestViewModel;
    private CardRequestAdapter.ViewHolder viewHolders;
    public CardRequestAdapter(Context context,ArrayList<CardRequestData> placeList) {
        this.cardRequestArrayList = placeList;
        this.context = context;

    }

    @NonNull
    @Override
    public CardRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_request_layout, viewGroup, false);
        return new CardRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardRequestAdapter.ViewHolder viewHolder, int i) {
        CardRequestData nearBy = cardRequestArrayList.get(i);

        viewHolders = viewHolder;
        cardRequestViewModel = ViewModelProviders.of((AppCompatActivity)context).get(CardRequestViewModel.class);
        cardRequestViewModel.storeResponse().observe((AppCompatActivity)context,this::consumeResponse);

        viewHolder.requestName.setText(nearBy.getUser().get(0).getFirst_name()+" "
                +nearBy.getUser().get(0).getLast_name());
        viewHolder.avatar.setText(String.valueOf(nearBy.getUser().get(0).getFirst_name().charAt(0)));
        viewHolder.phone.setText(nearBy.getUser().get(0).getPhone());
        viewHolder.cardTypeName.setText("Card type: "+nearBy.getCard_type().getValue()+" Birr card");
        viewHolder.cardAmount.setText("Amount: "+ Constants.getFormatedAmount(nearBy.getAmount()));
        if (nearBy.getPayment()!=null){
            viewHolder.paymentStatus.setText("Payment status: Payed");
            viewHolder.transactionRefNumber.setText("Transaction Ref number: "+nearBy.getPayment().getTransaction_ref_number());
            viewHolder.bankName.setText("Bank name: "+nearBy.getPayment().getBank().getName());
        }else {
            viewHolder.paymentStatus.setText("Payment status: Not payed");
        }

        viewHolder.sendCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardRequestViewModel.update(nearBy.getId(),"Sold");
                viewHolder.buttonLayout.setVisibility(View.GONE);
                viewHolder.loadingLayout.setVisibility(View.VISIBLE);
            }
        });

        viewHolder.removeCardRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardRequestViewModel.update(nearBy.getId(),"Remove");
                viewHolder.buttonLayout.setVisibility(View.GONE);
                viewHolder.loadingLayout.setVisibility(View.VISIBLE);
            }
        });

        viewHolder.reportAsSpam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardRequestViewModel.update(nearBy.getId(),"Spam");
                viewHolder.buttonLayout.setVisibility(View.GONE);
                viewHolder.loadingLayout.setVisibility(View.VISIBLE);
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
                viewHolders.loadingLayout.setVisibility(View.GONE);
                viewHolders.displayMessage.setText("Your request is updated");
                viewHolders.displayMessage.setVisibility(View.VISIBLE);
        }
        if (!response.isStatus()){
            viewHolders.loadingLayout.setVisibility(View.GONE);
            viewHolders.buttonLayout.setVisibility(View.VISIBLE);
            showDialog(response.getMessage());
        }

    }

    public void  showDialog(String messsage){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.confirmation_dialog);
        ((TextView)dialog.findViewById(R.id.confirmTitle)).setText("Message to you");
        TextView securityMessage=dialog.findViewById(R.id.security_message);
        securityMessage.setText(messsage);
        ((Button)dialog.findViewById(R.id.closeButton)).setText("Okay");
        ((Button)dialog.findViewById(R.id.closeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((Button)dialog.findViewById(R.id.addFileButton)).setVisibility(View.GONE);

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return cardRequestArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView requestName,phone,cardTypeName,cardAmount;
        private TextView paymentStatus,transactionRefNumber,bankName;
        private Button avatar,reportAsSpam,removeCardRequest,sendCard;
        private LinearLayout loadingLayout;
        private RelativeLayout buttonLayout;
        private TextView displayMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            requestName =itemView.findViewById(R.id.requesterName);
            avatar = itemView.findViewById(R.id.requestAvatar);
            phone= itemView.findViewById(R.id.requesterPhone);
            cardTypeName = itemView.findViewById(R.id.cardTypeName);
            cardAmount = itemView.findViewById(R.id.cardAmount);
            paymentStatus = itemView.findViewById(R.id.paymentStatus);
            transactionRefNumber = itemView.findViewById(R.id.transactionRedNumber);
            bankName = itemView.findViewById(R.id.bankName);
            reportAsSpam = itemView.findViewById(R.id.spamCardRequest);
            removeCardRequest = itemView.findViewById(R.id.removeCardRequest);
            sendCard = itemView.findViewById(R.id.sendCard);
            buttonLayout = itemView.findViewById(R.id.cardRequestButtonLayout);
            loadingLayout = itemView.findViewById(R.id.cardRequestLoadingLayout);
            displayMessage = itemView.findViewById(R.id.cardRequestDisplayMessage);

        }
    }
}