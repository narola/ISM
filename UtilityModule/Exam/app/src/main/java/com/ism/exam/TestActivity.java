package com.ism.exam;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TestActivity extends Activity {

	private static final String TAG = TestActivity.class.getSimpleName();

	private RadioGroup radioGroup;
	private Button btnClearSelection;
	private Button btnUncheckSelection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		initGlobal();

	}

	private void initGlobal() {
		radioGroup = (RadioGroup) findViewById(R.id.rg_options);
		btnClearSelection = (Button) findViewById(R.id.btn_clear_selection);
		btnUncheckSelection = (Button) findViewById(R.id.btn_uncheck_selection);

		btnClearSelection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				radioGroup.clearCheck();
			}
		});

		btnUncheckSelection.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				radioGroup.clearCheck();
				for (int i = 0; i < radioGroup.getChildCount(); i++) {
					RadioButton radioButton = ((RadioButton)radioGroup.getChildAt(i));
//					if (radioButton.isChecked()) {
						radioButton.setChecked(false);
//					}
				}
			}
		});

	}
}
