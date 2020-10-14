package com.example.foragentss.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foragentss.R;
import com.example.foragentss.auth.models.Child;
import com.example.foragentss.auth.models.RegionCity;
import com.example.foragentss.home.fragments.AddressFragment;

import java.util.ArrayList;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    private ArrayList<Child> regionCities;
    private Context context;
    private AddressFragment addressFragment;

    public CitiesAdapter(Context context, AddressFragment addressFragment, ArrayList<Child> placeList) {
        this.regionCities = placeList;
        this.context = context;
        this.addressFragment = addressFragment;
    }

    @NonNull
    @Override
    public CitiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.region_city_layout, viewGroup, false);
        return new CitiesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesAdapter.ViewHolder viewHolder, int i) {
        Child regionCity = regionCities.get(i);
        viewHolder.name.setText(regionCity.getName());
        viewHolder.cardView.setTag(regionCity.getName());
        viewHolder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        viewHolder.name.setTextColor(Color.WHITE);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressFragment.citySelected(regionCity);
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

            name = (TextView) itemView.findViewById(R.id.region_city_name);
            cardView = (CardView) itemView.findViewById(R.id.region_city_cardview);
        }
    }

}
