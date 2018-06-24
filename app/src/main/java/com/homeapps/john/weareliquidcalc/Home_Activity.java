package com.homeapps.john.weareliquidcalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.drawer.WearableActionDrawerView;
import android.support.wearable.activity.WearableActivity;
import android.view.MenuItem;
import android.view.View;

import com.homeapps.john.weareliquidcalc.Helpers.RecipeRecyclerViewAdapter;
import com.homeapps.john.weareliquidcalc.Helpers.RecyclerTouchListener;
import com.homeapps.john.weareliquidcalc.Pojo.Flavour;
import com.homeapps.john.weareliquidcalc.Pojo.Recipe;

import java.util.ArrayList;
import java.util.Arrays;

public class Home_Activity extends WearableActivity {

    public static interface ClickListener{
        public void onClick(View view,int position);
        public void onLongClick(View view, int position);
    }

    ArrayList<Recipe> testData;
    RecyclerView recyclerView;
    WearableActionDrawerView actionMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);

//        testData = new ArrayList<>();
        testData = getRecipeTestData();

        actionMenuView = (WearableActionDrawerView) findViewById(R.id.home_action_drawer);
        actionMenuView.getController().peekDrawer();
        actionMenuView.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent data;
                switch(menuItem.getItemId()) {
                    case R.id.add_new_recipe:
                        data = new Intent(Home_Activity.this, AddRecipe_Activity.class);
                        data.putExtra("Recipe",testData.get(2));
                        startActivityForResult(data, 1);
                        return true;
                    default:
                        return false;
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recipe_recyclerview);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecipeRecyclerViewAdapter(testData));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Intent recipeDetailsIntent = new Intent(Home_Activity.this, RecipeDetails_Activity.class);
                recipeDetailsIntent.putExtra("recipe",testData.get(position));
                startActivityForResult(recipeDetailsIntent,position);
                /* // Test sub-view selection detection
                TextView text=(TextView) view.findViewById(R.id.recipe_textview_row1);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(Home_Activity.this, "Single Click on Textbox:"+position, Toast.LENGTH_SHORT).show();
                        return;
                    }
                });
                Toast.makeText(Home_Activity.this, "Single Click on position:"+position, Toast.LENGTH_SHORT).show();
                */
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(Home_Activity.this, "Long press on position:"+position, Toast.LENGTH_LONG).show();
            }
        }));

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){
            case RESULT_OK:
                break;
            case RESULT_CANCELED:
                break;
            default:
                break;
        }
    }

    private ArrayList<Recipe> getRecipeTestData(){
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

}
