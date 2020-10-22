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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.BankAccount;
import com.example.foragentss.auth.models.CardRequestPayment;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.CardRequestPaymentViewModel;

import java.util.ArrayList;

public class BankAccountAdapter extends RecyclerView.Adapter<BankAccountAdapter.ViewHolder> {
    private ArrayList<BankAccount> bankAccounts;
    private Context context;
    private ArrayList<Integer> sendedCardRequest;
    private Dialog dialog;
    private int bankId;
    private String referenceNumber;
    private CardRequestPaymentViewModel cardRequestPaymentViewModel;
    public BankAccountAdapter(Context context,ArrayList<BankAccount> placeList
            ,ArrayList<Integer> sendedCardRequest) {
        this.bankAccounts = placeList;
        this.context = context;
        this.sendedCardRequest = sendedCardRequest;

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.bank_transfer_refernce_number_layout);
    }

    @NonNull
    @Override
    public BankAccountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bank_account_layout, viewGroup, false);
        return new BankAccountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankAccountAdapter.ViewHolder viewHolder, int i) {
        BankAccount nearBy = bankAccounts.get(i);
        viewHolder.bankName.setText(nearBy.getBank().getName());
        viewHolder.accountNumber.setText("Account number: " + nearBy.getAccount_number());
        viewHolder.accountHolderName.setText("Account holder name: " + nearBy.getHolder_full_name());

        cardRequestPaymentViewModel = ViewModelProviders.of((AppCompatActivity)context).get(CardRequestPaymentViewModel.class);
        cardRequestPaymentViewModel.storeResponse().observe((AppCompatActivity)context,this::consumeResponse);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Button)dialog.findViewById(R.id.sendReferenceBtn))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ((LinearLayout)dialog.findViewById(R.id.referenceLoadingLayout))
                                        .setVisibility(View.VISIBLE);
                                ((LinearLayout)dialog.findViewById(R.id.referenceMainLayout))
                                        .setVisibility(View.GONE);

                                String referenceNumber = ((EditText)dialog.findViewById(R.id.referenceNumber))
                                        .getText().toString();
                                sendReferenceNumber(nearBy.getBank().getId(),referenceNumber);
                            }
                        });
                dialog.show();
            }
        });
    }

    public void sendReferenceNumber(int bankId,String referenceNumber){
        this.bankId = bankId;
        this.referenceNumber = referenceNumber;
        sendRecursively(0);
    }

    public void sendRecursively(int index){
        CardRequestPayment cardRequestPayment = new CardRequestPayment();
        cardRequestPayment.setBank_id(bankId);
        cardRequestPayment.setReference_number(referenceNumber);
        cardRequestPayment.setCard_request_id(sendedCardRequest.get(index));
        cardRequestPayment.setIndex(index);
        cardRequestPaymentViewModel.store(cardRequestPayment);

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
        if(response.isStatus()) {
            if (response.getIndex()<sendedCardRequest.size()){
                sendRecursively(response.getIndex());
            }
            if(response.getIndex()==sendedCardRequest.size()){
                ((LinearLayout)dialog.findViewById(R.id.referenceNumberSuccessLayout))
                        .setVisibility(View.VISIBLE);
                ((LinearLayout)dialog.findViewById(R.id.referenceLoadingLayout))
                        .setVisibility(View.GONE);
                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                dialog.dismiss();
                            }
                        },
                        2000);
            }
        }

    }
    @Override
    public int getItemCount() {
        return bankAccounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView bankName, accountNumber,accountHolderName;
        private final CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bankName = itemView.findViewById(R.id.bankName);
            accountNumber = itemView.findViewById(R.id.account_number);
            accountHolderName = itemView.findViewById(R.id.holder_name);
            cardView = itemView.findViewById(R.id.bankAccountCardView);

        }
    }
}