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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.Bank;
import com.example.foragentss.auth.models.BankAccount;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.BankAccountViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class BanksAdapter extends RecyclerView.Adapter<BanksAdapter.ViewHolder> {
    private ArrayList<Bank> banks;
    private Context context;
    private Dialog dialog;
    private BankAccountViewModel bankAccountViewModel;
    private ViewHolder viewHolders;
    private String accountNumber;
    private String holderName;
    private int account_id=0;
    public BanksAdapter(Context context,ArrayList<Bank> placeList) {
        this.banks = placeList;
        this.context = context;


        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.set_bank_account_dialog);

    }

    @NonNull
    @Override
    public BanksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_bank_account_layout, viewGroup, false);
        return new BanksAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BanksAdapter.ViewHolder viewHolder, int i) {
        Bank nearBy = banks.get(i);

        if (nearBy.getMe().size()>0){
            account_id = nearBy.getMe().get(0).getId();
        }
        viewHolders = viewHolder;

        bankAccountViewModel = ViewModelProviders.of((AppCompatActivity)context).get(BankAccountViewModel.class);
        bankAccountViewModel.storeResponse().observe((AppCompatActivity)context,this::consumeResponse);

        viewHolder.bankName.setText(nearBy.getName());
        viewHolder.avatar.setText(""+nearBy.getName().charAt(0));
        viewHolder.numberOfUser.setText("Total user: "+nearBy.getTotal_user());
        if (nearBy.getMe().size()<=0){
            viewHolder.accountNotset.setVisibility(View.VISIBLE);
            viewHolder.accountLayout.setVisibility(View.GONE);
        }else {
            viewHolder.accountNumber.setText("Account number: "+
                    nearBy.getMe().get(0).getAccount_number());
            viewHolder.holderName.setText("Holder name: "+nearBy.getMe().get(0).getHolder_full_name());
        }

        viewHolder.setAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((TextView)dialog.findViewById(R.id.headerTitle))
                        .setText("Set your account for "+nearBy.getName());
                dialog.show();
            }
        });

        ((Button)dialog.findViewById(R.id.setMyAccountBtn))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((LinearLayout)dialog.findViewById(R.id.bankAccountLoadingLayout))
                                .setVisibility(View.VISIBLE);
                        ((LinearLayout)dialog.findViewById(R.id.bankAccountMainLayout))
                                .setVisibility(View.GONE);

                         accountNumber = ((EditText)dialog.findViewById(R.id.accountNumberInput))
                                .getText().toString();
                         holderName = ((EditText)dialog.findViewById(R.id.holderNameInput))
                                .getText().toString();

                        BankAccount bankAccount = new BankAccount();
                        bankAccount.setAccount_number(accountNumber);
                        bankAccount.setHolder_full_name(holderName);
                        bankAccount.setBank_id(nearBy.getId());
                        bankAccountViewModel.store(bankAccount);

                    }
                });


         viewHolder.edit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

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
            viewHolders.accountLayout.setVisibility(View.VISIBLE);
            viewHolders.accountNotset.setVisibility(View.GONE);
            viewHolders.accountNumber.setText("Account number: "+accountNumber);
            viewHolders.holderName.setText("Account holder name: "+holderName);

            ((TextView)dialog.findViewById(R.id.bankAccountSuccessMessage))
                    .setVisibility(View.VISIBLE);
            ((LinearLayout)dialog.findViewById(R.id.bankAccountMainLayout))
                    .setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return banks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView bankName,avatar,numberOfUser,accountNumber,holderName;
        private LinearLayout accountNotset;
        private RelativeLayout accountLayout;
        private Button setAccount;
        private ImageView edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bankName = itemView.findViewById(R.id.bankName);
            avatar = itemView.findViewById(R.id.bankAvatar);
            numberOfUser = itemView.findViewById(R.id.numberOfUser);
            accountNotset = itemView.findViewById(R.id.noBankAccountSet);
            accountLayout = itemView.findViewById(R.id.bankAccountMainDashboardLayout);
            setAccount = itemView.findViewById(R.id.setAccount);
            accountNumber = itemView.findViewById(R.id.accountNumberText);
            holderName = itemView.findViewById(R.id.accountHolderNameText);
            edit = itemView.findViewById(R.id.editBankAccount);
        }
    }
}