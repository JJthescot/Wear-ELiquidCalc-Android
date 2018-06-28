package com.homeapps.john.weareliquidcalc;

import android.app.Activity;
import android.os.Bundle;
import android.support.wear.widget.CircularProgressLayout;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

public class ConfirmAction_Activity extends WearableActivity implements CircularProgressLayout.OnClickListener, CircularProgressLayout.OnTimerFinishedListener{

    private TextView mTextView;
    private CircularProgressLayout circularProgressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_action);

        mTextView = findViewById(R.id.confirmationMessage);
        circularProgressLayout = findViewById(R.id.confirmation_circular_progress);
        circularProgressLayout.setOnClickListener(this);
        circularProgressLayout.setOnTimerFinishedListener(this);
        String passedMessage = getIntent().getStringExtra("confirmMessage");
        mTextView.setText(passedMessage);

        // Two seconds to cancel the action
        circularProgressLayout.setTotalTime(3000);
// Start the timer
        circularProgressLayout.startTimer();
        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onTimerFinished(CircularProgressLayout circularProgressLayout) {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View view) {
        if(view.equals(circularProgressLayout)){
            circularProgressLayout.stopTimer();
            setResult(Activity.RESULT_CANCELED);
            finish();
        }
    }
}
