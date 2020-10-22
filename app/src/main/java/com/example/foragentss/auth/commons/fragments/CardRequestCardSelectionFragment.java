package com.example.foragentss.auth.commons.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.activities.CardRequestActivity;
import com.example.foragentss.auth.agents.activities.SendCardActivity;
import com.example.foragentss.auth.commons.adapter.SelectCardTypeAdapter;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.CardType;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.CardRequestViewModel;
import com.example.foragentss.auth.view_model.CardTypeViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CardRequestCardSelectionFragment extends Fragment implements View.OnClickListener {
    private CardTypeViewModel cardTypeViewModel;
    private Button selectCardBtn;

    private ArrayList<CardType> cardTypeArrayList = new ArrayList<>();
    private SelectCardTypeAdapter adapter;
    private RecyclerView recyclerView;
    private Dialog dialog;
    private TextInputLayout fiveBirrLayout;
    private Button sendMyRequestBTN;
    private ArrayList<Integer> requestSize = new ArrayList<>();
    private ArrayList<Integer> minimizeSize = new ArrayList<>();
    private View view;
    private TextView errorShower;
    private LinearLayout mainLayout,loadingLayout;
    private ArrayList<CardRequest> cardRequestList = new ArrayList<>();
    private User user;
    private CardRequestViewModel cardRequestViewModel;
    public ArrayList<Integer> sendedCardRequestId = new ArrayList<>();
    private String tag;
    public CardRequestCardSelectionFragment() {
        // Required empty public constructor
    }

    public CardRequestCardSelectionFragment(User userId, String tag){
        this.user =userId;
        this.tag = tag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_card_rquest_card_selection, container, false);

        cardRequestViewModel = ViewModelProviders.of(getActivity()).get(CardRequestViewModel.class);
        cardRequestViewModel.storeResponse().observe(getActivity(),this::consumeResponse);

        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.select_card_dialog_layout);

        adapter = new SelectCardTypeAdapter(getContext(),this,cardTypeArrayList);
        recyclerView = dialog.findViewById(R.id.cardTypeListView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        selectCardBtn = view.findViewById(R.id.selectCard);
        cardTypeViewModel = ViewModelProviders.of(this).get(CardTypeViewModel.class);
        cardTypeViewModel.index().enqueue(new Callback<ArrayList<CardType>>() {
            @Override
            public void onResponse(Call<ArrayList<CardType>> call, Response<ArrayList<CardType>> response) {
                if (response.body().size()>0){
                    cardTypeArrayList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CardType>> call, Throwable t) {

            }
        });



        selectCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        mainLayout = view.findViewById(R.id.cardSelectionMainLayout);
        loadingLayout = view.findViewById(R.id.cardSelectLoadingLayout);

        sendMyRequestBTN = view.findViewById(R.id.senMyRequestBTN);
        fiveBirrLayout = view.findViewById(R.id.fiveBirrTextInput);
        int width = (int)(getResources().getDisplayMetrics().widthPixels*0.80);
        ViewGroup.LayoutParams layoutParams = fiveBirrLayout.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = width;
        fiveBirrLayout.setLayoutParams(layoutParams);
        view.findViewById(R.id.tenBirrTextInput).setLayoutParams(layoutParams);
        view.findViewById(R.id.fifteenTextInput).setLayoutParams(layoutParams);
        view.findViewById(R.id.twentyFiveBirrTextInput).setLayoutParams(layoutParams);
        view.findViewById(R.id.fiftyBirrTextInput).setLayoutParams(layoutParams);
        view.findViewById(R.id.hundredBirrTextInput).setLayoutParams(layoutParams);

        view.findViewById(R.id.five_birr_card_close).setOnClickListener(this::onClick);
        view.findViewById(R.id.ten_birr_card_close).setOnClickListener(this::onClick);
        view.findViewById(R.id.fifteen_birr_card_close).setOnClickListener(this::onClick);
        view.findViewById(R.id.twenty_five_birr_card_close).setOnClickListener(this::onClick);
        view.findViewById(R.id.fifty_birr_card_close).setOnClickListener(this::onClick);
        view.findViewById(R.id.hundred_birr_card_close).setOnClickListener(this::onClick);

        errorShower = view.findViewById(R.id.errorShower);
        sendMyRequestBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                    EditText fiveBirrEditText = view.findViewById(R.id.fiveBirrEditText);
                    EditText tenBirrEditText = view.findViewById(R.id.tenBirrEditText);
                    EditText fifteenBirrEditText = view.findViewById(R.id.fifteenBirrEditText);
                    EditText twentyFiveBirrEditText = view.findViewById(R.id.twentyFiveBirrEditText);
                    EditText fiftyEditText = view.findViewById(R.id.fiftyBirrEditText);
                    EditText hundredBirrEditText = view.findViewById(R.id.hundredBirrEditText);

                    if (requestSize.contains(1)&&fiveBirrEditText.getText().toString().equals("")){
                        errorShower.setVisibility(View.VISIBLE);
                        errorShower.setText("Please enter 5 birr card amount");
                    }else if(requestSize.contains(2)&&tenBirrEditText.getText().toString().equals("")){
                        errorShower.setVisibility(View.VISIBLE);
                        errorShower.setText("Please enter 10 birr card amount");
                    }else if (requestSize.contains(3)&&fifteenBirrEditText.getText().toString().equals("")){
                        errorShower.setVisibility(View.VISIBLE);
                        errorShower.setText("Please enter 15 birr card amount");
                    }else if (requestSize.contains(4)&&twentyFiveBirrEditText.getText().toString().equals("")){
                        errorShower.setVisibility(View.VISIBLE);
                        errorShower.setText("Please enter 25 birr card amount");
                    }else if (requestSize.contains(5)&&fiftyEditText.getText().toString().equals("")){
                        errorShower.setVisibility(View.VISIBLE);
                        errorShower.setText("Please enter 50 birr card amount");
                    }else if (requestSize.contains(6)&&hundredBirrEditText.getText().toString().equals("")){
                        errorShower.setVisibility(View.VISIBLE);
                        errorShower.setText("Please enter 100 birr card amount");
                    }else {
                        if (requestSize.contains(1)){
                            CardRequest fiveBirrCardRequest = new CardRequest();
                            fiveBirrCardRequest.setCard_type_id(1);
                            fiveBirrCardRequest.setAmount(Integer.parseInt(fiveBirrEditText.getText().toString()));
                            cardRequestList.add(fiveBirrCardRequest);
                        }
                        if (requestSize.contains(2)){
                            CardRequest tenBirrCardRequest = new CardRequest();
                            tenBirrCardRequest.setCard_type_id(2);
                            tenBirrCardRequest.setAmount(Integer.parseInt(tenBirrEditText.getText().toString()));
                            cardRequestList.add(tenBirrCardRequest);
                        }
                        if (requestSize.contains(3)){
                            CardRequest fifteenBirrCardRequest = new CardRequest();
                            fifteenBirrCardRequest.setCard_type_id(3);
                            fifteenBirrCardRequest.setAmount(Integer.parseInt(fifteenBirrEditText.getText().toString()));
                            cardRequestList.add(fifteenBirrCardRequest);
                        }
                        if (requestSize.contains(4)){
                            CardRequest twentyFiveBirrCardRequest = new CardRequest();
                            twentyFiveBirrCardRequest.setCard_type_id(4);
                            twentyFiveBirrCardRequest.setAmount(Integer.parseInt(twentyFiveBirrEditText.getText().toString()));
                            cardRequestList.add(twentyFiveBirrCardRequest);
                        }
                        if (requestSize.contains(5)){
                            CardRequest fiftyBirrCardRequest = new CardRequest();
                            fiftyBirrCardRequest.setCard_type_id(5);
                            fiftyBirrCardRequest.setAmount(Integer.parseInt(fiftyEditText.getText().toString()));
                            cardRequestList.add(fiftyBirrCardRequest);
                        }
                        if (requestSize.contains(6)){
                            CardRequest hundredBirrCardRequest = new CardRequest();
                            hundredBirrCardRequest.setCard_type_id(6);
                            hundredBirrCardRequest.setAmount(Integer.parseInt(hundredBirrEditText.getText().toString()));
                            cardRequestList.add(hundredBirrCardRequest);
                        }

                        sendCardRequest(cardRequestList);
                    }
            }
        });
        return view;
    }

    public void sendCardRequest(ArrayList<CardRequest> cardRequests){
        if (tag.equalsIgnoreCase("send_card_fragment")){
            SendCardActivity sendCardActivity =(SendCardActivity)getActivity();
            sendCardActivity.proceed(cardRequests);

        }else {
            mainLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);

            CardRequest cardRequest = cardRequestList.get(0);
            cardRequest.setCompany_agent_id(user.getId());
            sendRecursively(0);
        }
    }

    public void sendRecursively(int index){
        CardRequest cardRequest = cardRequestList.get(index);
        cardRequest.setCompany_agent_id(user.getId());
        cardRequest.setIndex(index);
        cardRequest.setPayment_type_id(1);
        cardRequestViewModel.store(cardRequest);

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
            sendedCardRequestId.add(response.getCard_request().getId());
            if (response.getIndex()<cardRequestList.size()){
                sendRecursively(response.getIndex());
            }
            if (response.getIndex()==cardRequestList.size()){
                CardRequestActivity cardRequestActivity =(CardRequestActivity)getActivity();
                cardRequestActivity.showPaymentTransaction(user,cardRequestList,sendedCardRequestId);
            }
        }

    }

    public void addCardType(CardType cardType){
        dialog.dismiss();
        view.findViewById(R.id.sendRequestBtn).setVisibility(View.VISIBLE);
        requestSize.add(cardType.getId());
        switch (cardType.getId()){
            case 1:
                view.findViewById(R.id.fiveBirrCardLayout).setVisibility(View.VISIBLE);
                break;
            case 2:
                view.findViewById(R.id.tenBirrCardLayout).setVisibility(View.VISIBLE);
                break;
            case 3:
                view.findViewById(R.id.fifteenBirrCardLayout).setVisibility(View.VISIBLE);
                break;
            case 4:
                view.findViewById(R.id.twentyFiveBirrCardLayout).setVisibility(View.VISIBLE);
                break;
            case 5:
                view.findViewById(R.id.fiftyBirrCardLayout).setVisibility(View.VISIBLE);
                break;
            case 6:
                view.findViewById(R.id.hundredBirrCardLayout).setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public void onClick(View mainView) {
        minimizeSize.add(1);
        refresh();
        switch (mainView.getId()){
            case R.id.five_birr_card_close:
                view.findViewById(R.id.fiveBirrCardLayout).setVisibility(View.GONE);
                break;
            case R.id.ten_birr_card_close:
                view.findViewById(R.id.tenBirrCardLayout).setVisibility(View.GONE);
                break;
            case R.id.fifteen_birr_card_close:
                view.findViewById(R.id.fifteenBirrCardLayout).setVisibility(View.GONE);
                break;
            case R.id.twenty_five_birr_card_close:
                view.findViewById(R.id.twentyFiveBirrCardLayout).setVisibility(View.GONE);
                break;
            case R.id.fifty_birr_card_close:
                view.findViewById(R.id.fiftyBirrCardLayout).setVisibility(View.GONE);
                break;
            case R.id.hundred_birr_card_close:
                view.findViewById(R.id.hundredBirrCardLayout).setVisibility(View.GONE);
                break;

        }
    }

    public void refresh(){
        if(requestSize.size()==minimizeSize.size()){
            view.findViewById(R.id.sendRequestBtn).setVisibility(View.GONE);
            requestSize.clear();
            minimizeSize.clear();
        }
    }

}
