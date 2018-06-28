package com.homeapps.john.weareliquidcalc.Helpers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homeapps.john.weareliquidcalc.Pojo.Recipe;
import com.homeapps.john.weareliquidcalc.R;

import java.util.ArrayList;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.myViewHolder> {
    private ArrayList<Recipe> mDataset;

    public static class myViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView_Row1;
        public TextView mTextView_Row2;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView_Row1 = itemView.findViewById(R.id.recipe_textview_row1);//itemView;
            mTextView_Row2 = itemView.findViewById(R.id.recipe_textview_row2);//itemView;
        }
    }

    public RecipeRecyclerViewAdapter(ArrayList<Recipe> dataSet){
        this.mDataset = dataSet;
    }

    @NonNull
    @Override
    public RecipeRecyclerViewAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item,viewGroup,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder viewHolder, int i) {
        viewHolder.mTextView_Row1.setText(mDataset.get(i).getName());
        viewHolder.mTextView_Row2.setText(mDataset.get(i).getRatio().toString());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
