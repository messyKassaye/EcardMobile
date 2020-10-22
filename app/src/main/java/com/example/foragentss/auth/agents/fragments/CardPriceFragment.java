package com.example.foragentss.auth.agents.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.TextView;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.models.CardPrice;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.CardPriceViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CardPriceFragment extends Fragment implements View.OnClickListener {
    private CardPriceViewModel cardPriceViewModel;
    private Dialog dialog;
    private double percentages;
    private String updatePercentage,sendPercentage;
    private int id;
    private View view;

    public CardPriceFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_card_price, container, false);

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);

        dialog.setContentView(R.layout.card_price_dialog);

        cardPriceViewModel = ViewModelProviders.of(getActivity())
                .get(CardPriceViewModel.class);
        cardPriceViewModel.storeResponse().observe(getActivity(),this::consumeResponse);

        cardPriceViewModel.index().enqueue(new Callback<ArrayList<CardPrice>>() {
            @Override
            public void onResponse(Call<ArrayList<CardPrice>> call, Response<ArrayList<CardPrice>> response) {
                if (response.isSuccessful()){
                    if (response.body().size()<=0){
                        view.findViewById(R.id.priceNotSet).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.priceMainLayout).setVisibility(View.GONE);
                    }else {
                        id =response.body().get(0).getId();
                        percentages = response.body().get(0).getPercentage();
                        view.findViewById(R.id.priceNotSet).setVisibility(View.GONE);
                        view.findViewById(R.id.priceMainLayout).setVisibility(View.VISIBLE);

                        ((TextView)view.findViewById(R.id.cardPriceValue))
                                .setText(response.body().get(0).getPercentage()+" %");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CardPrice>> call, Throwable t) {

            }
        });

        view.findViewById(R.id.setCardPriceNow).setOnClickListener(this::onClick);
        view.findViewById(R.id.updatePrice).setOnClickListener(this::onClick);

        dialog.findViewById(R.id.sendMyPrice).setOnClickListener(this::onClick);
        dialog.findViewById(R.id.updateCardPrice).setOnClickListener(this::onClick);
        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setCardPriceNow:
                dialog.show();
                break;
            case R.id.sendMyPrice:
                sendPrice();
                break;
        }
    }

    public void sendPrice(){
        dialog.findViewById(R.id.cardPriceLoadingLayout).setVisibility(View.VISIBLE);
        dialog.findViewById(R.id.cardPriceMainLayout).setVisibility(View.GONE);
        sendPercentage = ((EditText)dialog.findViewById(R.id.cardPriceInput)).getText()
                .toString();
        CardPrice cardPrice = new CardPrice();
        cardPrice.setPercentage(Double.parseDouble(sendPercentage));
        cardPriceViewModel.store(cardPrice);
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
            view.findViewById(R.id.priceNotSet).setVisibility(View.GONE);
            view.findViewById(R.id.priceMainLayout).setVisibility(View.VISIBLE);
            ((TextView)view.findViewById(R.id.cardPriceValue))
                    .setText(""+sendPercentage);
            dialog.dismiss();
        }

    }
}
