package com.homeapps.john.mobileeliquidcalc.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.homeapps.john.mobileeliquidcalc.Providers.DataManager;
import com.homeapps.john.mobileeliquidcalc.R;
import com.homeapps.john.shared.common.Flavour;
import com.homeapps.john.shared.common.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class RecipeListActivityFragment extends Fragment {

    private RecipeAdapter mAdapter;
    private boolean mItemClicked;

    public RecipeListActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        List<Recipe> recipes = loadRecipesFromFile();
        List<Recipe> recipes = loadTestingRecipeData();
        mAdapter = new RecipeAdapter(getActivity(), recipes);

        View view = inflater.inflate(R.layout.recipe_fragment_main, container, false);
        RecipeRecyclerView recyclerView =
                view.findViewById(android.R.id.list);
        recyclerView.setEmptyView(view.findViewById(android.R.id.empty));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mItemClicked = false;
    }

    private List<Recipe> loadTestingRecipeData(){
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        for (int i=0; i<25; i++) {
            recipes.add(new Recipe(
                    "Recipe"+i,
                    45,
                    72,
                    3,
                    new ArrayList<Flavour>(Arrays.asList(
                            new Flavour("Strawberry", 10),
                            new Flavour("Raspberry", 10))
                    )
            ));
        }
        return recipes;
    }
    private List<Recipe> loadRecipesFromFile() {
        if (DataManager.fileExists(getContext())) {
            return DataManager.fileLoadData(getContext());
        }
        return new ArrayList<Recipe>();
    }


    private class RecipeAdapter extends RecyclerView.Adapter<ViewHolder>
            implements ItemClickListener {

        public List<Recipe> mRecipeList;
        private Context mContext;

        public RecipeAdapter(Context context, List<Recipe> recipes) {
            super();
            mContext = context;
            mRecipeList = recipes;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.recipt_list_item, parent, false);
            return new ViewHolder(view, this);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Recipe recipe = mRecipeList.get(position);

            holder.mTitleTextView.setText(recipe.Name);
            holder.mDescriptionTextView.setText(getString(R.string.RecipeListDescFlavours, recipe.Flavours.size()));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return mRecipeList == null ? 0 : mRecipeList.size();
        }

        @Override
        public void onItemClick(View view, int position) {
            if (!mItemClicked) {
                mItemClicked = true;
                View heroView = view.findViewById(android.R.id.icon);
//                DetailActivity.launch(
//                        getActivity(), mAdapter.mRecipeList.get(position).name, heroView);
            }
        }
    }

    private static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView mTitleTextView;
        TextView mDescriptionTextView;
        ItemClickListener mItemClickListener;

        public ViewHolder(View view, ItemClickListener itemClickListener) {
            super(view);
            mTitleTextView = view.findViewById(android.R.id.text1);
            mDescriptionTextView = view.findViewById(android.R.id.text2);
            mItemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
