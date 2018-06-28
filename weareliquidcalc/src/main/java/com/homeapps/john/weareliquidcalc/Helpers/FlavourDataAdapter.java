package com.homeapps.john.weareliquidcalc.Helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homeapps.john.weareliquidcalc.Pojo.Flavour;
import com.homeapps.john.weareliquidcalc.R;

import java.util.List;

public class FlavourDataAdapter extends RecyclerView.Adapter<FlavourDataAdapter.FlavourViewHolder> {
    public List<Flavour> flavours;

    public class FlavourViewHolder extends RecyclerView.ViewHolder {
        public TextView name, percent;

        public FlavourViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.flavour_listitem_name);
            percent = view.findViewById(R.id.flavour_listitem_percentage);
        }
    }

    public FlavourDataAdapter(List<Flavour> players) {
        this.flavours = players;
    }

    @Override
    public FlavourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flavour_list_item, parent, false);

        return new FlavourViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FlavourViewHolder holder, int position) {
        Flavour flavour = flavours.get(position);
        holder.name.setText(flavour.getName());
        holder.percent.setText(flavour.getPercentage().toString());
    }

    @Override
    public int getItemCount() {
        return flavours.size();
    }
}