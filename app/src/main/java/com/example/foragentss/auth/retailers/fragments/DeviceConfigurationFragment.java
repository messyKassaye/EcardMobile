package com.example.foragentss.auth.retailers.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.Attribute;
import com.example.foragentss.auth.models.CardType;
import com.example.foragentss.auth.models.Device;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retailers.RetailersDashboard;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.CardTypeViewModel;
import com.example.foragentss.auth.view_model.DeviceViewModel;
import com.example.foragentss.auth.view_model.MeViewModel;
import com.example.foragentss.rooms.entity.CardTypeRoom;
import com.example.foragentss.rooms.entity.DeviceRoom;
import com.example.foragentss.rooms.entity.UserRoom;
import com.example.foragentss.rooms.view_model.CardTypeRoomViewModel;
import com.example.foragentss.rooms.view_model.DeviceRoomViewModel;
import com.example.foragentss.rooms.view_model.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceConfigurationFragment extends Fragment implements View.OnClickListener {
    private DeviceViewModel deviceViewModel;
    private DeviceRoomViewModel deviceRoomViewModel;
    private Button setMyDevice;
    private View view;
    private CardTypeViewModel cardTypeViewModel;
    private CardTypeRoomViewModel cardTypeRoomViewModel;
    private MeViewModel meViewModel;
    private UserViewModel userViewModel;

    private LinearLayout deviceAlreadySetLayout;
    private LinearLayout deviceMainLayout;
    private LinearLayout deviceCheckingLayout;

    private TextView deviceInfo;
    private Button yesILostItBTN,iNeedToUseThisDevice;
    private List<Device> devices =new ArrayList<>();
    public DeviceConfigurationFragment() {
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
        view= inflater.inflate(R.layout.fragment_device_configuration, container, false);

        deviceAlreadySetLayout = view.findViewById(R.id.deviceAlreadySetLayout);
        deviceMainLayout = view.findViewById(R.id.deviceSettingMainLayout);
        deviceInfo = view.findViewById(R.id.deviceInfo);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        meViewModel = ViewModelProviders.of(getActivity()).get(MeViewModel.class);

        deviceViewModel = ViewModelProviders.of(getActivity()).get(DeviceViewModel.class);
        deviceViewModel.storeResponse().observe(getActivity(),this::consumeResponse);

        cardTypeRoomViewModel = ViewModelProviders.of(getActivity()).get(CardTypeRoomViewModel.class);

        cardTypeViewModel = ViewModelProviders.of(getActivity()).get(CardTypeViewModel.class);
        deviceRoomViewModel = ViewModelProviders.of(getActivity()).get(DeviceRoomViewModel.class);

        deviceCheckingLayout = view.findViewById(R.id.deviceCheckingLayout);
        setMyDevice = view.findViewById(R.id.setMyDevice);
        setMyDevice.setOnClickListener(this::onClick);

        meViewModel.me().observe(getActivity(),meResponse -> {
            deviceCheckingLayout.setVisibility(View.GONE);
            devices.addAll(meResponse.getData().getRelations().getDevice());
            if (devices.size()>0){
                deviceInfo.setText("You were using another phone with a serial number "
                        +devices.get(0).getSerial_number()+". do you stop using it or have you lost it. " +
                        "if you lost it we can prevent your card from lost and you will continue with this device." +
                        " or if you need to use this phone we will prevent your previous phone you will continue with" +
                        " this device as well.");
                deviceAlreadySetLayout.setVisibility(View.VISIBLE);
                deviceMainLayout.setVisibility(View.GONE);
            }else {
                deviceAlreadySetLayout.setVisibility(View.GONE);
                deviceMainLayout.setVisibility(View.VISIBLE);
            }
        });

        yesILostItBTN = view.findViewById(R.id.lostDeviceBTN);
        iNeedToUseThisDevice = view.findViewById(R.id.useThisDevice);

        if (devices.size()>0){
            yesILostItBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View item) {
                    view.findViewById(R.id.deviceSettingLoadingLayout)
                            .setVisibility(View.VISIBLE);
                    deviceAlreadySetLayout.setVisibility(View.GONE);
                    Device device = new Device();
                    device.setSerial_number(Build.SERIAL);
                    deviceViewModel.update(devices.get(0).getId(),device);

                }
            });
        }
        iNeedToUseThisDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View itemView) {
                view.findViewById(R.id.deviceSettingLoadingLayout)
                        .setVisibility(View.VISIBLE);
                deviceAlreadySetLayout.setVisibility(View.GONE);
                Device device = new Device();
                device.setSerial_number(Build.SERIAL);
                deviceViewModel.store(device);

            }
        });
        return view;
    }

    @Override
    public void onClick(View itemView) {
        view.findViewById(R.id.deviceSettingLoadingLayout).setVisibility(View.VISIBLE);
        view.findViewById(R.id.deviceSettingMainLayout).setVisibility(View.GONE);
        Device device = new Device();
        device.setSerial_number(android.os.Build.SERIAL);
        deviceViewModel.store(device);

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
            DeviceRoom device = new DeviceRoom();
            device.setSerial_number(android.os.Build.SERIAL);
            deviceRoomViewModel.store(device);

            cardTypeViewModel.index().enqueue(new Callback<ArrayList<CardType>>() {
                @Override
                public void onResponse(Call<ArrayList<CardType>> call, Response<ArrayList<CardType>> response) {
                    if (response.isSuccessful()){
                        for (int i=0;i<response.body().size();i++){
                            CardType cardType = response.body().get(i);
                            CardTypeRoom cardTypeRoom = new CardTypeRoom();
                            cardTypeRoom.setId(cardType.getId());
                            cardTypeRoom.setName(cardType.getName());
                            cardTypeRoom.setValue(cardType.getValue());
                            cardTypeRoomViewModel.store(cardTypeRoom);
                        }

                        addUserToThisPhone();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<CardType>> call, Throwable t) {

                }
            });

        }

    }

    public void addUserToThisPhone(){
        meViewModel.me().observe(getActivity(),meResponse -> {
            Attribute attribute = meResponse.getData().getAttribute();
            UserRoom userRoom = new UserRoom();
            userRoom.setFull_name(attribute.getFirst_name());
            userRoom.setEmail(attribute.getEmail());
            userRoom.setPhone(attribute.getPhone());
            userViewModel.store(userRoom);

            Intent intent = new Intent(getActivity(), RetailersDashboard.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);

        });
    }
}
