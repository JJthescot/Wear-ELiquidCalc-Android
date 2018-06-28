package com.homeapps.john.weareliquidcalc;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.homeapps.john.shared.common.Recipe;

public class MixDetails_Activity extends WearableActivity {

    private final int AMOUNTMIN = 1;
    private final int AMOUNTMAX = 1000;

    private TextView textView_Header;
    private TextView textView_Result;
    private TextView textView_Quantity;
    private SeekBar seekBar_Quantity;

    float PG_fract;
    float VG_fract;
    float nic_fract;

    int amountValue;
    Recipe passedRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mix_details);
        passedRecipe = (Recipe)getIntent().getSerializableExtra("recipe");

        VG_fract = (100f - passedRecipe.Ratio.floatValue()) / 100f;
        nic_fract = passedRecipe.Nicotine.floatValue() / passedRecipe.NicotineBase.floatValue();
        PG_fract = passedRecipe.Ratio.floatValue() / 100f;

        amountValue = 50;

        textView_Header = findViewById(R.id.mix_header_textview);
        textView_Header.setText(passedRecipe.Name);

        textView_Quantity = findViewById(R.id.mix_amount_textview);
        textView_Result = findViewById(R.id.mix_results_textview);
        seekBar_Quantity = findViewById(R.id.mix_seekBar_amount);
        seekBar_Quantity.setMax(AMOUNTMAX);

        seekBar_Quantity.setProgress(amountValue);
        seekBar_Quantity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                amountValue = i;
                if (amountValue < AMOUNTMIN) {
                    amountValue = AMOUNTMIN;
                    seekBar_Quantity.setProgress(AMOUNTMIN);
                }
                populateViewData(passedRecipe);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        populateViewData(passedRecipe);
        // Enables Always-on
        setAmbientEnabled();
    }

    private void populateViewData(Recipe recipe){
        String message = "";
        String flavourMessage = "";
        float flavourAmountSum = 0f;

        for (int i=0;i<recipe.Flavours.size();i++){
            float amount = roundedFloat((recipe.Flavours.get(i).Percentage.floatValue() / 100f) * amountValue);
            flavourMessage += getString(R.string.mix_details_result_flavour,
                    recipe.Flavours.get(i).Name,
                    amount
            );
            flavourAmountSum += amount;
        }
        message = getString(R.string.mix_details_result_main,
                roundedFloat(VG_fract*amountValue),
                roundedFloat(((PG_fract*amountValue)-(nic_fract*amountValue))-flavourAmountSum),
                roundedFloat( nic_fract*amountValue));

        textView_Quantity.setText(String.valueOf(amountValue));
        textView_Result.setText(message + flavourMessage);

    }
    private float roundedFloat(float value){

        return (Math.round(value * 1000f) / 1000f);
    }
    public void onChangeAmountValue(View view){
        switch(view.getId()){
            case R.id.mix_add_button:
                amountValue++;
                if (amountValue>AMOUNTMAX)
                    amountValue = AMOUNTMAX;
                break;
            case R.id.mix_subtract_button:
                amountValue--;
                if (amountValue<AMOUNTMIN)
                    amountValue = AMOUNTMIN;
                break;
        }
        populateViewData(passedRecipe);
    }
}
