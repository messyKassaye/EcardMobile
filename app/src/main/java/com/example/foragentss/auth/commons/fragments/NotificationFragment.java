package com.example.foragentss.auth.commons.fragments;

import android.content.Context;
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
import android.widget.TextView;

import com.example.foragentss.R;
import com.example.foragentss.auth.commons.adapter.NotificationAdapter;
import com.example.foragentss.auth.commons.adapter.OldestNotificationAdapter;
import com.example.foragentss.auth.models.Notification;
import com.example.foragentss.auth.response.NotificationResponse;
import com.example.foragentss.auth.view_model.NotificationViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    private ArrayList<Notification> notifications;
    private RecyclerView recyclerView,olderNotificationRecyclerView;
    private NotificationAdapter adapter;
    private NotificationViewModel notificationViewModel;

    private ArrayList<Notification> notificationArrayList = new ArrayList<>();
    private OldestNotificationAdapter oldestNotificationAdapter;
    public NotificationFragment() {
        // Required empty public constructor
    }

    public NotificationFragment(ArrayList<Notification> notifications){
        this.notifications = notifications;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notification, container, false);

        notificationViewModel = ViewModelProviders.of(getActivity()).get(NotificationViewModel.class);

        if (notifications.size()<=0){
            ((TextView)view.findViewById(R.id.noNewNotification)).setVisibility(View.VISIBLE);
            ((TextView)view.findViewById(R.id.noNewNotification)).setText("No new notification for you");
        }else {
            adapter = new NotificationAdapter(getActivity(),notifications);
            recyclerView = view.findViewById(R.id.newNotificationRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        oldestNotificationAdapter = new OldestNotificationAdapter(getActivity(),notificationArrayList);
        olderNotificationRecyclerView = view.findViewById(R.id.oldestNotificationRecyclerView);
        olderNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        olderNotificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        olderNotificationRecyclerView.setAdapter(oldestNotificationAdapter);

        notificationViewModel.index().enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                 view.findViewById(R.id.notificationLoader).setVisibility(View.GONE);
                 olderNotificationRecyclerView.setVisibility(View.VISIBLE);
                if (response.isSuccessful()){
                    notificationArrayList.addAll(response.body().getData());
                    oldestNotificationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {

            }
        });
        updateNotification();

        return view;
    }

    public void updateNotification(){
        for (int i=0;i<notifications.size();i++){
            notificationViewModel.update(notifications.get(i).getId(),1);
        }
    }

}
