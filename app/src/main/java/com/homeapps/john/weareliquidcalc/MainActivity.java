package com.homeapps.john.weareliquidcalc;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;

public class MainActivity extends WearableActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Enables Always-on
        setAmbientEnabled();
    }

    //Todo: Move nav code from action drawer to nav click listeners
    protected void nav_ClickListener(View view){
        // handle navigational stuff here
    }
}
