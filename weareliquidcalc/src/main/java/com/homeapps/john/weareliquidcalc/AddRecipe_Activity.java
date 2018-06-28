package com.homeapps.john.weareliquidcalc;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.wear.widget.drawer.WearableNavigationDrawerView;
import android.support.wearable.activity.WearableActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.homeapps.john.weareliquidcalc.Helpers.FlavourDataAdapter;
import com.homeapps.john.weareliquidcalc.Helpers.SwipeRecycler.SwipeController;
import com.homeapps.john.weareliquidcalc.Helpers.SwipeRecycler.SwipeControllerActions;
import com.homeapps.john.weareliquidcalc.Pojo.Flavour;
import com.homeapps.john.weareliquidcalc.Pojo.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AddRecipe_Activity extends WearableActivity implements WearableNavigationDrawerView.OnItemSelectedListener{

    private static final int REQCODE_ADDFLAVOUR = 1;

    private ArrayList<String> mMenuItems;

    private EditText editName;
    private TextView tvRatio;//txt_lbl_pg_vg_ratio
    private TextView tvNicBase;//txt_lbl_base_nicotine
    private TextView tvNicTarget;//txt_lbl_desired_nic

    private SeekBar seekRatio;
    private SeekBar seekNicBase;
    private SeekBar seekNicTarget;

    private SeekBarChangeHandler seekChangeHandler;

    private int ratioValue = 50;
    private int nicBaseValue = 0;
    private int nicTargetValue = 0;


    private FlavourDataAdapter mAdapter;
    SwipeController swipeController = null;
    Recipe newRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe);

        newRecipe = new Recipe();//(Recipe)passedIntent.getSerializable("Recipe");
         mMenuItems = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.mainmenu_items)));

         editName = findViewById(R.id.editText);
        tvRatio = findViewById(R.id.txt_pg_vg_ratio);
        tvNicBase = findViewById(R.id.txt_base_nicotine);
        tvNicTarget = findViewById(R.id.txt_desired_nic);

        tvRatio.setText(getString(R.string.txt_lbl_pg_vg_ratio,ratioValue));
        tvNicBase.setText(getString(R.string.txt_lbl_base_nicotine,nicBaseValue));
        tvNicTarget.setText(getString(R.string.txt_lbl_desired_nic,nicTargetValue));

        seekRatio = findViewById(R.id.seek_vgpg);
        seekNicBase = findViewById(R.id.seek_nic_base);
        seekNicTarget = findViewById(R.id.seek_nic_target);
        seekChangeHandler = new SeekBarChangeHandler();

        editName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    // handle input
                    InputMethodManager imm = (InputMethodManager)textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    editName.clearFocus();
                    handled = true;
                }
                return handled;
            }
        });

        seekRatio.setProgress(ratioValue);
        seekNicBase.setProgress(nicBaseValue);
        seekNicTarget.setProgress(nicTargetValue);

        seekRatio.setOnSeekBarChangeListener(seekChangeHandler );
        seekNicBase.setOnSeekBarChangeListener(seekChangeHandler );
        seekNicTarget.setOnSeekBarChangeListener(seekChangeHandler );

        editName.requestFocus();
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editName, InputMethodManager.SHOW_IMPLICIT);

        setFlavoursDataAdapter(newRecipe);
        setupRecyclerView();
        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onItemSelected(int i) {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQCODE_ADDFLAVOUR:
                    Flavour resultFlavour = (Flavour)data.getSerializableExtra("flavour");
                    newRecipe.getFlavours().add(0,resultFlavour);
                    mAdapter.notifyDataSetChanged();
                    break;
            }

        }
    }

    private void setFlavoursDataAdapter(Recipe recipe) {
        List<Flavour> flavours = recipe.getFlavours();
        try {
            for (int i=0;i<flavours.size();i++)
                mMenuItems.add(flavours.get(i).getName());
        } catch (Exception e) {

        }

        mAdapter = new FlavourDataAdapter(flavours);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.flavours_recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position){

            }
            @Override
            public void onRightClicked(int position) {
                mAdapter.flavours.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    public void addRecipe_click(View view){
        Intent addFlavourIntent = new Intent(AddRecipe_Activity.this, AddFlavour_Activity.class);
        startActivityForResult(addFlavourIntent,REQCODE_ADDFLAVOUR);
    }

    //Todo: verify valid recipe before returning
    public void save_click(View view){
        newRecipe.setName(editName.getText().toString());
        newRecipe.setRatio(ratioValue);
        newRecipe.setNicotinebase(nicBaseValue);
        newRecipe.setNicotine(nicTargetValue);
//        newRecipe.setFlavours(newRecipe.);
        Intent result = new Intent();
        result.putExtra("recipe", newRecipe);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    public void discard_click(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private final class NavAdapter extends WearableNavigationDrawerView.WearableNavigationDrawerAdapter{

        Context mContext;

        public NavAdapter(Context context){
            this.mContext = context;
        }

        @Override
        public CharSequence getItemText(int i) {
            return mMenuItems.get(i);
        }

        @Override
        public Drawable getItemDrawable(int i) {
            return ContextCompat.getDrawable(mContext, android.R.drawable.btn_star);
                    //mContext.getResources().getDrawable(android.R.drawable.btn_star);
        }

        @Override
        public int getCount() {
            return mMenuItems.size();
        }
    }

    private final class SeekBarChangeHandler implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            switch(seekBar.getId()){
                case R.id.seek_vgpg:
                    ratioValue = i;
                    seekRatio.setProgress(ratioValue);
                    tvRatio.setText(getString(R.string.txt_lbl_pg_vg_ratio,ratioValue));
                    break;
                case R.id.seek_nic_base:
                    nicBaseValue = i;
                    seekNicBase.setProgress(nicBaseValue);
                    tvNicBase.setText(getString(R.string.txt_lbl_base_nicotine,nicBaseValue));
                    break;
                case R.id.seek_nic_target:
                    nicTargetValue = i;
                    seekNicTarget.setProgress(nicTargetValue);
                    tvNicTarget.setText(getString(R.string.txt_lbl_desired_nic,nicTargetValue));
                    break;
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

    }
}
