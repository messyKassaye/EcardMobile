package com.example.foragentss.auth.commons.adapter;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.Card;
import com.example.foragentss.auth.models.Complain;
import com.example.foragentss.auth.models.MyCardRequest;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.ComplainViewModel;
import com.example.foragentss.constants.Constants;

import java.util.ArrayList;

public class MyCardRequestAdapter  extends RecyclerView.Adapter<MyCardRequestAdapter.ViewHolder> {
    private ArrayList<MyCardRequest> cards;
    private Context context;
    private Dialog dialog;
    private ComplainViewModel complainViewModel;
    private ViewHolder viewHolders;
    public MyCardRequestAdapter(Context context,ArrayList<MyCardRequest> placeList) {
        this.context = context;
        this.cards = placeList;

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.complain_dialog);

    }

    @NonNull
    @Override
    public MyCardRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_card_request_layout, viewGroup, false);
        return new MyCardRequestAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCardRequestAdapter.ViewHolder viewHolder, int i) {
        MyCardRequest nearBy = cards.get(i);
        viewHolders = viewHolder;

        complainViewModel = ViewModelProviders.of((AppCompatActivity)context).get(ComplainViewModel.class);
        complainViewModel.storeResponse().observe((AppCompatActivity)context,this::consumeResponse);

        viewHolder.requested_to.setText("Requested to: "+nearBy.getUser().get(0).getFirst_name());
        viewHolder.cardType.setText("Card type: "+nearBy.getCard_type().getValue()+" Birr card");

        double unitPrice = nearBy.getUser().get(0).getCard_price().get(0).getPercentage()*nearBy.getCard_type().getValue();
        viewHolder.unitPrice.setText("Unit price: "+unitPrice+" ETB");
        viewHolder.requestedAmount.setText("Total requested amount: "+Constants.getFormatedAmount(nearBy.getAmount()));
        double totalPrice = unitPrice*nearBy.getAmount();
        viewHolder.totalPrice.setText("Total price: "+Constants.getFormatedAmount(totalPrice)+" ETB");
        viewHolder.status.setText("Status: "+nearBy.getStatus());

        if (nearBy.getPayment()==null){
            viewHolder.paymentStatus.setText("Payment status: Not payed");
            viewHolder.payNow.setVisibility(View.VISIBLE);
        }else {
            viewHolder.paymentStatus.setText("Payment status: Payed");
            viewHolder.complain.setVisibility(View.VISIBLE);
            viewHolder.payNow.setVisibility(View.GONE);
        }

        viewHolder.complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                ((Button)dialog.findViewById(R.id.sendComplain))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((LinearLayout)dialog.findViewById(R.id.complainMainLayout))
                                        .setVisibility(View.GONE);
                                ((LinearLayout)dialog.findViewById(R.id.complainLoadingLayout))
                                        .setVisibility(View.VISIBLE);

                                String complainText = ((EditText)dialog.findViewById(R.id.complainInput))
                                        .getText().toString();
                                Complain complain = new Complain();
                                complain.setCard_request_id(nearBy.getId());
                                complain.setComplain(complainText);
                                complainViewModel.store(complain);
                            }
                        });
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
        if(response.isStatus()) {
            ((TextView)dialog.findViewById(R.id.successMessage))
                    .setVisibility(View.VISIBLE);
            ((LinearLayout)dialog.findViewById(R.id.complainLoadingLayout))
                    .setVisibility(View.GONE);
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            dialog.dismiss();
                        }
                    },
                    2200);
        }

    }


    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView requested_to,cardType,unitPrice,requestedAmount,totalPrice;
        private final TextView status,paymentStatus;
        private final Button payNow,complain;
        private LinearLayout successLayout,buttonLayout;
        private TextView successMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            requested_to = itemView.findViewById(R.id.requested_to);
            cardType = itemView.findViewById(R.id.requestedCardType);
            unitPrice = itemView.findViewById(R.id.unitPrice);
            requestedAmount = itemView.findViewById(R.id.requestedAmount);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            status = itemView.findViewById(R.id.status);
            paymentStatus = itemView.findViewById(R.id.paymentStatus);
            payNow = itemView.findViewById(R.id.payNow);
            complain = itemView.findViewById(R.id.complain);
            successLayout = itemView.findViewById(R.id.successLayout);
            buttonLayout = itemView.findViewById(R.id.buttonLayout);
            successMessage = itemView.findViewById(R.id.successMessage);


        }
    }
}
