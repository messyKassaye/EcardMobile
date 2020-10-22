package com.example.foragentss.auth.commons.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.adapter.BankAccountAdapter;
import com.example.foragentss.auth.commons.adapter.TotalPaymentAdapter;
import com.example.foragentss.auth.helpers.GridSpacingItemDecoration;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.ConnectionsData;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.constants.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class PaymentTransactionFragment extends Fragment {

    private ConnectionsData requestedUser;
    private ArrayList<CardRequest> cardRequestArrayList;
    private User selectedUser;
    private RecyclerView totalPaymentRecyclerView,bankAccountRecyclerView;
    private BankAccountAdapter bankAccountAdapter;
    private TotalPaymentAdapter totalPaymentAdapter;
    private ArrayList<Integer> sendedCardRequestId;
    public PaymentTransactionFragment() {
        // Required empty public constructor
    }

    public PaymentTransactionFragment(User selectedUser, ArrayList<CardRequest> cardRequests,ArrayList<Integer> sendedCardRequest){
        this.selectedUser = selectedUser;
        this.cardRequestArrayList = cardRequests;
        this.sendedCardRequestId = sendedCardRequest;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_payment_transaction, container, false);
        TextView infoTextView =view.findViewById(R.id.paymentIndo);
        infoTextView.setText("You card request has been send successfully");

        double price = selectedUser.getCard_price().get(0).getPercentage_value();
        totalPaymentAdapter = new TotalPaymentAdapter(getContext(),price,cardRequestArrayList);
        totalPaymentRecyclerView = view.findViewById(R.id.totalPaymentRecyclerView);
        totalPaymentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        totalPaymentRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10,getContext()), true));
        totalPaymentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        totalPaymentRecyclerView.setAdapter(totalPaymentAdapter);
        totalPaymentAdapter.notifyDataSetChanged();

        double totalPayment = getTotalPayment(cardRequestArrayList,price);
        ((TextView)view.findViewById(R.id.totalPayment)).setText("Total payment: "+Constants.getFormatedAmount(totalPayment)+" ETB");

        ((TextView)view.findViewById(R.id.bankAccountInfo)).setText("You can transfer your payment to" +
                " "+selectedUser.getFirst_name()+" Via the following banks of his/her account");

        bankAccountAdapter = new BankAccountAdapter(getContext(),selectedUser.getBank_account(),sendedCardRequestId);
        bankAccountRecyclerView = view.findViewById(R.id.bankAccountsRecyclerView);
        bankAccountRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        bankAccountRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10,getContext()), true));
        bankAccountRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bankAccountRecyclerView.setAdapter(bankAccountAdapter);
        bankAccountAdapter.notifyDataSetChanged();

        ((Button)view.findViewById(R.id.paymentDone))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((AppCompatActivity)getContext()).finish();
                    }
                });

        return view;
    }

    public double getTotalPayment(ArrayList<CardRequest> cardRequests,double price){
        double totalPrice=0.0;
        for (int i=0;i<cardRequests.size();i++){
            double unitPrice = Constants.getCardTypeValue(cardRequests.get(i).getCard_type_id())*price;
            totalPrice += cardRequests.get(i).getAmount()*unitPrice;
        }
        return totalPrice;
    }

}
