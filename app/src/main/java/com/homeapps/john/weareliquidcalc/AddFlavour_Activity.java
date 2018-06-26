package com.homeapps.john.weareliquidcalc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import com.homeapps.john.weareliquidcalc.Pojo.Flavour;

public class AddFlavour_Activity extends WearableActivity {
//Todo: Implement 'add flavour' functionality
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addflavour);

        mTextView = (TextView) findViewById(R.id.text);
        Intent result = new Intent();
        result.putExtra("flavour",new Flavour("shrubbery",10));
        setResult(Activity.RESULT_OK,result);
        finish();
        // Enables Always-on
        setAmbientEnabled();
    }
}
