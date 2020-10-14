package com.example.foragentss.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.RegionCity;
import com.example.foragentss.home.fragments.AddressFragment;

import java.util.ArrayList;

public class RegionCityAdapter extends RecyclerView.Adapter<RegionCityAdapter.ViewHolder> {
    private ArrayList<RegionCity> regionCities;
    private Context context;
    private AddressFragment addressFragment;
    public RegionCityAdapter(Context context,AddressFragment addressFragment, ArrayList<RegionCity> placeList){
        this.regionCities = placeList;
        this.context = context;
        this.addressFragment = addressFragment;
    }

    @NonNull
    @Override
    public RegionCityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.region_city_layout,viewGroup,false);
        return new RegionCityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegionCityAdapter.ViewHolder viewHolder, int i) {
        RegionCity regionCity=regionCities.get(i);
        viewHolder.name.setText(regionCity.getName());
        viewHolder.cardView.setTag(regionCity.getName());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressFragment.showCities(regionCity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return regionCities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.region_city_name);
            cardView = (CardView)itemView.findViewById(R.id.region_city_cardview);
        }
    }

}
