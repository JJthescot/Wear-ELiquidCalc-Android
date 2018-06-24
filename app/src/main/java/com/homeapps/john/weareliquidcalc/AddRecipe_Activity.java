package com.homeapps.john.weareliquidcalc;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.wear.widget.drawer.WearableNavigationDrawerView;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
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

    private ArrayList<String> mMenuItems;

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

    private WearableNavigationDrawerView mWearableNavigationDrawer;
//    private WearableActionDrawerView wearableActionDrawerView;

    private FlavourDataAdapter mAdapter;
    SwipeController swipeController = null;
    Recipe currentRecipe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe);

        Bundle passedIntent = getIntent().getExtras();
        currentRecipe = (Recipe)passedIntent.getSerializable("Recipe");
         mMenuItems = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.mainmenu_items)));

        tvRatio = (TextView) findViewById(R.id.txt_pg_vg_ratio);
        tvNicBase = (TextView) findViewById(R.id.txt_base_nicotine);
        tvNicTarget = (TextView) findViewById(R.id.txt_desired_nic);

        tvRatio.setText(getString(R.string.txt_lbl_pg_vg_ratio,ratioValue));
        tvNicBase.setText(getString(R.string.txt_lbl_base_nicotine,nicBaseValue));
        tvNicTarget.setText(getString(R.string.txt_lbl_desired_nic,nicTargetValue));

        seekRatio = (SeekBar) findViewById(R.id.seek_vgpg);
        seekNicBase = (SeekBar) findViewById(R.id.seek_nic_base);
        seekNicTarget = (SeekBar) findViewById(R.id.seek_nic_target);
        seekChangeHandler = new SeekBarChangeHandler();

        seekRatio.setProgress(ratioValue);
        seekNicBase.setProgress(nicBaseValue);
        seekNicTarget.setProgress(nicTargetValue);

        seekRatio.setOnSeekBarChangeListener(seekChangeHandler );
        seekNicBase.setOnSeekBarChangeListener(seekChangeHandler );
        seekNicTarget.setOnSeekBarChangeListener(seekChangeHandler );

        mWearableNavigationDrawer = (WearableNavigationDrawerView)findViewById(R.id.navigation_drawer);
        mWearableNavigationDrawer.setAdapter(new NavAdapter(this));
        mWearableNavigationDrawer.getController().peekDrawer();
        mWearableNavigationDrawer.addOnItemSelectedListener(this);

/*        wearableActionDrawerView = (WearableActionDrawerView)findViewById(R.id.action_drawer);
        wearableActionDrawerView.getController().peekDrawer();
        wearableActionDrawerView.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.nav_back:
                        setResult(RESULT_CANCELED);
                        finish();
                        break;
                    case R.id.nav_save:
                        setResult(RESULT_OK);
                        finish();
                        break;
                    case R.id.nav_vgpg_ratio:
                        break;
                    case R.id.nav_set_nicotine:
                        break;
                    case R.id.nav_add_flava:
                        break;
                }
                return false;
            }
        });
        */
        setFlavoursDataAdapter(currentRecipe);
        setupRecyclerView();
        // Enables Always-on
        setAmbientEnabled();
    }

    //Todo: Move nav code from action drawer to nav click listeners
    protected void nav_ClickListener(View view){
        // handle navigational stuff here
    }

    @Override
    public void onItemSelected(int i) {
        setResult(RESULT_OK);
        finish();
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
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.flavours_recyclerview);

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
