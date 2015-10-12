package com.narola.kpa.scientificcalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.narola.kpa.scientificcalc.view.Calc;

public class CalcActivity extends AppCompatActivity {

    private static final String TAG = CalcActivity.class.getSimpleName();

    private Calc mCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        initGlobal();

    }

    private void initGlobal() {
        mCalc = (Calc) findViewById(R.id.calc);

        mCalc.setOnDisplayLongClickListener(new Calc.OnDisplayLongClickListener() {
            @Override
            public void onDisplayLongClick(View view, String displayText) {
                Toast.makeText(CalcActivity.this, displayText, Toast.LENGTH_SHORT).show();
            }
        });

        /*Log.e(TAG, "is scientific enabled byDefault : " + mCalc.isEnableScientific());
        mCalc.setEnableScientific(true);
        Log.e(TAG, "is scientific enabled : " + mCalc.isEnableScientific());*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
