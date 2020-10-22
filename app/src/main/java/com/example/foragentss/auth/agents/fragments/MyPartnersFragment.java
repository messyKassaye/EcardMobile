package com.example.foragentss.auth.agents.fragments;

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
import android.widget.ProgressBar;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.adapter.PartnersAdapter;
import com.example.foragentss.auth.helpers.GridSpacingItemDecoration;
import com.example.foragentss.auth.models.ConnectionsData;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.ConnectionsResponse;
import com.example.foragentss.auth.view_model.MyPartnersViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPartnersFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayout noPartnersLayout;
    private ArrayList<User> userArrayList = new ArrayList<>();
    private PartnersAdapter adapter;
    private MyPartnersViewModel myPartnersViewModel;
    public MyPartnersFragment() {
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
        View view= inflater.inflate(R.layout.fragment_my_partners, container, false);

        progressBar = view.findViewById(R.id.partnersLoader);
        noPartnersLayout = view.findViewById(R.id.noPartnersLayout);

        adapter = new PartnersAdapter(getContext(),"Partner",userArrayList);
        recyclerView = view.findViewById(R.id.partnersRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, GridSpacingItemDecoration.dpToPx(10,getContext()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        myPartnersViewModel = ViewModelProviders.of(getActivity()).get(MyPartnersViewModel.class);
        myPartnersViewModel.show(1)
                .enqueue(new Callback<ConnectionsResponse>() {
                    @Override
                    public void onResponse(Call<ConnectionsResponse> call, Response<ConnectionsResponse> response) {
                        if (response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            List<ConnectionsData> partners = response.body().getData();
                            for (int i=0;i<partners.size();i++){
                                userArrayList.add(partners.get(i).getUser().get(0));
                            }

                            if (userArrayList.size()<=0){
                                noPartnersLayout.setVisibility(View.VISIBLE);
                            }else {
                                recyclerView.setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ConnectionsResponse> call, Throwable t) {

                    }
                });


        return view;
    }


}
