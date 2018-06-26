package com.homeapps.john.weareliquidcalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.support.wearable.activity.WearableActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.homeapps.john.weareliquidcalc.Pojo.Recipe;

public class RecipeDetails_Activity extends WearableActivity {
//Todo: Add 'Back' actiondrawer button
    private TextView mTextView;//recipe_details_details
    private WearableActionDrawerView actionMenuView;
    private int recipeIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Recipe passedRecipe = (Recipe)getIntent().getSerializableExtra("recipe");
        recipeIndex = getIntent().getIntExtra("recipeIndex",-1);

        mTextView = (TextView) findViewById(R.id.txt_lbl_details);
        actionMenuView = (WearableActionDrawerView) findViewById(R.id.details_actionview);

        String strDetails = String.format(getString(R.string.recipe_details_details),
                passedRecipe.getName(),
                "PG "+passedRecipe.getRatio().toString() + "/" + (100 - passedRecipe.getRatio() + " VG"),
                passedRecipe.getNicotinebase(),
                passedRecipe.getNicotine());
        for(int i=0;i<passedRecipe.getFlavours().size();i++){
            strDetails += getString(R.string.recipe_details_details_flavour, i+1,
                    passedRecipe.getFlavours().get(i).getName(),
                    passedRecipe.getFlavours().get(i).getPercentage());
        }

        mTextView.setText(strDetails);
        actionMenuView.getController().peekDrawer(); // no peeking
        actionMenuView.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete_recipe:
                        Intent result = new Intent();
                        result.putExtra("recipeIndex", recipeIndex);
                        result.putExtra("deleting", true);
                        setResult(RESULT_OK, result);
                        finish();
                        break;
                    case R.id.details_back:
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                        break;
                }
                return true;
            }
        });
        // Enables Always-on
        setAmbientEnabled();
    }
}
