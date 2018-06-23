package com.homeapps.john.weareliquidcalc;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wear.widget.drawer.WearableNavigationDrawerView;
import android.support.wearable.activity.WearableActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends WearableActivity implements WearableNavigationDrawerView.OnItemSelectedListener{

    ArrayList<String> mMenuItems;
    private WearableNavigationDrawerView mWearableNavigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mMenuItems = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.mainmenu_items)));

        mWearableNavigationDrawer = (WearableNavigationDrawerView)findViewById(R.id.navigation_drawer);
        mWearableNavigationDrawer.setAdapter(new NavAdapter(this));
        mWearableNavigationDrawer.getController().peekDrawer();
        mWearableNavigationDrawer.addOnItemSelectedListener(this);

        // Enables Always-on
        setAmbientEnabled();
    }

    //Todo: Move nav code from action drawer to nav click listeners
    protected void nav_ClickListener(View view){
        // handle navigational stuff here
    }

    @Override
    public void onItemSelected(int i) {

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
            return mContext.getResources().getDrawable(android.R.drawable.btn_star);
        }

        @Override
        public int getCount() {
            return mMenuItems.size();
        }
    }
}
