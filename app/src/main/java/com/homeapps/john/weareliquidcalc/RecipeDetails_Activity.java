package com.homeapps.john.weareliquidcalc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import com.homeapps.john.weareliquidcalc.Pojo.Recipe;

public class RecipeDetails_Activity extends WearableActivity {
//Todo: Add 'Back' actiondrawer button
    private TextView mTextView;//recipe_details_details

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Recipe passedRecipe = (Recipe)getIntent().getSerializableExtra("recipe");

        mTextView = (TextView) findViewById(R.id.txt_lbl_details);

        String strDetails = getString(R.string.recipe_details_details,
                passedRecipe.getName(),
                passedRecipe.getRatio(),
                passedRecipe.getNicotinebase(),
                passedRecipe.getNicotine());
        for(int i=0;i<passedRecipe.getFlavours().size();i++){
            strDetails += getString(R.string.recipe_details_details_flavour, i+1,
                    passedRecipe.getFlavours().get(i).getName(),
                    passedRecipe.getFlavours().get(i).getPercentage());
        }

        mTextView.setText(strDetails);
        // Enables Always-on
        setAmbientEnabled();
    }
}
