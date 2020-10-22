package com.example.foragentss.auth.agents.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.adapter.SendCardPaymentTAdapter;
import com.example.foragentss.auth.commons.adapter.PaymentTypesAdapter;
import com.example.foragentss.auth.commons.fragments.CardRequestCardSelectionFragment;
import com.example.foragentss.auth.commons.fragments.PaymentTypesFragment;
import com.example.foragentss.auth.helpers.GridSpacingItemDecoration;
import com.example.foragentss.auth.models.CardPrice;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.PaymentType;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.view_model.CardPriceViewModel;
import com.example.foragentss.auth.view_model.PaymentTypeViewModel;
import com.example.foragentss.constants.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SendCardPaymentTransactionFragment extends Fragment {
    private User sendToUser;
    private CardPriceViewModel cardPriceViewModel;
    private ArrayList<CardPrice> cardPriceArrayList;

    private SendCardPaymentTAdapter adapter;
    private ArrayList<CardRequest> cardRequestArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayout loadingLayout,sendCardMainLayout,paymentTypeLayout,noEnougnCardLayout;
    private TextView totalPayment,paymentInfo;

    private ProgressBar paymentTypeLoader;
    private RecyclerView paymentTypesRecyclerView;
    private PaymentTypesAdapter paymentTypesAdapter;
    private ArrayList<PaymentType> paymentTypeArrayList = new ArrayList<>();
    private PaymentTypeViewModel paymentTypeViewModel;

    private TextView noEnougnCardText;
    private Button getCardsBTN;

    private LinearLayout seningLayout;
    public SendCardPaymentTransactionFragment() {
        // Required empty public constructor
    }

    public SendCardPaymentTransactionFragment(ArrayList<CardRequest> cardRequests,User user){
        cardRequestArrayList.addAll(cardRequests);
        this.sendToUser = user;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_send_card_payment_transaction, container, false);

        loadingLayout = view.findViewById(R.id.calculatingPayment);
        sendCardMainLayout = view.findViewById(R.id.sendCardMainLayout);

        totalPayment = view.findViewById(R.id.total_payment);
        paymentInfo = view.findViewById(R.id.paymentInfo);

        paymentTypeLayout = view.findViewById(R.id.payment_type_content_frame);
        noEnougnCardLayout = view.findViewById(R.id.noEnoughCardLayout);

        seningLayout = view.findViewById(R.id.sendingCardLayout);


        recyclerView = view.findViewById(R.id.sendTotalPaymentTransaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10,getContext()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        paymentTypesAdapter = new PaymentTypesAdapter(getContext(),this,paymentTypeArrayList,cardRequestArrayList,sendToUser);
        paymentTypeLoader = view.findViewById(R.id.paymentTypeLoader);
        paymentTypesRecyclerView =view.findViewById(R.id.paymentTypesRecyclerView);
        paymentTypesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        paymentTypesRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10,getContext()), true));
        paymentTypesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        paymentTypesRecyclerView.setAdapter(paymentTypesAdapter);

        paymentTypeViewModel = ViewModelProviders.of(getActivity()).get(PaymentTypeViewModel.class);
        paymentTypeViewModel.index().enqueue(new Callback<ArrayList<PaymentType>>() {
            @Override
            public void onResponse(Call<ArrayList<PaymentType>> call, Response<ArrayList<PaymentType>> response) {
                if (response.isSuccessful()){
                    paymentTypeArrayList.addAll(response.body());
                    paymentTypesAdapter.notifyDataSetChanged();
                    paymentTypeLoader.setVisibility(View.GONE);
                    paymentTypesRecyclerView.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ArrayList<PaymentType>> call, Throwable t) {

            }
        });

        cardPriceViewModel = ViewModelProviders.of(getActivity()).get(CardPriceViewModel.class);
        cardPriceViewModel.index().enqueue(new Callback<ArrayList<CardPrice>>() {
            @Override
            public void onResponse(Call<ArrayList<CardPrice>> call, Response<ArrayList<CardPrice>> response) {
                if (response.isSuccessful()){
                    loadingLayout.setVisibility(View.GONE);
                    sendCardMainLayout.setVisibility(View.VISIBLE);
                    cardPriceArrayList = response.body();
                    adapter = new SendCardPaymentTAdapter(getContext(),cardPriceArrayList.get(0).getPercentage_value(),cardRequestArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    totalPayment.setText("Total payment: "+Constants.getFormatedAmount(getTotalPayment(cardRequestArrayList,cardPriceArrayList.get(0).getPercentage_value()))+" ETB");
                    paymentInfo.setText("How do you accept the payment for the above cards from "+sendToUser.getFirst_name()
                            +". Select how "+sendToUser.getFirst_name()+" pay you from the following payment types.");

                }
            }

            @Override
            public void onFailure(Call<ArrayList<CardPrice>> call, Throwable t) {

            }
        });
        return view;
    }

    public void responseLayout(SuccessResponse response){
        seningLayout.setVisibility(View.GONE);
        noEnougnCardLayout.setVisibility(View.VISIBLE);
        noEnougnCardText = getView().findViewById(R.id.noEnoughCardText);
        getCardsBTN = getView().findViewById(R.id.getCards);
        noEnougnCardText.setText(response.getMessage());
        if (response.isStatus()){
            getCardsBTN.setVisibility(View.GONE);
            noEnougnCardText.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void showSendingOnProccess(){
        seningLayout.setVisibility(View.VISIBLE);
        paymentTypeLayout.setVisibility(View.GONE);
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
