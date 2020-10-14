package com.example.foragentss.auth.commons.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.agents.activities.FloatingButtonActivity;
import com.example.foragentss.auth.commons.adapter.ShowConnectionAdapter;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.ConnectionsData;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.ConnectionsResponse;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.CardRequestViewModel;
import com.example.foragentss.auth.view_model.ConnectViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowConnectionFragment extends Fragment {
    private RecyclerView recyclerView;
    private ShowConnectionAdapter adapter;
    private ArrayList<ConnectionsData> arrayList = new ArrayList<>();
    private ConnectViewModel connectViewModel;
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

        connectViewModel = ViewModelProviders.of(getActivity()).get(ConnectViewModel.class);
        connectViewModel.show("accepted")
                .enqueue(new Callback<ConnectionsResponse>() {
                    @Override
                    public void onResponse(Call<ConnectionsResponse> call, Response<ConnectionsResponse> response) {
                        view.findViewById(R.id.cardRequestSelectLoadingLayout).setVisibility(View.GONE);
                        if (response.isSuccessful()&&response.body().getData().size()>0){
                            view.findViewById(R.id.cardRequestMainLayout)
                                    .setVisibility(View.VISIBLE);
                            arrayList.addAll(response.body().getData());
                            adapter.notifyDataSetChanged();

                        }else {
                            view.findViewById(R.id.noConnectionLayout).setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ConnectionsResponse> call, Throwable t) {

                    }
                });
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
                FloatingButtonActivity floatingButtonActivity=(FloatingButtonActivity)getActivity();
                floatingButtonActivity.showPaymentTransaction(selectedUserId,cardRequestArrayList,sendedCardRequestId);
            }
        }

    }

}
