package com.example.foragentss.home.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foragentss.R;
import com.example.foragentss.auth.agents.AgentsDashboard;
import com.example.foragentss.auth.helpers.GridSpacingItemDecoration;
import com.example.foragentss.auth.models.Address;
import com.example.foragentss.auth.models.Child;
import com.example.foragentss.auth.models.RegionCity;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retailers.RetailersDashboard;
import com.example.foragentss.auth.utils.ApiResponse;
import com.example.foragentss.auth.view_model.AddressViewModel;
import com.example.foragentss.auth.view_model.RegionCityViewModel;
import com.example.foragentss.home.RegistrationActivity;
import com.example.foragentss.home.adapter.CitiesAdapter;
import com.example.foragentss.home.adapter.RegionCityAdapter;

import java.util.ArrayList;


public class AddressFragment extends Fragment implements View.OnClickListener {
   private int roleId;
   private RegionCityViewModel regionCityViewModel;
   private ProgressBar progressBar;
   private LinearLayout mainLayout;
   private LinearLayout selectRegion;

   private RecyclerView recyclerView;
   private RegionCityAdapter regionCityAdapter;
   private ArrayList<RegionCity> regionCityArrayList = new ArrayList<>();

   private RecyclerView citiesRecyclerView;
   private CitiesAdapter citiesAdapter;
   private ArrayList<Child> citiesArrayList = new ArrayList<>();

   private LinearLayout regionLayout,cityLayout,agentsForm;
   private TextView selectCityName;
   private TextView selectedRegionName,selectedcityName;
   private RegionCity selecteRegionCity;
   private Child selectedCity;
   private AddressViewModel addressViewModel;
   private Address address;
   private Button agentDone;
   private TextView settingYourAddress;
   private TextView addressInfo;
   private String infoMessage;
   private String from;
    public AddressFragment() {
        // Required empty public constructor
    }

    public AddressFragment(int roleId,String message,String from){
        this.roleId = roleId;
        this.infoMessage = message;
        this.from = from;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_address, container, false);

        addressViewModel = ViewModelProviders.of(getActivity()).get(AddressViewModel.class);
        address = new Address();

        regionLayout = view.findViewById(R.id.regionLayout);
        cityLayout = view.findViewById(R.id.citiesLayout);
        agentsForm = view.findViewById(R.id.agentsForm);
        selectedRegionName = view.findViewById(R.id.selectedRegion);
        selectedcityName = view.findViewById(R.id.selectedCity);
        agentDone = view.findViewById(R.id.yesBtn);
        agentDone.setOnClickListener(this::onClick);
        settingYourAddress = view.findViewById(R.id.settingYourAddress);
        addressInfo = view.findViewById(R.id.addressInfo);
        addressInfo.setText(this.infoMessage);

        progressBar = view.findViewById(R.id.addressLoading);
        mainLayout = view.findViewById(R.id.addressMainLayout);
        selectRegion = view.findViewById(R.id.selectRegion);

        recyclerView = view.findViewById(R.id.address_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        regionCityAdapter = new RegionCityAdapter(getContext(),this,regionCityArrayList);
        recyclerView.setAdapter(regionCityAdapter);


        selectCityName = view.findViewById(R.id.selectCityName);
        citiesRecyclerView = view.findViewById(R.id.cities_recyclerview);
        citiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        citiesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        citiesAdapter = new CitiesAdapter(getContext(),this,citiesArrayList);
        citiesRecyclerView.setAdapter(citiesAdapter);

        regionCityViewModel = ViewModelProviders.of(getActivity()).get(RegionCityViewModel.class);
        regionCityViewModel.index().observe(getActivity(),regionCityResponse -> {
            progressBar.setVisibility(View.GONE);
            mainLayout.setVisibility(View.VISIBLE);
            regionCityArrayList.addAll(regionCityResponse.getData());
            regionCityAdapter.notifyDataSetChanged();
        });

        return  view;
    }


    public void showCities(RegionCity regionCity){
        this.selecteRegionCity = regionCity;
        regionLayout.setVisibility(View.GONE);
        cityLayout.setVisibility(View.VISIBLE);
        selectCityName.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        citiesArrayList.addAll(regionCity.getChild());
        citiesAdapter.notifyDataSetChanged();

    }


    public void citySelected(Child child){
        this.selectedCity = child;
        cityLayout.setVisibility(View.GONE);
        selectedRegionName.setText("Selected region: "+selecteRegionCity.getName());
        selectedcityName.setText("Selected city: "+selectedCity.getName());
        agentsForm.setVisibility(View.VISIBLE);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yesBtn:
                registerAgentAddress(selecteRegionCity,selectedCity);
        }
    }

    public void registerAgentAddress(RegionCity selecteRegionCity,Child selectedCity){
            address.setRegion_id(selecteRegionCity.getId());
            address.setCity_id(selectedCity.getId());
            address.setBuilding_name("");
            address.setFloor_no("");
            address.setSpecific_name("");
            mainLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            settingYourAddress.setVisibility(View.VISIBLE);
            addressViewModel.storeResponse().observe(getActivity(), this::consumeResponse);
            addressViewModel.store(address);
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
        if(response.isStatus()) {
            if (roleId==3){
                if (from.equals("dashboard")){
                    AgentsDashboard agentsDashboard = (AgentsDashboard)getActivity();
                    agentsDashboard.showHome();
                }else {
                    Intent intent = new Intent(getActivity(), AgentsDashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            }else if (roleId==4){
                RegistrationActivity registrationActivity =(RegistrationActivity)getActivity();
                registrationActivity.showDeviceConfig();
            }
        }

    }
}
