package com.ism.exam;

import android.app.Activity;
import android.os.Bundle;

import com.ism.exam.fragment.ExamHostFragment;
import com.ism.exam.fragment.QuestionPaletteFragment;

/**
 * Created by c161 on 14/10/15.
 */
public class HostActivity extends Activity {

	private static final String TAG = HostActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host);

		initGlobal();

	}

	private void initGlobal() {
		getFragmentManager().beginTransaction().replace(R.id.fl_fragment_container_main, ExamHostFragment.newInstance()).commit();
	}

}
