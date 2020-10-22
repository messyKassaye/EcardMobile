package com.example.foragentss.auth.retailers.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.DownloadCard;
import com.example.foragentss.auth.models.DownloadData;
import com.example.foragentss.auth.view_model.DownloadCardViewModel;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.rooms.entity.Card;
import com.example.foragentss.rooms.view_model.CardsRoomViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DownloadCardsFragment extends Fragment implements View.OnClickListener {

    private int card_type_id;
    private EditText amountEditText;
    private Button downloadBTN;
    private LinearLayout downloadLoadingLayout;
    private DownloadCardViewModel downloadCardViewModel;
    private CardsRoomViewModel cardsRoomViewModel;
    public DownloadCardsFragment() {
        // Required empty public constructor
    }

    public DownloadCardsFragment(String card_type_id){
        this.card_type_id = Integer.parseInt(card_type_id);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_download_cards, container, false);

        downloadCardViewModel = ViewModelProviders.of(getActivity()).get(DownloadCardViewModel.class);

        cardsRoomViewModel = ViewModelProviders.of(getActivity()).get(CardsRoomViewModel.class);

        downloadLoadingLayout = view.findViewById(R.id.downloadingLayout);
        ((TextView)view.findViewById(R.id.card_type_info)).setText("Now you are downloading "+Constants.getCardTypeValue(card_type_id)+" Birr card");
        amountEditText = view.findViewById(R.id.download_amount);
        downloadBTN = view.findViewById(R.id.downloadBTN);
        downloadBTN.setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onClick(View view) {
        String cardAmount = amountEditText.getText().toString();
        if (cardAmount.equalsIgnoreCase("")){
            ((TextView)getView().findViewById(R.id.downloadErrorShower)).setText("Please enter the amount of "
                    +Constants.getCardTypeValue(card_type_id)+" Birr card you want to download");
        }else {
            downloadLoadingLayout.setVisibility(View.VISIBLE);
            downloadBTN.setVisibility(View.GONE);
            downloadCardViewModel.show(card_type_id,Integer.valueOf(cardAmount))
                    .enqueue(new Callback<DownloadData>() {
                        @Override
                        public void onResponse(Call<DownloadData> call, Response<DownloadData> response) {
                            if (response.isSuccessful()){
                                Toast.makeText(getContext(),""+response.body().getData().size(),Toast.LENGTH_LONG).show();
                                if (response.body().isStatus()){
                                    storeLocally(response.body());
                                }else {
                                    downloadLoadingLayout.setVisibility(View.GONE);
                                    downloadBTN.setVisibility(View.VISIBLE);
                                    ((TextView)getView().findViewById(R.id.downloadErrorShower))
                                            .setText(response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<DownloadData> call, Throwable t) {

                        }
                    });
        }

    }

    public void storeLocally(DownloadData downloadData){
        ArrayList<DownloadCard> downloadCards = downloadData.getData();
        for (int i=0;i<downloadCards.size();i++){
            Card card = new Card();
            card.setCard_type_id(card_type_id);
            card.setNumber(downloadCards.get(i).getCard_number());
            card.setStatus("not_soled");
            card.setServer_card_id(downloadCards.get(i).getId());
            cardsRoomViewModel.store(card);

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            downloadLoadingLayout.setVisibility(View.GONE);
                            ((TextView)getView().findViewById(R.id.downloadErrorShower))
                                    .setText("Your download is completed. No you can sell and increase your income");
                            ((TextView)getView().findViewById(R.id.downloadErrorShower))
                                    .setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                    },
                    2000);
        }
    }
}
