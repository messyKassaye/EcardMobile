package com.example.foragentss.auth.commons.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.activities.CardRequestActivity;
import com.example.foragentss.auth.commons.adapter.ShowConnectionAdapter;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.ConnectionsData;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.CardRequestViewModel;

import java.util.ArrayList;

public class ShowConnectionFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShowConnectionAdapter adapter;
    private ArrayList<ConnectionsData> arrayList = new ArrayList<>();
    private CardRequestViewModel cardRequestViewModel;
    private ArrayList<CardRequest> cardRequestArrayList;
    private View view;
    private User selectedUserId;
    public ArrayList<Integer> sendedCardRequestId = new ArrayList<>();
    public ShowConnectionFragment() {
        // Required empty public constructor
    }

    public ShowConnectionFragment(ArrayList<CardRequest> cardRequests){
            this.cardRequestArrayList = cardRequests;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_show_connection, container, false);

        cardRequestViewModel = ViewModelProviders.of(getActivity()).get(CardRequestViewModel.class);
        cardRequestViewModel.storeResponse().observe(getActivity(),this::consumeResponse);

        recyclerView = view.findViewById(R.id.connectionsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ShowConnectionAdapter(getActivity(),this,arrayList);
        recyclerView.setAdapter(adapter);


        return view;
    }

    public void sendTo(ConnectionsData user){
        selectedUserId = user.getUser().get(0);

        view.findViewById(R.id.cardRequestMainLayout).setVisibility(View.GONE);
        view.findViewById(R.id.cardRequestSendingLayout).setVisibility(View.VISIBLE);
        CardRequest cardRequest = cardRequestArrayList.get(0);
        cardRequest.setCompany_agent_id(user.getUser().get(0).getId());
        sendRecursively(0);
    }

    public void sendRecursively(int index){
        CardRequest cardRequest = cardRequestArrayList.get(index);
        cardRequest.setCompany_agent_id(selectedUserId.getId());
        cardRequest.setIndex(index);
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
            if (response.getIndex()<cardRequestArrayList.size()){
                sendRecursively(response.getIndex());
            }
            if (response.getIndex()==cardRequestArrayList.size()){
                CardRequestActivity cardRequestActivity =(CardRequestActivity)getActivity();
                cardRequestActivity.showPaymentTransaction(selectedUserId,cardRequestArrayList,sendedCardRequestId);
            }
        }

    }

}
